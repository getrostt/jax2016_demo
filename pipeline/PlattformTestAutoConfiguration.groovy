node {
  def wildflyContainer = null
  def postgresContainer = null
  try {
    stage 'start container'
      // #1 add postgres container
      def postgresImage = docker.image('postgres:9.4')
      postgresContainer = postgresImage.run("--name db1 -e POSTGRES_PASSWORD=jax2016!")

      def wildflyImage = docker.image('hap/wildfly:8.1.0.Final')
      wildflyContainer = wildflyImage.run("--name srv01 -p 8180:8080 -p 9990:9990 --link db1:db1")

    stage 'configure server'
      // #2 replace with data source configuration
      wildflyImage.inside('--link srv01:srv01') {
        git url: 'https://github.com/getrostt/jax2016_demo.git'

        waitToBeStarted('srv01')

        def cliFiles = findFiles glob: '**/*.cli'
        for (int i = 0; i < cliFiles.size(); i++) {
          runCli(cliFiles[i].path, 'srv01', 'db', 'db1')
        }
      }

    stage 'deploy application'
      echo 'Deploying application...'
      git url: 'https://github.com/getrostt/jax2016_demo.git'
      wildflyImage.inside('--link srv01:srv01') {
        // #1 change name of deployable
        deploy('deployables/jboss-kitchensink-angularjs-bootstrap-wo-datasource.war', 'srv01')
      }

    stage 'test application'
      input 'Done?'
  } finally {
      wildflyContainer?.stop()
      postgresContainer?.stop()
  }
}
