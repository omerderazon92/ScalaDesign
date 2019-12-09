package http.requests
import scalaj.http.HttpResponse

class GetLatestVersion extends BaseRequest {
  override val requestUrl: String = ""

  override def parseResponse(response: HttpResponse[String]): String = ???
}
