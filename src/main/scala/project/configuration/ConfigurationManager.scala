package project.configuration

import project.configuration.DevName.DevName
import project.configuration.ConfigurationName.ConfigurationName
import scala.collection.mutable

/**
  * Contract for managing the project configurations
  */
abstract class ConfigurationManager {
  var devName: DevName
  var configsToFetch: Seq[(ConfigurationName, String)]
  var fileToPathMap: mutable.HashMap[String, String]

  def getLatestConfigVersion(project: ConfigurationName): String

  def fetchConfiguration()
}
