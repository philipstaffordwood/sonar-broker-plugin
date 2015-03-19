package org.sonarqube.newlanguage.example;

import java.util.List;



import org.sonar.api.batch.bootstrap.ProjectBuilder;
import org.sonar.api.batch.bootstrap.ProjectDefinition;
import org.sonar.api.batch.bootstrap.ProjectReactor;
import org.sonar.api.config.Settings;


public class BrokerProjectBuilder extends ProjectBuilder{

	  private Settings settings;

	  public BrokerProjectBuilder( Settings settings) {

	    this.settings = settings;
	  }
	  @Override
	  public void build(Context context) {

	    ProjectReactor reactor = context.projectReactor();
	    ProjectDefinition root =reactor.getRoot();
	    for (ProjectDefinition moduleDef : reactor.getProjects()) {
	      List<String> sources = moduleDef.sources();
	      for (String source:sources) {
	    	  ProjectDefinition newProj = ProjectDefinition.create();
	    	  newProj.setName(source);
	    	  newProj.setKey(source);
	    	  root.addSubProject(newProj);
	      }
	      
	    }
	  }

	
	
}
