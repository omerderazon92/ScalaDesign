import project.configuration.{ConfigurationFetcher, DevName, ConfigurationName}

object Main {
  def main(args: Array[String]): Unit = {
    ConfigurationFetcher.apply(configurationName = ConfigurationName.Cliff)
    ConfigurationFetcher.fetchConfiguration()
  }
}