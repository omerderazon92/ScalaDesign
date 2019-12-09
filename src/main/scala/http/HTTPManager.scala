package http

import http.requests.{BaseRequest, GetLatestVersion}
import http.requests.project.configuration.GetConfigurationRequest
import scalaj.http._

/**
  * Managing the connection between the lib and config micro service
  */
object HTTPManager extends API {

  implicit final val OK: Int = 200
  val httpSettingFactory: HttpSettingFactory.type = HttpSettingFactory

  /**
    * Execute the http request using basic- no library scala http handler
    *
    * @param request the request itself
    * @return
    */
  def executeHttpRequest(request: BaseRequest): Option[HttpResponse[String]] = {
    val response: HttpResponse[String] = Http(request.requestUrl).asString
    if (response.code == OK)
      return Option(response)
    null
  }

  /**
    * RestGet method
    *
    * @param projectName project name
    * @return map of configurations
    */
  def getConfigurations(projectName: String, devName: String): Option[String] = {
    var getConfigurationRequest = new GetConfigurationRequest(httpSettingFactory.configBaseUrl, projectName, devName)
    val response = executeHttpRequest(getConfigurationRequest).orNull
    if (response != null) {
      val parsedResponse = getConfigurationRequest.parseResponse(response)
      return Option(parsedResponse)
    }
    null
  }

  override def getLatsetVersion: Option[String] = {
    var getLatestVersion = new GetLatestVersion(httpSettingFactory.configBaseUrl)
    val response = executeHttpRequest(getLatestVersion).orNull
    val parsedResponse = getLatestVersion.parseResponse(response)
    if (response != null) {
      return Option(parsedResponse)
    }
    null
  }
}
