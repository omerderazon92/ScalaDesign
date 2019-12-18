import org.scalatest.funsuite.AnyFunSuite
import project.configuration.ConfigurationName.ConfigurationName
import project.configuration.{ConfigurationFetcher, ConfigurationName, DevName}

class TestConfig extends AnyFunSuite {

  private def createFilesMap(configsToFetch: Seq[(ConfigurationName, String)]): Unit = {
    for ((configName, version) <- configsToFetch) {
      ConfigurationFetcher.fileToPathMap.put(configName.toString, configName.toString + ".conf")
    }
  }

  test("Test simple load of testConfig1.0.conf file") {
    //Preparation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfigLocalFile, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()

    //Assert
    assert(config.getInt("test.rightConfig.one") == 1)
    assert(config.getInt("test.rightConfig.two") == 2)
    assert(config.getInt("test.rightConfig.three") == 3)
    assert(config.getInt("test.rightConfig.four") == 4)
    assert(config.getInt("test.rightConfig.five") == 5)
  }

  test("Test typesafe, pull int as a String") {
    //Preparation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfigLocalFile, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()

    //Assert
    assert(config.getString("test.rightConfig.one") == "1")
    assert(config.getString("test.rightConfig.two") == "2")
    assert(config.getString("test.rightConfig.three") == "3")
    assert(config.getString("test.rightConfig.four") == "4")
    assert(config.getString("test.rightConfig.five") == "5")
  }

  test("Test load by order TestConfig to TestConfig2") {
    //Preparation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfigLocalFile, ""), (ConfigurationName.TestConfigLocalFile2, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()

    //Assert
    assert(config.getInt("test.rightConfig.one") == 1)
    assert(config.getInt("test.rightConfig.two") == 2)
    assert(config.getInt("test.rightConfig.three") == 3)
    assert(config.getInt("test.rightConfig.four") == 4)
    assert(config.getInt("test.rightConfig.five") == 5)
  }

  test("Test load by order TestConfig2 to TestConfig") {
    //Preparation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfigLocalFile2, ""), (ConfigurationName.TestConfigLocalFile, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()

    //Assert
    assert(config.getInt("test.rightConfig.one") == 11)
    assert(config.getInt("test.rightConfig.two") == 22)
    assert(config.getInt("test.rightConfig.three") == 33)
    assert(config.getInt("test.rightConfig.four") == 44)
    assert(config.getInt("test.rightConfig.five") == 55)
  }

  test ("Test unique values after loading with") {
    //Preparation
    ConfigurationFetcher.apply(DevName.Test, (ConfigurationName.TestConfigLocalFile, ""), (ConfigurationName.TestConfigLocalFile2, ""))
    createFilesMap(ConfigurationFetcher.configsToFetch)

    //Test
    val config = ConfigurationFetcher.provideConfigObject()

    //Assert
    assert(config.getString("test.uniqueVal.value1") == "unique1")
    assert(config.getString("test.uniqueVal.value2") == "unique2")
  }
}