package project.configuration

import project.configuration.DevName.DevName
import project.configuration.ProjectName.ProjectName

/**
  * Contract for managing the project configurations
  */
abstract class ConfigurationManager {
  var projectName: ProjectName
  var devName: DevName

  def fetchConfiguration()

  def extractValue(response: String): Option[String]
}
