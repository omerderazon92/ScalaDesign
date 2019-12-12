package http.requests

import scalaj.http.HttpResponse

/**
  * Base request to inherit from
  */
abstract class BaseRequest {
  var requestUrl: String

  def buildRequest: String

  def parseResponse(response: HttpResponse[String]): String
}
