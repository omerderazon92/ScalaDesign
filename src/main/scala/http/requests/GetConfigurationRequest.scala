package http.requests
package project.configuration

import http.responses.ConsulKV
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, parser}
import scalaj.http.HttpResponse

/**
  * * The request the gets the configuration
  *
  * @param baseUrl
  * @param devName
  * @param configName
  * @param version
  */
class GetConfigurationRequest(baseUrl: String, devName: String, configName: String, version: String) extends BaseRequest {

  override var requestUrl: String = buildRequest

  override def buildRequest: String = {
    var request = baseUrl + devName + "/" + "Shared"
    if (configName.equals("Shared")) {
      request += "/" + configName + version
    } else {
      request += "/" + configName + "/" + configName + version
    }
    request
  }

  override def parseResponse(response: HttpResponse[String]): String = {
    implicit val staffDecoder: Decoder[ConsulKV] = deriveDecoder[ConsulKV]
    val decodeResult = parser.decode[List[ConsulKV]](response.body)
    decodeResult match {
      case Right(res) => res.head.Value
      case Left(error) => null
    }
  }
}
