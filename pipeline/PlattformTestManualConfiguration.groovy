node {
  def wildflyContainer = null
  try {
    stage 'start container'
      def wildflyImage = docker.image('hap/wildfly:8.1.0.Final')
      wildflyContainer = wildflyImage.run("--name srv01 -p 8180:8080 -p 9990:9990")

    stage 'configure server'
      input 'Please configure server. Ready to go on?'

    stage 'deploy application'
      echo 'Deploying application...'
      git url: 'https://github.com/getrostt/jax2016_demo.git'
      wildflyImage.inside('--link srv01:srv01') {
        deploy('deployables/jboss-kitchensink-angularjs-bootstrap.war', 'srv01')
      }

    stage 'test application'
      input 'Done?'
  } finally {
      wildflyContainer?.stop()
  }
}
