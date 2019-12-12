package http

import project.configuration.DevName.DevName

/**
  * API contract
  */
trait API {
  def getConfigurations(devName: DevName, configurationName: String, version: String): Option[String]

  def getLatsetVersion(devName: String, configurationName: String): Option[String]
}
