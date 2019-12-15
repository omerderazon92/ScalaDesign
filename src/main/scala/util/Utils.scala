package util

import java.io.{File, PrintWriter}

import scalaj.http.Base64

/**
  * Project utils
  */
object Utils {
  var logsDelimiter = "-------------------------------------------------------------"

  def base64Decoder(input: String): String = {
    Base64.decodeString(input)
  }

  def writeFile(fileName: String, input: String): Unit = {
    val current = new File(".").getCanonicalPath
    val file = new File(current + "/cliff-common/src/main/resources/" + fileName)
    val writer = new PrintWriter(file) {
      write(input)
      close()
    }
  }
}
