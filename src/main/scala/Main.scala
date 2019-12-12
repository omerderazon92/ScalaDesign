import project.configuration.{ConfigurationFetcher, DevName, ConfigurationName}

object Main {
  def main(args: Array[String]): Unit = {
    ConfigurationFetcher.apply(ConfigurationName.Cliff, sharedVersion = "2.0")
    ConfigurationFetcher.fetchConfiguration()
  }
}