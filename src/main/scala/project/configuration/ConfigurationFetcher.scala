package project.configuration

import http.HTTPManager
import project.configuration
import project.configuration.DevName.DevName
import project.configuration.ProjectName.ProjectName
import util.Utils

object DevName extends Enumeration {
  type DevName = Value
  val OmerD: configuration.DevName.Value = Value("omerd")
  val Master: configuration.DevName.Value = Value("master")
}

object ProjectName extends Enumeration {
  type ProjectName = Value
  val Cliff: configuration.ProjectName.Value = Value("Cliff")
  val Item: configuration.ProjectName.Value = Value("Item")
}

/**
  * Responsible for fetching the project configuration from a Redis remote server
  * please use the projectName from the const options to prevent mistakes
  */
object ConfigurationFetcher extends ConfigurationManager {

  override var projectName: ProjectName = _
  override var devName: DevName = _
  override var sharedVersion: String = _

  def apply(projectName: ProjectName, devName: DevName, sharedVersion: String): Unit = {
    this.projectName = projectName
    this.devName = devName
    this.sharedVersion = sharedVersion
  }

  /**
    * Fetches the configuration from the Redis remote server
    *
    * @return A key map HashMap of String to Any
    */
  override def fetchConfiguration(): Unit = {
    val results = HTTPManager.getConfigurations(projectName.toString, devName.toString).orNull
    if (results == null) {
      return
    }
    val decodedString = Utils.base64Decoder(results)
    Utils.writeFile("appremoteconf.conf", decodedString)
  }
}
