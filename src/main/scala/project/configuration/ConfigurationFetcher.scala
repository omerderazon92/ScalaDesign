package project.configuration

import http.HTTPManager
import http.responses.ConsulKV
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, parser}
import project.configuration
import project.configuration.DevName.DevName
import project.configuration.ProjectName.ProjectName
import util.Utils

object DevName extends Enumeration {
  type DevName = Value
  val OmerD: configuration.DevName.Value = Value("omerd")
  val Master: configuration.DevName.Value = Value("master")
}

object ProjectName extends Enumeration {
  type ProjectName = Value
  val Cliff: configuration.ProjectName.Value = Value("Cliff")
  val Item: configuration.ProjectName.Value = Value("Item")
}

/**
  * Responsible for fetching the project configuration from a Redis remote server
  * please use the projectName from the const options to prevent mistakes
  */
object ConfigurationFetcher extends ConfigurationManager {

  override var projectName: ProjectName = _
  override var devName: DevName = _

  def apply(projectName: ProjectName, devName: DevName): Unit = {
    this.projectName = projectName
    this.devName = devName
  }

  /**
    * Fetches the configuration from the Redis remote server
    *
    * @return A key map HashMap of String to Any
    */
  override def fetchConfiguration(): Unit = {
    val results = HTTPManager.getConfigurations(projectName.toString, devName.toString).orNull
    val value = extractValue(results).orNull
    if (value == null) {
      return null
    }
    val decodedString = Utils.base64Decoder(value)
    Utils.writeFile("appremoteconf.conf", decodedString)
  }

  /**
    * Extract the right value from the response
    * @param response
    * @return
    */
  override def extractValue(response: String): Option[String] = {
    implicit val staffDecoder: Decoder[ConsulKV] = deriveDecoder[ConsulKV]
    val decodeResult = parser.decode[List[ConsulKV]](response)
    decodeResult match {
      case Right(res) => Option(res.head.Value)
      case Left(error) => null
    }
  }
}
