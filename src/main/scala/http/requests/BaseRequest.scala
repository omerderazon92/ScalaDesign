package http.requests

import scalaj.http.HttpResponse

abstract class BaseRequest {
  val requestUrl: String
  def parseResponse(response:HttpResponse[String]):String
}
