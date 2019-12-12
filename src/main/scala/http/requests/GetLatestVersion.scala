package http.requests

import java.util.regex.Pattern

import scalaj.http.HttpResponse

import scala.collection.mutable.ListBuffer

/**
  * Gets the latest version of the shared configuration
  *
  * @param baseUrl
  */
class GetLatestVersion(baseUrl: String, devName: String, configName: String) extends BaseRequest {

  override def parseResponse(response: HttpResponse[String]): String = {
    val matcher = Pattern.compile(configName + "[0-9].[0-9]").matcher(response.body)
    var list = new ListBuffer[Double]
    while (matcher.find()) {
      list += matcher.group().takeRight(3).toDouble
    }
    val sorted = list.sorted
    sorted.toList.last.toString
  }

  override def buildRequest: String = {
    var request = baseUrl + devName + "/" + "Shared/?keys=true"
    request
  }

  override var requestUrl: String = buildRequest
}
