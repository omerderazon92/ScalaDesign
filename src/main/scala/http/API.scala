package http

trait API {
  def getConfigurations(forProject: String, devName: String): Option[String]
}
