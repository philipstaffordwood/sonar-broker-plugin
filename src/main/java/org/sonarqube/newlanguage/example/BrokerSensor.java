package org.sonarqube.newlanguage.example;



import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.sonarqube.newlanguage.example.BrokerMetrics;
import org.sonarqube.newlanguage.example.BrokerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.Issuable;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import com.ibm.broker.MessageBrokerAPIException;
import com.ibm.broker.config.appdev.MessageFlow;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.FlowRendererMSGFLOW;

public class BrokerSensor implements Sensor {

  private static final Logger LOG = LoggerFactory.getLogger(BrokerSensor.class);

  private Settings settings;
  private FileSystem fs;
  private final ResourcePerspectives perspectives;


  /**
   * Use of IoC to get Settings and FileSystem
   */
  public BrokerSensor(Settings settings, FileSystem fs, ResourcePerspectives perspectives) {
    this.settings = settings;
    this.fs = fs;
    this.perspectives = perspectives;

  }

  public BrokerSensor() {
    perspectives = null;
    // TODO Auto-generated constructor stub
  }

  public boolean shouldExecuteOnProject(Project project) {
    // This sensor is executed only when there are broker files
    //return fs.hasFiles(fs.predicates().hasLanguage("broker"));
    return true;
  }

  public void analyse(Project project, SensorContext sensorContext) {
    // This sensor create a measure for metric MESSAGE on each Java file
    String value = settings.getString(BrokerPlugin.MY_PROPERTY);
    LOG.info(BrokerPlugin.MY_PROPERTY + "=" + value);


    Integer nodeCountTotal = 0;
    for (InputFile inputFile : fs.inputFiles(fs.predicates().hasLanguage("broker"))) {
      LOG.info("file="+inputFile.file().getName());
      try {
        MessageFlow mf = getMessageFlow(inputFile.file());
        if(!verifyErrorHandling(mf)) {
          //TODO: maybe split class to handle metrics/issues separately?
          Issuable issuable = perspectives.as(Issuable.class, inputFile);
          issuable.addIssue(issuable.newIssueBuilder()
            .ruleKey(RuleKey.of("broker_default", "noerr"))
            .message("Correct Standard Error Handling Subflownot present.")
            .line(1)
            .build());
        }
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      Integer nodeCount = getNodeCount(inputFile.file());
      nodeCountTotal+=nodeCount;
        sensorContext.saveMeasure(inputFile, new Measure<Integer>(BrokerMetrics.NODE_COUNT, (double)nodeCount));





      }
    sensorContext.saveMeasure(project, new Measure<Integer>(BrokerMetrics.NODE_COUNT, (double)nodeCountTotal));



  }

  private MessageFlow getMessageFlow(File file) throws Exception {
    MessageFlow mf1 = null;

       mf1 = FlowRendererMSGFLOW.read(file);
       if (mf1==null) {
         throw new Exception("Failure to read MessageFlow.");
       }
       return mf1;

  }

  private Integer getNodeCount(File file) {
    Integer result = 0;
    try {
          MessageFlow mf1 = FlowRendererMSGFLOW.read(file);
          result = mf1.getNodes().size();
      } catch (IOException e) {
          // Add your own code here
          e.printStackTrace();
  //    } catch (MessageBrokerAPIException e) {
   //       // Add your own code here
    //      e.printStackTrace();
     }
  return result;
}
  private boolean verifyErrorHandling(MessageFlow mf) {
    Node errorSubflowNode = mf.getNodeByName("SF_EMO_Generic_Error_Handling");
    if(errorSubflowNode==null) {
      return false;
    }

    return true;
  }


@Override
  public String toString() {
    return getClass().getSimpleName();
  }

public static void main(String[] args) {
  // TODO Remove this this harness
  BrokerSensor instance = new BrokerSensor();
  instance.testMain();


}

private void testMain() {
  // TODO Auto-generated method stub
  try {
    MessageFlow mf = getMessageFlow(new File("C:\\code\\IIB9.0\\Audits\\ACBS_To_DomesticTreasuryRequest\\V1_0\\MF_Composite_ACBS_To_DomesticTreasury.msgflow"));
    boolean result = verifyErrorHandling(mf);
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
}

}
