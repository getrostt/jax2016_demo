// vars/waitToBeStarted.groovy
// need to be run inside a docker container
def call(serverName) {
  sh "for i in {1..5}; do curl --silent -I http://$serverName:8080 && break || sleep 10; done"
}
