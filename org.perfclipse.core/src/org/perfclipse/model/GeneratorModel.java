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

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Generator.Run;

public class GeneratorModel {
	
	public final static String PROPERTY_CLASS = "generator-class";
	public final static String PROPERTY_THREADS = "generator-threads";
	public final static String PROPERTY_RUN = "generator-run";
	public final static String PROPERTY_PROPERTY = "generator-property";
	
	private PropertyChangeSupport listeners;
	private Generator generator;
	
	public GeneratorModel(Generator generator){
		if (generator == null){
			throw new IllegalArgumentException("Generator must not be null");
		}
		this.generator = generator;
		listeners = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	/**
	 * This method should not be used for modifying generator (in a way getGenerator().setThreads(n))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Generator
	 */
	public Generator getGenerator(){
		return generator;
	}

	public void setThreads(String value) {
		String oldValue = getGenerator().getThreads();
		getGenerator().setThreads(value);
		listeners.firePropertyChange(PROPERTY_THREADS, oldValue, value);
	}

	public void setClazz(String value) {
		String oldValue = getGenerator().getClazz();
		getGenerator().setClazz(value);
		listeners.firePropertyChange(PROPERTY_CLASS, oldValue, value);
	}

	public void setRun(Run value) {
		Run oldValue = getGenerator().getRun();
		getGenerator().setRun(value);
		listeners.firePropertyChange(PROPERTY_RUN, oldValue, value);
	}
	
	public void addProperty(Property newProperty){
		getGenerator().getProperty().add(newProperty);
		listeners.firePropertyChange(PROPERTY_PROPERTY, null, newProperty);
	}
	
	public void removeProperty(Property property){
		if (getGenerator().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTY, property, null);
		}
		
	}

}
