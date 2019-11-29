package project.configuration

import scala.collection.immutable.HashMap

/**
  * Contract for managing the project configurations
  */
trait ConfigurationManager {
  def fetchConfiguration: HashMap[String, Any]

  def parseResult(hashMap: HashMap[String, String]): HashMap[String, Any]
}
