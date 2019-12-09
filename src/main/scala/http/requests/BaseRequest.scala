package http.requests

import scalaj.http.HttpResponse

/**
  * Base request to inherit from
  */
abstract class BaseRequest {
  val requestUrl: String
  def parseResponse(response:HttpResponse[String]):String
}
