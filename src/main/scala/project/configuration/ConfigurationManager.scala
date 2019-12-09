package project.configuration

import project.configuration.DevName.DevName
import project.configuration.ConfigurationName.ConfigurationName

/**
  * Contract for managing the project configurations
  */
abstract class ConfigurationManager {
  var configurationName: ConfigurationName
  var devName: DevName
  var sharedVersion: String

  def fetchConfiguration()
}
