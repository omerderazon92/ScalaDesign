import java.io.{File, FilenameFilter}
import java.nio.file.Paths

import org.scalatest.funsuite.AnyFunSuite
import project.configuration.{ConfigurationFetcher, ConfigurationName, DevName}

class TestAPI extends AnyFunSuite {

  private def deleteFilesFromAPITesting(): Unit = {
    val currentRelativePath = Paths.get("")
    val s = currentRelativePath.toAbsolutePath.toFile

    if (s.isDirectory) {
      val files = s.listFiles(new FilenameFilter {
        override def accept(dir: File, name: String): Boolean = {
          name.contains("testapi")
        }
      })
      for (file <- files) {
        if (file.delete())
          System.out.println("Deleting apitest files")
      }
    }
  }

  test("Test API with known version") {
    //Preperation
    deleteFilesFromAPITesting()
    val version = "1.0"
    val fileName: String = (ConfigurationName.TestAPI + version + ".conf").toLowerCase

    //Test
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestAPI, version))
    ConfigurationFetcher.fetchConfiguration()

    //Assert
    assert(new File(fileName).exists())

    //Clean up redundant files
    deleteFilesFromAPITesting()
  }

  test("Test API with latest version") {
    deleteFilesFromAPITesting()
    //Preperation
    val version = "1.1"
    val fileName: String = (ConfigurationName.TestAPI + version + ".conf").toLowerCase

    //Test
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestAPI, null))
    ConfigurationFetcher.fetchConfiguration()

    //Assert
    assert(new File(fileName).exists())

    //Clean up redundant files
    deleteFilesFromAPITesting()
  }
}
