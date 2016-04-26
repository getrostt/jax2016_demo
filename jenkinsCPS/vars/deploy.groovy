// vars/deploy.groovy
// need to be run inside a docker container
def call(deploymentFileName, serverName) {
  sh "/opt/jboss/wildfly/bin/jboss-cli.sh -c \"deploy $deploymentFileName --force\" --controller=$serverName:9990 --user=jax --password=mySecretPassword"
}
