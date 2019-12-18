import java.io.File

import org.scalatest.funsuite.AnyFunSuite
import project.configuration.{ConfigurationFetcher, ConfigurationName, DevName}

class TestAPI extends AnyFunSuite {

  def deleteFilesFromAPITesting(): Unit = {

  }

  test("Test API with known version") {
    //Preperation
    val version = "1.0"
    val fileName: String = (ConfigurationName.TestAPI + version + ".conf").toLowerCase

    //Test
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestAPI, version))
    ConfigurationFetcher.fetchConfiguration()

    //Assert
    assert(new File(fileName).exists())
  }

  test("Test API with latest version") {
    //Preperation
    val version = "1.1"
    val fileName: String = (ConfigurationName.TestAPI + version + ".conf").toLowerCase

    //Test
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestAPI, null))
    ConfigurationFetcher.fetchConfiguration()

    //Assert
    assert(new File(fileName).exists())
  }
}
