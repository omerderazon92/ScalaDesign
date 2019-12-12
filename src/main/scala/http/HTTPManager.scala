package http

import http.requests.{BaseRequest, GetLatestVersion}
import http.requests.project.configuration.GetConfigurationRequest
import project.configuration.DevName.DevName
import scalaj.http._

/**
  * Managing the HTTP request of the library
  */
object HTTPManager extends API {

  implicit final val OK: Int = 200
  val httpSettingFactory: HttpSettingFactory.type = HttpSettingFactory

  /**
    * A generic HTTP requests executer
    *
    * @param request generic base request
    * @return returns an optional HTTP response
    */
  def executeHttpRequest(request: BaseRequest): Option[HttpResponse[String]] = {
    val response: HttpResponse[String] = Http(request.requestUrl).asString
    if (response.code == OK)
      return Option(response)
    null
  }

  override def getConfigurations(devName: DevName, configurationName: String, version: String): Option[String] = {
    var getConfigurationRequest = new GetConfigurationRequest(httpSettingFactory.sharedConfigBaseUrl, devName.toString, configurationName, version)
    val response = executeHttpRequest(getConfigurationRequest).orNull
    val parsedResponse = getConfigurationRequest.parseResponse(response)
    if (parsedResponse != null) {
      return Option(parsedResponse)
    }
    null
  }

  override def getLatsetVersion(devName: String, configurationName: String): Option[String] = {
    var getLatestVersion = new GetLatestVersion(httpSettingFactory.sharedConfigBaseUrl, devName.toString, configurationName)
    val response = executeHttpRequest(getLatestVersion).orNull
    val parsedResponse = getLatestVersion.parseResponse(response)
    if (parsedResponse != null) {
      return Option(parsedResponse)
    }
    null
  }
}
