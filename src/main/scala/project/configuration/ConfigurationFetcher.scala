package project.configuration

import http.HTTPManager
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.collection.immutable.HashMap

/**
  * Responsible for fetching the project configuration from a Redis remote server
  * please use the projectName from the const options to prevent mistakes
  */
object ConfigurationFetcher extends ConfigurationManager {

  implicit final val CLIFF: String = "cliffConfiguration"
  implicit final val ITEM: String = "itemConfiguration"
  implicit var projectName: String = ""

  /**
    * Fetches the configuration from the Redis remote server
    *
    * @return A key map HashMap of String to Any
    */
  override def fetchConfiguration: HashMap[String, Any] = {
    val results = HTTPManager.getConfigurations(projectName)
    if (results != null) {
      val resultAsAMap = jsonStrToMap(results)
      System.out.println(resultAsAMap.toString())
      parseResult(new HashMap[String, String])
    }
    null
  }

  /**
    * Parses the HashMap from string to string, to string to any
    *
    * @param result
    * @return
    */
  override def parseResult(result: HashMap[String, String]): HashMap[String, Any] = {
    new HashMap[String, Any]
  }
}
