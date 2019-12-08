import project.configuration.{ConfigurationFetcher, DevName, ProjectName}

object Main {
  def main(args: Array[String]): Unit = {
    ConfigurationFetcher.apply(ProjectName.Cliff, DevName.OmerD)
    ConfigurationFetcher.fetchConfiguration()
  }
}