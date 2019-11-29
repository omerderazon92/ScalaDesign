package http

import scalaj.http._

/**
  * Managing the connection between the lib and config micro service
  */
object HTTPManager {

  implicit final val OK: Int = 200
  val baseUrl: String = "http://localhost:8082/baseUrl/getConfigList/"

  /**
    * RestGet method
    *
    * @param forProject project name
    * @return map of configurations
    */
  def getConfigurations(forProject: String): String = {
    val response: HttpResponse[String] = Http(baseUrl + "/" + forProject).asString
    if (response.code == OK) {
      response.body
    } else {
      null
    }
  }
}
