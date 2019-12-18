import org.scalatest.funsuite.AnyFunSuite
import project.configuration.ConfigurationName.ConfigurationName
import project.configuration.{ConfigurationFetcher, ConfigurationName, DevName}

class TestConfig extends AnyFunSuite {


  private def createFilesMap(configsToFetch: Seq[(ConfigurationName, String)]): Unit = {
    for ((configName, version) <- configsToFetch) {
      ConfigurationFetcher.fileToPathMap.put(configName.toString, configName.toString + ".conf")
    }
  }

  test("Test simple load") {
    //Preperation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfig, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()
    assert(config.getInt("test.rightConfig.one") == 1)
    assert(config.getInt("test.rightConfig.two") == 2)
    assert(config.getInt("test.rightConfig.three") == 3)
    assert(config.getInt("test.rightConfig.four") == 4)
    assert(config.getInt("test.rightConfig.five") == 5)
  }

  test("Test typesafe") {
    //Preperation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfig, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()
    assert(config.getString("test.rightConfig.one") == "1")
    assert(config.getString("test.rightConfig.two") == "2")
    assert(config.getString("test.rightConfig.three") == "3")
    assert(config.getString("test.rightConfig.four") == "4")
    assert(config.getString("test.rightConfig.five") == "5")
  }

  test("Test load by order1") {
    //Preperation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfig, ""), (ConfigurationName.TestConfig2, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()
    assert(config.getInt("test.rightConfig.one") == 1)
    assert(config.getInt("test.rightConfig.two") == 2)
    assert(config.getInt("test.rightConfig.three") == 3)
    assert(config.getInt("test.rightConfig.four") == 4)
    assert(config.getInt("test.rightConfig.five") == 5)
  }

  test("Test load by order2") {
    //Preperation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfig2, ""), (ConfigurationName.TestConfig, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()
    assert(config.getInt("test.rightConfig.one") == 11)
    assert(config.getInt("test.rightConfig.two") == 22)
    assert(config.getInt("test.rightConfig.three") == 33)
    assert(config.getInt("test.rightConfig.four") == 44)
    assert(config.getInt("test.rightConfig.five") == 55)
  }
}