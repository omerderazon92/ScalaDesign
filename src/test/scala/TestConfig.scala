import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.funsuite.AnyFunSuite

class TestConfig extends AnyFunSuite {

  var config: Config = _

  test("Loading simple config object from conf file") {
    config = ConfigFactory.load("application.conf")
    System.out.println()
  }
}