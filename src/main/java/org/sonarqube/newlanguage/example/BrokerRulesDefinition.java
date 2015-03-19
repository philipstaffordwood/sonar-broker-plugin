package org.sonarqube.newlanguage.example;

import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RuleParamType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

public class BrokerRulesDefinition implements RulesDefinition {

  // @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository("broker_default",
        "broker").setName("Default Broker Rules");

    NewRule x1Rule = createRule(repository);

    NewRule errorHandlingRule = createErrorHandlingRule(repository);

    // define a rule programmatically. Note that rules
    // could be loaded from files (JSON, XML, ...)
    NewRule x2Rule = repository.createRule("x2").setName("We hate 'XML' in a name")
        .setHtmlDescription("Generate an issue for no purpose at all. In this case because 'XML' is a four letter word.")

        // optional tags
        .setTags("style", "stupid")

        // optional status. Default value is READY.
        .setStatus(RuleStatus.BETA)

        // default severity when the rule is activated on a Quality
        // profile. Default value is MAJOR.
        .setSeverity(Severity.MAJOR);

    x2Rule.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
        .setDebtRemediationFunction(
            x1Rule.debtRemediationFunctions().linearWithOffset(
                "0h", "30min"));

    x2Rule.createParam("acceptWhitespace").setDefaultValue("false")
        .setType(RuleParamType.BOOLEAN)
        .setDescription("Accept whitespaces on the line");

    // don't forget to call done() to finalize the definition
    repository.done();
  }

  private NewRule createRule(NewRepository repository) {
    // define a rule programmatically. Note that rules
    // could be loaded from files (JSON, XML, ...)
    NewRule x1Rule = repository.createRule("x1").setName("Dummy Issue")
        .setHtmlDescription("Generate an issue for no purpose at all.")

        // optional tags
        .setTags("style", "stupid")

        // optional status. Default value is READY.
        .setStatus(RuleStatus.BETA)

        // default severity when the rule is activated on a Quality
        // profile. Default value is MAJOR.
        .setSeverity(Severity.MINOR);

    x1Rule.setDebtSubCharacteristic("INTEGRATION_TESTABILITY")
        .setDebtRemediationFunction(
            x1Rule.debtRemediationFunctions().linearWithOffset(
                "1h", "30min"));

    x1Rule.createParam("acceptWhitespace").setDefaultValue("false")
        .setType(RuleParamType.BOOLEAN)
        .setDescription("Accept whitespaces on the line");
    return x1Rule;
  }

  private NewRule createErrorHandlingRule(NewRepository repository) {
    // define a rule programmatically. Note that rules
    // could be loaded from files (JSON, XML, ...)
    NewRule x1Rule = repository.createRule("noerr").setName("No Common ErrorHandler used.")
        .setHtmlDescription("The Standard Error handler is not used.")

        // optional tags
        .setTags("error-handling")

        // optional status. Default value is READY.
        .setStatus(RuleStatus.BETA)

        // default severity when the rule is activated on a Quality
        // profile. Default value is MAJOR.
        .setSeverity(Severity.BLOCKER);

    x1Rule.setDebtSubCharacteristic("INTEGRATION_")
        .setDebtRemediationFunction(
            x1Rule.debtRemediationFunctions().linearWithOffset(
                "0h", "10min"));

    x1Rule.createParam("errorhandlingSubflowName").setDefaultValue("SF_EMO_Generic_Error_Handling")
        .setType(RuleParamType.STRING)
        .setDescription("The name of the default error handler.");
    return x1Rule;
  }
}
