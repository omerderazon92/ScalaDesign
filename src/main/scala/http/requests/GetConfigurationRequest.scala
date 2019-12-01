package http.requests

/**
  * Fetching the configurations for each project
  * @param url
  * @param projectName
  */
class GetConfigurationRequest(url: String, projectName: String) extends BaseRequest {
  override val requestUrl: String = url + "/" +projectName
}
