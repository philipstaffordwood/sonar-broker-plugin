package org.sonarqube.newlanguage.example;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public final class BrokerMetrics implements Metrics {



  public static final Metric<Integer> NODE_COUNT = new Metric.Builder("node_count", "Node Count", Metric.ValueType.INT)
  .setDescription("Number of Nodes in FLows")
  .setDirection(Metric.DIRECTION_NONE)
  .setQualitative(false)
  .setDomain(CoreMetrics.DOMAIN_SIZE)
  .create();
  
  
  // getMetrics() method is defined in the Metrics interface and is used by
  // Sonar to retrieve the list of new metrics
  public List<Metric> getMetrics() {
    return Arrays.<Metric>asList( NODE_COUNT);
  }
}