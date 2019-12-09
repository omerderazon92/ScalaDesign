package http.requests
package project.configuration

import scalaj.http.HttpResponse
import http.responses.ConsulKV
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, parser}

/**
  * Fetching the configurations for each project
  *
  * @param url
  * @param projectName
  */
class GetConfigurationRequest(url: String, projectName: String, devName: String) extends BaseRequest {
  override val requestUrl: String = url + devName + "/" + projectName

  override def parseResponse(response: HttpResponse[String]): String = {
    implicit val staffDecoder: Decoder[ConsulKV] = deriveDecoder[ConsulKV]
    val decodeResult = parser.decode[List[ConsulKV]](response.body)
    decodeResult match {
      case Right(res) => res.head.Value
      case Left(error) => null
    }
  }
}
