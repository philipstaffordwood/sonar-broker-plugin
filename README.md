# sonar-broker-plugin
Experiment of creating a SonarQube (http://www.sonarqube.org/) plugin for a new language.

Currently: Working, but rough (pre-alpha) code for creating a customSonarQube plugin for a new language/system.

The system/language is IBM Integration Bus or Broker.
(http://www-03.ibm.com/software/products/en/ibm-integration-bus)

So far only messageflows and not ESQL files are covered using the IBM Integration API rather than parsing of source file.

Currently the plugin needs to be built from source 
to allow the inclusion of the IBM Integration API libraries 
(I'm not certain about the redistribution status yet).
