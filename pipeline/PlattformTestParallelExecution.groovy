def stepsForParallel = [:]

stepsForParallel['wildfly8'] = createStepFromImage('hap/wildfly:8.1.0.Final', 1)
stepsForParallel['wildfly10'] = createStepFromImage('hap/wildfly:10.0.0.Final', 2)

parallel stepsForParallel

def createStepFromImage(imageName, imageCnt) {
  return {
    node {
      def wildflyContainer = null
      def postgresContainer = null
      try {
        stage "start container $imageName"
          // #1 add postgres container
          def postgresImage = docker.image('postgres:9.4')
          postgresContainer = postgresImage.run("--name db$imageCnt -e POSTGRES_PASSWORD=jax2016!")

          def wildflyImage = docker.image(imageName)
          wildflyContainer = wildflyImage.run("--name srv$imageCnt --link db$imageCnt:db$imageCnt")

        stage "configure server $imageName"
          // #2 replace with data source configuration
          wildflyImage.inside("--link srv$imageCnt:srv$imageCnt") {
            git url: 'https://github.com/getrostt/jax2016_demo.git'

            waitToBeStarted(imageCnt)

            def cliFiles = findFiles glob: '**/*.cli'
            for (int i = 0; i < cliFiles.size(); i++) {
              runCli(cliFiles[i].path, "srv$imageCnt", 'db', "db$imageCnt")
            }
          }

        stage "deploy application $imageName"
          echo 'Deploying application...'
          git url: 'https://github.com/getrostt/jax2016_demo.git'
          wildflyImage.inside("--link srv$imageCnt:srv$imageCnt") {
            // #1 change name of deployable
            deploy('deployables/jboss-kitchensink-angularjs-bootstrap-wo-datasource.war', "srv$imageCnt")
          }

        stage "test application $imageName"
          input 'Done?'
      } finally {
          wildflyContainer?.stop()
          postgresContainer?.stop()
      }
    }
  }
}
