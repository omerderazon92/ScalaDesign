package project.configuration

import org.json4s.jackson.JsonMethods.parse

import scala.collection.immutable.HashMap

/**
  * Contract for managing the project configurations
  */
abstract class ConfigurationManager {
  def fetchConfiguration: HashMap[String, Any]

  def parseResult(hashMap: HashMap[String, String]): HashMap[String, Any]


  /**
    * A util function for parsing the json into a map
    *
    * @param jsonStr
    * @return
    */
  protected def jsonStrToMap(jsonStr: String): Map[String, String] = {
    implicit val formats = org.json4s.DefaultFormats
    parse(jsonStr).extract[Map[String, String]]
  }
}
