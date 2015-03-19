/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonarqube.newlanguage.example;

import com.google.common.collect.ImmutableList;
import org.sonar.api.CoreProperties;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.Properties;
import org.sonar.api.Property;

import java.util.List;

@Properties({
	  @Property(
	    key = BrokerPlugin.MY_PROPERTY,
	    name = "Plugin Property",
	    description = "A property for the plugin",
	    defaultValue = "Hello World!")})
public class BrokerPlugin extends SonarPlugin {
	public static final String MY_PROPERTY = "sonar.example.myproperty";
  private static final String BROKER_CATEGORY = "broker";
  private static final String GENERAL_SUBCATEGORY = "General";

  //public static final String SQUID_ANALYSE_ACCESSORS_PROPERTY = "sonar.squid.analyse.property.accessors";
  //public static final boolean SQUID_ANALYSE_ACCESSORS_DEFAULT_VALUE = true;

  //@Override
  public List getExtensions() {
    ImmutableList.Builder<Object> builder = ImmutableList.builder();

    builder.add(
 
        PropertyDefinition.builder(Broker.FILE_SUFFIXES_KEY)
            .defaultValue(Broker.DEFAULT_FILE_SUFFIXES)
            .name("File suffixes")
            .description("Comma-separated list of suffixes for files to analyze. To not filter, leave the list empty.")
            .subCategory("General")
            .onQualifiers(Qualifiers.PROJECT)
            .build(),
            Broker.class,
            /*DummyBrokerScanner.class,*/
            BrokerMetrics.class,
            BrokerSensor.class,
            //BrokerProjectBuilder.class,
            BrokerRulesDefinition.class,
            XmlCodeColorizerFormat.class
            );
    return builder.build();
  }

}
