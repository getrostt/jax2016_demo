// vars/runCli.groovy
// need to be run inside a docker container
def call(cliFile, serverName, key, value) {
  echo "Running CLI $cliFile"
  sh "sed -i 's#@$key@#$value#g' $cliFile"
  sh "/opt/jboss/wildfly/bin/jboss-cli.sh -c --controller=$serverName:9990 --user=jax --password=mySecretPassword --file=$cliFile"
}
