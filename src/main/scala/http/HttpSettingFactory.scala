package http

import http.HttpSettingFactory.AAClusterURL

/**
  * Includes all the url's
  */
object HttpSettingFactory {
  val sharedConfigVersions: String = "http://10.11.123.191:31553/v1/kv/omer-design/Prod/?keys=true"

  val AAClusterURL: String = "http://10.11.123.191:31553/v1"

  val sharedConfigBaseUrl: String = {
    AAClusterURL + "/kv/omer-design/"
  }
}
