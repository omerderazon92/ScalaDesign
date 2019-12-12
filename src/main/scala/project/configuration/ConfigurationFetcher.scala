package project.configuration

import com.typesafe.scalalogging.Logger
import http.HTTPManager
import org.slf4j.LoggerFactory
import project.configuration
import project.configuration.ConfigurationName.ConfigurationName
import project.configuration.DevName.DevName
import util.Utils

import scala.collection.mutable

object DevName extends Enumeration {
  type DevName = Value
  val OmerD: configuration.DevName.Value = Value("OmerD")
  val Prod: configuration.DevName.Value = Value("Prod")
}

object ConfigurationName extends Enumeration {
  type ConfigurationName = Value
  val Cliff: configuration.ConfigurationName.Value = Value("Cliff")
  val Item: configuration.ConfigurationName.Value = Value("Item")
  val Shared: configuration.ConfigurationName.Value = Value("Shared")
}

/**
  * Responsible for fetching the configuration from Consul KV according to few parameters
  */
object ConfigurationFetcher extends ConfigurationManager {

  override var devName: DevName = _
  override var configToFetch: Seq[(ConfigurationName, String)] = _
  override var fileNameMap = new mutable.HashMap[String, String]()
  val logger = Logger(LoggerFactory.getLogger("Configuration Fetcher"))


  def apply(devName: DevName, configToFetch: (ConfigurationName, String)*): Unit = {
    this.devName = devName
    this.configToFetch = configToFetch
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
        fileNameMap.put(configName, fileName)
      } else {
        logger.error("Couldn't achieve the configuration of " + configName + " " + version + " Maybe version doesn't exist in Consul-KV ")
        logger.info(Utils.logsDelimiter)
      }
    }
    //Fetch Configuration begins here
    for (config <- configToFetch) {
      var versionToPull: String = config._2
      if (config._2 == null) {
        versionToPull = getLatestConfigVersion(config._1)
      }
      executeRequest(configName = config._1.toString, version = versionToPull)
    }
  }

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

}
