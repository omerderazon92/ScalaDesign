package project.configuration

import http.HTTPManager
import project.configuration
import project.configuration.DevName.DevName
import project.configuration.ConfigurationName.ConfigurationName
import util.Utils

object DevName extends Enumeration {
  type DevName = Value
  val OmerD: configuration.DevName.Value = Value("omerd")
  val Prod: configuration.DevName.Value = Value("prod")
}

object ConfigurationName extends Enumeration {
  type ConfigurationName = Value
  val Cliff: configuration.ConfigurationName.Value = Value("Cliff")
  val Item: configuration.ConfigurationName.Value = Value("Item")
  val Shared: configuration.ConfigurationName.Value = Value("Shared")
}

/**
  * Responsible for fetching the project configuration from a Redis remote server
  * please use the projectName from the const options to prevent mistakes
  */
object ConfigurationFetcher extends ConfigurationManager {

  override var configurationName: ConfigurationName = _
  override var devName: DevName = _
  override var sharedVersion: String = _

  def apply(configurationName: ConfigurationName, devName: DevName, sharedVersion: String = null): Unit = {
    this.configurationName = configurationName
    this.devName = devName
    this.sharedVersion = sharedVersion
  }

  /**
    * Fetches the configuration from the Redis remote server
    *
    * @return A key map HashMap of String to Any
    */
  override def fetchConfiguration(): Unit = {

    def getLatestVersion: String = {
      HTTPManager.getLatsetVersion.getOrElse("")
    }

    def executeRequests(projectName: String, devName: String, fileName: String, sharedVersion: String): Unit = {
      var sharedVersionToUse = if (sharedVersion == null) "" else sharedVersion
      if (projectName.equals(ConfigurationName.Shared.toString) && sharedVersion == null
        && devName.equals(DevName.Prod.toString)) {
        sharedVersionToUse = getLatestVersion
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
