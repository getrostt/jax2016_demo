<pre>


      _   _    __  __  ____   ___  _  __
     | | / \   \ \/ / |___ \ / _ \/ |/ /_  
  _  | |/ _ \   \  /    __) | | | | | '_ \
 | |_| / ___ \  /  \   / __/| |_| | | (_) |
  \___/_/   \_\/_/\_\ |_____|\___/|_|\___/



</pre>

# Von Schwergewichten und Walfischen - Plattformtests mit Docker: Demos zum Vortrag auf der JAX 2016

## Vorbereitung
1. Docker Container erstellen
   1. Base Docker Image
   ```
   cd base-docker
   docker build -t hap/base-jdk8 .
   ```
   2. Wildfly 8.1.0.Final Image
   ```
   cd wildfly-docker
   docker build --build-arg WILDFLY_VERSION=8.1.0.Final -t hap/wildfly:8.1.0.Final .
   ```
   3. Wildfly 10.0.0.Final Image
   ```
   cd wildfly-docker
   docker build --build-arg WILDFLY_VERSION=10.0.0.Final -t hap/wildfly:10.0.0.Final .
   ```
2. Jenkins herunterladen oder eine vorhandene Installation verwenden (Homepage: https://jenkins.io/)
   * Benötigte Plugins:
      * CloudBees Docker Pipeline
      * Git plugin
      * Pipeline
      * Pipeline Utility Steps
      * Pipeline: Stage View Plugin
3. Jenkins Pipeline Global Functions hinzufügen (Tutorial: https://github.com/jenkinsci/workflow-cps-global-lib-plugin)
   1. Mit git folgende URL clonen (LOCATION mit URL zum Jenkins ersetzen)
   ```
   http://LOCATION/workflowLibs.git
   ```
   2. In diesem Repository die Scripte aus dem Ordner JenkinsCPS hinzufügen
   3. Änderungen commiten und zum Jenkins pushen

## Demo 1: Docker Container starten
```
docker run --rm --name demo1 -p 8180:8080 -p 9990:9990 hap/wildfly:8.1.0.Final
```
## Demo 2: Build Pipeline mit Docker und manueller Konfiguration
In Jenkins die folgende Datei aus dem GIT Repository als Pipeline verwenden:
```
pipeline/PlattformTestManualConfiguration.groovy
```

## Demo 3: Build Pipeline mit Docker und automatischer Konfiguration
In Jenkins die folgende Datei aus dem GIT Repository als Pipeline verwenden:
```
pipeline/PlattformTestAutoConfiguration.groovy
```

## Demo 4: Mehrere Pipeline parallel
In Jenkins die folgende Datei aus dem GIT Repository als Pipeline verwenden:
```
pipeline/PlattformTestParallelExecution.groovy
```
