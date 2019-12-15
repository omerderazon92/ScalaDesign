package util

import java.io.PrintWriter

import scalaj.http.Base64

import scala.reflect.io.File

/**
  * Project utils
  */
object Utils {
  var logsDelimiter = "-------------------------------------------------------------"

  def base64Decoder(input: String): String = {
    Base64.decodeString(input)
  }

  def writeFile(fileName: String, input: String): Unit = {
    new PrintWriter(fileName) {
      write(input);
      close()
    }
  }
}
