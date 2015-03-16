# sonar-broker-plugin
Experiment of creating a SonarQube (http://www.sonarqube.org/) plugin for a new language.

Currently: Working, but rough (pre-alpha) code for creating a customSonarQube plugin for a new language/system.

The system/language is IBM Integration Bus or Broker.
(http://www-03.ibm.com/software/products/en/ibm-integration-bus)

So far only messageflows and not ESQL files are covered using the IBM Integration API (rather than parsing of source files).

Currently the plugin needs to be built from source 
to allow the inclusion of the IBM Integration API libraries 
(I'm not certain about the redistribution status yet).

Licensing is LGPL 3.0.
I'd be happy for the code to be used, 
but I expect changes and further development to be incorporated back to increase the value of the code base.
I'm planning, however, to put in clear plug points to allow non-LGPL code rules and metrics to be added to allow 
use where proprietary and private measures, rules and metrics would be needed.
