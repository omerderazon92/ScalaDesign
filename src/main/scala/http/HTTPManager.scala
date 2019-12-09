package http

import http.requests.{BaseRequest, GetLatestVersion}
import http.requests.project.configuration.GetConfigurationRequest
import scalaj.http._

/**
  * Managing the HTTP request of the library
  */
object HTTPManager extends API {

  implicit final val OK: Int = 200
  val httpSettingFactory: HttpSettingFactory.type = HttpSettingFactory

  /**
    * A generic HTTP requests executer
    * @param request generic base request
    * @return returns an optional HTTP response
    */
  def executeHttpRequest(request: BaseRequest): Option[HttpResponse[String]] = {
    val response: HttpResponse[String] = Http(request.requestUrl).asString
    if (response.code == OK)
      return Option(response)
    null
  }

  /**
    * Gets the configuration from Consul
    * @param projectName the project that we want the configuration of
    * @param devName the developer that we want the configuration of
    * @return
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

  /**
    * Gets the latest version of the shared configuration
    * @return
    */
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
