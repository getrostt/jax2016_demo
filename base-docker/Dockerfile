# Use latest jboss/base-jdk:7 image as the base
FROM ubuntu:14.04
MAINTAINER Tobias Getrost <tobias.getrost@1und1.de>

# Setup java and tools
RUN apt-get update \
  && apt-get install -y curl tar software-properties-common postgresql-client \
  && echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections \
  && add-apt-repository -y ppa:webupd8team/java \
  && apt-get update \
  && apt-get install -y oracle-java8-installer \
  && rm -rf /var/lib/apt/lists/* \
  && rm -rf /var/cache/oracle-jdk8-installer
  
