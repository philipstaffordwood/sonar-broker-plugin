package org.sonarqube.newlanguage.example;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.Project;
import org.sonar.api.scan.filesystem.ModuleFileSystem;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.batch.Phase;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.checks.AnnotationCheckFactory;

@Phase(name = Phase.Name.PRE)
public class DummyBrokerScanner implements Sensor{
	private final FileSystem fileSystem;
	private final RulesProfile profile;
	
	  public static final Metric<String> MESSAGE = new Metric.Builder("message_key", "Message", Metric.ValueType.STRING)
	    .setDescription("This is a metric to store a well known message")
	    .setDirection(Metric.DIRECTION_WORST)
	    .setQualitative(false)
	    .setDomain(CoreMetrics.DOMAIN_GENERAL)
	    .create();

	  public DummyBrokerScanner(RulesProfile profile,  FileSystem fileSystem) {
		    this.fileSystem = fileSystem;
		    this.profile = profile;
		  }
	  
	  
	public boolean shouldExecuteOnProject(Project project) {
		// TODO Auto-generated method stub
	    return fileSystem.hasFiles(fileSystem.predicates().hasLanguage(Broker.KEY));
	}

	public void analyse(Project module, SensorContext context) {
		// TODO Auto-generated method stub
		Measure measure = new Measure(MESSAGE);
		context.saveMeasure(measure);

		
	}

}
