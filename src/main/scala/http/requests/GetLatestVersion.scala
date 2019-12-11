package http.requests

import java.util.regex.Pattern

import scalaj.http.HttpResponse

import scala.collection.mutable.ListBuffer

/**
  * Gets the latest version of the shared configuration
  *
  * @param url
  */
class GetLatestVersion(url: String, devName: String) extends BaseRequest {
  override val requestUrl: String = url + devName + "/?keys=true"

  override def parseResponse(response: HttpResponse[String]): String = {
    val matcher = Pattern.compile("[0-9].[0-9]").matcher(response.body)
    var list = new ListBuffer[Double]
    while (matcher.find()) {
      list += matcher.group().toDouble
    }
    val sorted = list.sorted
    sorted.toList.last.toString
  }
}
