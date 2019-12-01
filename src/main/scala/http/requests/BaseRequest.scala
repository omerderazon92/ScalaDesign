package http.requests

import scalaj.http.HttpResponse

abstract class BaseRequest {
  val requestUrl: String
  def parseResponse[T,U](respone:HttpResponse[U]):T
}
