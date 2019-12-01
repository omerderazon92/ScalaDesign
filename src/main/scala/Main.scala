import project.configuration.ConfigurationFetcher

object Main {
  def main(args: Array[String]): Unit = {
    ConfigurationFetcher.projectName = ConfigurationFetcher.ITEM
    val remoteConfigurations = ConfigurationFetcher.fetchConfiguration
  }
}
