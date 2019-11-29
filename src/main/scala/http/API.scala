package http

trait API {
  def getConfigurations(forProject: String): String
}
