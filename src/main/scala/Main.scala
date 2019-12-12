import project.configuration.{ConfigurationFetcher, DevName, ConfigurationName}

object Main {
  def main(args: Array[String]): Unit = {
    ConfigurationFetcher.apply(DevName.OmerD, (ConfigurationName.Shared, null), (ConfigurationName.Cliff, null))
    ConfigurationFetcher.fetchConfiguration()
  }
}