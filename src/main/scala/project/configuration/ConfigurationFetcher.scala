package project.configuration

import http.HTTPManager
import project.configuration
import project.configuration.DevName.DevName
import project.configuration.ConfigurationName.ConfigurationName
import util.Utils

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

  override var configurationName: ConfigurationName = _
  override var devName: DevName = _
  override var sharedVersion: String = _

  def apply(configurationName: ConfigurationName, devName: DevName = DevName.Prod, sharedVersion: String = null): Unit = {
    this.configurationName = configurationName
    this.devName = devName
    this.sharedVersion = sharedVersion
  }

  /**
    * Fetches the configuration and save them to local conf files.
    * One for the unique project configuration and other for the shared configurations
    */
  override def fetchConfiguration(): Unit = {

    def getLatestVersion(devName: String): String = {
      HTTPManager.getLatsetVersion(devName).getOrElse("")
    }

    def executeRequests(projectName: String, devName: String, fileName: String, sharedVersion: String): Unit = {
      var sharedVersionToUse = if (sharedVersion == null) "" else sharedVersion
      if (projectName.equals(ConfigurationName.Shared.toString) && sharedVersion == null) {
        sharedVersionToUse = getLatestVersion(devName)
      }
      val configurations = HTTPManager.getConfigurations(projectName + sharedVersionToUse, devName).orNull
      if (configurations == null) {
        return
      }
      val decodedString = Utils.base64Decoder(configurations)
      Utils.writeFile(fileName, decodedString)
    }
    //Unique
    executeRequests(configurationName.toString, devName.toString, "uniqueremotecofig.conf", null)
    //Shared
    executeRequests(ConfigurationName.Shared.toString, devName.toString, "sharedremoteconfig.conf", sharedVersion)
  }
}
