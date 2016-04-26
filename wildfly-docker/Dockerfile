FROM hap/base-jdk8
MAINTAINER Tobias Getrost <tobias.getrost@1und1.de>

# build time arguments
ARG WILDFLY_VERSION

# Set the WILDFLY_VERSION env variable
ENV WILDFLY_VERSION $WILDFLY_VERSION

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN mkdir -p /opt/jboss \
  && cd /opt/jboss \
  && curl http://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
  | tar zx \
  && mv /opt/jboss/wildfly-$WILDFLY_VERSION /opt/jboss/wildfly

# Set the JBOSS_HOME env variable
ENV JBOSS_HOME /opt/jboss/wildfly

# add prostgresql jdbc driver
ADD postgresql-9.4.1208.jre7.jar $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/postgresql-9.4.1208.jre7.jar
ADD module.xml $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/module.xml

# add JBoss management user
RUN $JBOSS_HOME/bin/add-user.sh -u jax -p mySecretPassword -s

# Expose the ports we're interested in
EXPOSE 8080 9990 4447

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
