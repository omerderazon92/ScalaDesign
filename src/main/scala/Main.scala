import project.configuration.{ConfigurationFetcher, DevName, ProjectName}

object Main {
  def main(args: Array[String]): Unit = {
    ConfigurationFetcher.apply(ProjectName.Cliff, DevName.OmerD, "1.0")
    ConfigurationFetcher.fetchConfiguration()
  }
}