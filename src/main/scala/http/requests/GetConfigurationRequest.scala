package http.requests

import scalaj.http.HttpResponse

/**
  * Fetching the configurations for each project
  *
  * @param url
  * @param projectName
  */
class GetConfigurationRequest(url: String, projectName: String, devName: String) extends BaseRequest {
  override val requestUrl: String = url + devName + "/" + projectName

  override def parseResponse[T, U](respone: HttpResponse[U]): T = ???
}
