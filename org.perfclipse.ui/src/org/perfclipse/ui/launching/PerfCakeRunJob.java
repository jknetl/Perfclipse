package org.perfclipse.ui.launching;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.perfcake.PerfCakeConst;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.scenario.ScenarioException;
import org.perfclipse.core.scenario.ScenarioManager;
import org.perfclipse.ui.Activator;

final public class PerfCakeRunJob extends Job{
	
	static final Logger log = Activator.getDefault().getLogger();
	

	private static final long CHECK_INTERVAL = 500;
	private IFile file;
	private MessageConsole console;
	private MessageConsoleStream errorStream;
	private List<SystemProperty> properties;

	/**
	 * 
	 * @param name
	 * @param file
	 * @param console
	 * @param sysProperties
	 */
	public PerfCakeRunJob(String name, IFile file, MessageConsole console, List<SystemProperty> sysProperties) {
		super(name);
		if (file == null){
			log.warn("File with scenario is null.");
			throw new IllegalArgumentException("File with scenario is null.");
		}
		if (console == null){
			log.warn("Console for scenario run  output is null.");
			throw new IllegalArgumentException("Console for scenario run output is null."); 
		} 
		this.file = file;
		this.console = console;
		this.properties = sysProperties;
		errorStream = console.newMessageStream();
	}

	
	@Override
	public boolean belongsTo(Object family) {
		if (PerfCakeLaunchConstants.PERFCAKE_RUN_JOB_FAMILY.equals(family)){
			return true;	
		}
		
		return false;
	}


	@Override
	protected IStatus run(IProgressMonitor monitor) {

		//redirect System.out to eclipse console
		OutputStream out = console.newOutputStream();
		PrintStream standardOut = System.out;
		System.setOut(new PrintStream(out));
		
		//Add appender for Log4j to eclipse console
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();

		Appender appender = rootLogger.getAppender("CONSOLE");
		if (appender instanceof ConsoleAppender){
			ConsoleAppender consoleAppender = (ConsoleAppender) rootLogger.getAppender("CONSOLE");
			consoleAppender.activateOptions();
		} else{
			log.warn("Cannot obtain PerfCake console logger. Output will not be redirected to Eclipse console");
		}
		


		//set message and scenario paths for PerfCake 
		IFolder messageDir = file.getProject().getFolder("messages");
		IFolder scenarioDir = file.getProject().getFolder("scenarios");
		System.setProperty(PerfCakeConst.MESSAGES_DIR_PROPERTY, messageDir.getRawLocation().toString());
		System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, scenarioDir.getRawLocation().toString());
		ScenarioManager scenarioManager = new ScenarioManager();

		try {
			monitor.beginTask("PerfCake task", 100);
			Thread perfCakeThread = new Thread(new PerfCakeRun(scenarioManager, file.getLocationURI().toURL()));
			perfCakeThread.start();
			while(perfCakeThread.isAlive()){
				//TODO: get RunInfo and set progress
				if (monitor.isCanceled()){
					scenarioManager.stopScenario();
				}
				try {
					Thread.sleep(CHECK_INTERVAL);
				} catch (InterruptedException e) {
					log.warn("Sleep exception. Doing busy waiting.", e);
				}
			}
			monitor.done();
		} catch (MalformedURLException e) {
			log.warn("Wrong url to scenario.", e);
		} finally {
			System.setOut(standardOut); //set System.out to standard output
			try {
				out.close();
			} catch (IOException e) {
				log.warn("Cannot close stream to eclipse consolse!", e);
			}
		}
		return Status.OK_STATUS;
	}
	
	final class PerfCakeRun implements Runnable{

		private ScenarioManager manager;
		private URL scenarioURL;
		
		public PerfCakeRun(ScenarioManager manager, URL scenarioURL) {
			super();
			this.manager = manager;
			this.scenarioURL = scenarioURL;
		}

		@Override
		public void run() {
			try {
				for (SystemProperty p : properties){
					System.setProperty(p.getName(), p.getValue());
				}
				manager.runScenario(scenarioURL);
			} catch (ScenarioException e) {
				log.error("Cannot run scenario", e);
				errorStream.print("Cannot run scenario. See error log for more details.");
			} finally{
				//unset properties
				for (SystemProperty p : properties){
					System.clearProperty(p.getName());
				}
			}
		}
	}
}