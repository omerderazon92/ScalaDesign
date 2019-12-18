package project.configuration

import java.io.File

import com.typesafe.config.{Config, ConfigFactory, ConfigParseOptions, ConfigResolveOptions, ConfigResolver}
import com.typesafe.scalalogging.Logger
import http.HTTPManager
import org.slf4j.LoggerFactory
import project.configuration
import project.configuration.ConfigurationName.ConfigurationName
import project.configuration.DevName.DevName
import util.Utils

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object DevName extends Enumeration {
  type DevName = Value
  val OmerD: configuration.DevName.Value = Value("OmerD")
  val Prod: configuration.DevName.Value = Value("Prod")
  val Test: configuration.DevName.Value = Value("Test")
}

object ConfigurationName extends Enumeration {
  type ConfigurationName = Value
  val Cliff: configuration.ConfigurationName.Value = Value("Cliff")
  val Item: configuration.ConfigurationName.Value = Value("Item")
  val Shared: configuration.ConfigurationName.Value = Value("Shared")
  val TestConfig: configuration.ConfigurationName.Value = Value("testConfig1.0")
  val TestConfig2: configuration.ConfigurationName.Value = Value("testConfig2.0")
  val TestAPI: configuration.ConfigurationName.Value = Value("TestAPI")
}

/**
  * Responsible for fetching the configuration from Consul KV according to few parameters
  */
object ConfigurationFetcher extends ConfigurationManager {

  override var devName: DevName = _
  override var configsToFetch: Seq[(ConfigurationName, String)] = _
  override var fileToPathMap = new mutable.HashMap[String, String]()
  val logger = Logger(LoggerFactory.getLogger("Configuration Fetcher"))


  def apply(devName: DevName, configToFetch: (ConfigurationName, String)*): Unit = {
    this.devName = devName
    this.configsToFetch = configToFetch
  }

  /**
    * Fetches the configuration and save them to local conf files.
    * One for the unique project configuration and other for the shared configurations
    */
  override def fetchConfiguration(): Unit = {

    def executeRequest(devName: DevName = devName, configName: String, version: String): Unit = {
      logger.info("Trying to fetch the configuration of " + devName + " " + configName + " Version " + version + "......")
      val encodedResponse = HTTPManager.getConfigurations(devName, configName, version)
      if (encodedResponse != null) {
        val fileName = configName.toLowerCase + version.toLowerCase + ".conf"
        logger.info("Was able to fetch the configurations successfully, writing them into a conf file named " + "\"" + fileName + "\"")
        logger.info(Utils.logsDelimiter)
        val decodedResponse = Utils.base64Decoder(encodedResponse)
        Utils.writeFile(fileName, decodedResponse)
        fileToPathMap.put(configName, fileName)
      } else {
        logger.error("Couldn't achieve the configuration of " + configName + " " + version + " Maybe version doesn't exist in Consul-KV ")
        logger.info(Utils.logsDelimiter)
      }
    }

    //Fetch Configuration method begins here
    for (config <- configsToFetch) {
      var versionToPull: String = config._2
      if (config._2 == null) {
        versionToPull = getLatestConfigVersion(config._1)
      }
      executeRequest(configName = config._1.toString, version = versionToPull)
    }
  }

  /**
    * Fetches the lates version number of a given project
    * @param project
    * @return
    */
  override def getLatestConfigVersion(project: ConfigurationName): String = {
    logger.info("Trying to achieve the latest version of " + project)
    val maybeString = HTTPManager.getLatsetVersion(devName.toString, project.toString)
    if (maybeString != null) {
      logger.info("The latest version of " + project + " is " + maybeString)
    } else {
      logger.error("Couldn't achieve latest version")
    }
    maybeString
  }

  /**
    * After fetching the configuration into a local file, provideConfigObject() returns the config object with the right order
    * @return
    */
  def provideConfigObject(): Config = {
    val listOfConfFiles = new ListBuffer[File]
    val listOfConfigs = new ListBuffer[Config]
    // List of config files to add
    for (configs <- configsToFetch) {
      val fileName = fileToPathMap.get(configs._1.toString).orNull
      if (fileName == null) {
        logger.error("Couldn't create a complete config object, config file of " + configs._1.toString + " version " + configs._2 + " doesnt exist ")
      } else {
        listOfConfFiles.append(new File(fileName))
      }
    }
    //Creating Config objects from the path
    for (file <- listOfConfFiles) {
      logger.info("Merging " + file.getName)
      listOfConfigs.append(ConfigFactory.parseFile(file))
    }
    val mergedConfig = listOfConfigs.foldLeft(ConfigFactory.empty()) { (toReturn, configFile) =>
      toReturn.withFallback(configFile)
    }
    mergedConfig.resolve()
  }
}
