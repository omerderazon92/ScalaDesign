package project.configuration

import http.HTTPManager
import project.configuration
import project.configuration.DevName.DevName
import project.configuration.ConfigurationName.ConfigurationName
import util.Utils

import scala.concurrent.{ExecutionContext, Future}

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
      val encodedResponse = HTTPManager.getConfigurations(devName, configName, version).orNull
      if (encodedResponse != null) {
        val decodedResponse = Utils.base64Decoder(encodedResponse)
        Utils.writeFile(configName, decodedResponse)
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
    val maybeString = HTTPManager.getLatsetVersion(devName.toString, project.toString).orNull
    maybeString
  }
}
