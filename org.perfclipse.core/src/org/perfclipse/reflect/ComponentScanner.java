package org.perfclipse.reflect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.perfcake.message.generator.AbstractMessageGenerator;
import org.perfcake.message.sender.AbstractSender;
import org.perfcake.reporting.destinations.Destination;
import org.perfcake.reporting.reporters.Reporter;
import org.perfcake.validation.MessageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentScanner {
	
	final static Logger log = LoggerFactory.getLogger(ComponentScanner.class);
	private static final String PERFCAKE_BUNDLE = "org.perfcake";
	private static final String PERFCAKE_SENDER_PACKAGE = "org.perfcake.message.sender";
	private static final String PERFCAKE_GENERATOR_PACKAGE = "org.perfcake.message.generator";
	private static final String PERFCAKE_REPORTER_PACKAGE = "org.perfcake.reporting.reporters";
	private static final String PERFCAKE_DESTINATION_PACKAGE = "org.perfcake.reporting.destinations";
	private static final String PERFCAKE_VALIDATOR_PACKAGE = "org.perfcake.validation";


	private static ComponentScanner instance;
	
	private Set<Class<? extends AbstractSender>> senders;
	private Set<Class<? extends AbstractMessageGenerator>> generators;
	private Set<Class<? extends Reporter>> reporters;
	private Set<Class<? extends Destination>> destinations;
	private Set<Class<? extends MessageValidator>> validators;
	
	private ComponentScanner() throws PerfClipseScannerException{
		senders = scanType(PERFCAKE_SENDER_PACKAGE, AbstractSender.class);
		generators = scanType(PERFCAKE_GENERATOR_PACKAGE, AbstractMessageGenerator.class);
		reporters = scanType(PERFCAKE_REPORTER_PACKAGE, Reporter.class);
		destinations = scanType(PERFCAKE_DESTINATION_PACKAGE, Destination.class);
		validators = scanType(PERFCAKE_VALIDATOR_PACKAGE, MessageValidator.class);
		log.debug("Components included in PerfCake has been loaded.");
	}
	
	public static ComponentScanner getInstance() throws PerfClipseScannerException{
		if (instance == null){
			instance = new ComponentScanner();
		}
		
		return instance;
	}
	
	
	
	
	public Set<Class<? extends AbstractSender>> getSenders() {
		return senders;
	}

	public Set<Class<? extends AbstractMessageGenerator>> getGenerators() {
		return generators;
	}

	public Set<Class<? extends Reporter>> getReporters() {
		return reporters;
	}

	public Set<Class<? extends Destination>> getDestinations() {
		return destinations;
	}

	public Set<Class<? extends MessageValidator>> getValidators() {
		return validators;
	}

	//TODO : remove duplicate information about type (both T and componentType)
	private <T> Set<Class<? extends T>> scanType(String packageName, Class<T> componentType) throws PerfClipseScannerException{
		Set<Class<? extends T>> classes = new HashSet<>();
		String packagePath = File.separator + packageName.replace(".", File.separator);
		
		Bundle bundle = Platform.getBundle(PERFCAKE_BUNDLE);
		URL bundleUrl = bundle.getEntry(packagePath);
		URL pathUrl = null;
		try {
			pathUrl = FileLocator.toFileURL(bundleUrl);
		} catch (IOException e) {
			log.error("Cannot obtain URL to package", e);
			throw new PerfClipseScannerException("Cannot obtain URL to package", e);
		}
		
		try {
			File packageDir = new File(pathUrl.toURI());
			
			if (packageDir.exists()){
				String[] files = packageDir.list();
				for (String filename : files){
					// TODO: if directory then recursion ???
					if (filename.endsWith(".class")){
						//trim .class extension
						filename = filename.substring(0, filename.length() - 6);
						try {
							Class<?> clazz = Class.forName(packageName + "." + filename);
							//if class is subclass of componentType
							if (componentType.isAssignableFrom(clazz)){
								// if class is not abstract
								Class<? extends T> component = clazz.asSubclass(componentType);
								if (!Modifier.isAbstract(clazz.getModifiers()))
									classes.add(component);
							}
						} catch (ClassNotFoundException e) {
							log.error("Cannot obtain class file for given file", e );
							throw new PerfClipseScannerException("Cannot obtain class file for given file", e);
						}
					}
				}
			}
		} catch (URISyntaxException e) {
			log.error("Cannot open file with package URI", e);
			throw new PerfClipseScannerException("Cannot open file with package URI", e);
		}
		
		return classes;
	}

}