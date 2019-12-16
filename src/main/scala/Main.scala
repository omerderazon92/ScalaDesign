import project.configuration.{ConfigurationFetcher, DevName, ConfigurationName}

object Main {
  def main(args: Array[String]): Unit = {
    ConfigurationFetcher.apply(DevName.OmerD, (ConfigurationName.Cliff, null), (ConfigurationName.Shared, null))
    ConfigurationFetcher.fetchConfiguration()
    val config = ConfigurationFetcher.provideConfigObject()
  }
}