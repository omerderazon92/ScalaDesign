package http.requests

class GetConfigurationRequest(url: String, projectName: String) extends BaseRequest {
  override val requestUrl: String = url + "/" +projectName
}
