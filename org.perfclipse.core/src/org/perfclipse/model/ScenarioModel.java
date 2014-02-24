/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2014 Jakub Knetl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfclipse.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.perfcake.model.Scenario;

public class ScenarioModel {
	
	public static final String PROPERTY_GENERATOR = "scenario-generator";
	public static final String PROPERTY_SENDER = "scenario-sender";
	public static final String PROPERTY_MESSAGES = "scenario-messages";
	public static final String PROPERTY_PROPERTIES = "scenario-properties";
	public static final String PROPERTY_REPORTING = "scenario-reporting";
	public static final String PROPERTY_VALIDATION = "scenario-validation";
	
	private PropertyChangeSupport listeners;
	private Scenario scenario;

	public ScenarioModel(Scenario scenario) {
		if (scenario == null){
			throw new IllegalArgumentException("Scenario must not be null");
		}
		this.scenario = scenario;
		listeners = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}

	/**
	 * This method should not be used for modifying scenario (in a way getScenario().setSender(sender))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of scenario
	 */
	public Scenario getScenario(){
		return scenario;
	}
	
	public void setSender(Scenario.Sender sender){
		Scenario.Sender oldSender = getScenario().getSender();
		getScenario().setSender(sender);
		listeners.firePropertyChange(PROPERTY_SENDER, oldSender, sender);
	}
	
	public void setGenerator(Scenario.Generator generator){
		Scenario.Generator oldGenerator = getScenario().getGenerator();
		getScenario().setGenerator(generator);
		listeners.firePropertyChange(PROPERTY_GENERATOR, oldGenerator, generator);
	}
	
	public void setReporting(Scenario.Reporting reporting){
		Scenario.Reporting oldReporting = getScenario().getReporting();
		getScenario().setReporting(reporting);
		listeners.firePropertyChange(PROPERTY_REPORTING, oldReporting, reporting);
	}
	
	public void setMessages(Scenario.Messages messages){
		Scenario.Messages oldMessages = getScenario().getMessages();
		getScenario().setMessages(messages);
		listeners.firePropertyChange(PROPERTY_MESSAGES, oldMessages, messages);
	}
	
	public void setValidation(Scenario.Validation validatrion){
		Scenario.Validation oldValidation = getScenario().getValidation();
		getScenario().setValidation(validatrion);
		listeners.firePropertyChange(PROPERTY_VALIDATION, oldValidation, validatrion);
	}
	
	public void setProperties(Scenario.Properties properties){
		Scenario.Properties oldProperties = getScenario().getProperties();
		getScenario().setProperties(properties);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, oldProperties, properties);
	}

}
