package http

import project.configuration.DevName.DevName

/**
  * API contract
  */
trait API {
  def getConfigurations(forProject: String, devName: String): Option[String]
  def getLatsetVersion(devName: String): Option[String]
}
