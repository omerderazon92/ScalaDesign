package project.configuration

import project.configuration.DevName.DevName
import project.configuration.ConfigurationName.ConfigurationName

/**
  * Contract for managing the project configurations
  */
abstract class ConfigurationManager {
  var devName: DevName
  var configToFetch: Seq[(ConfigurationName, String)]

  def getLatestConfigVersion(project: ConfigurationName): String

  def fetchConfiguration()
}
