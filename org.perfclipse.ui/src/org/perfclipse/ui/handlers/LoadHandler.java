/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
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

package org.perfclipse.ui.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.perfclipse.scenario.*;


public class LoadHandler extends AbstractHandler {

	private final static Logger LOGGER = Logger.getLogger(LoadHandler.class .getName()); 
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		IFile file = Utils.getFirstSelectedFile(event);
		if (file != null){
			writeFile(file, shell);
		}
		else{
			MessageDialog.openError(shell, "Cannot open scenario", "Scenario has to be xml file which is valid"
					+ " according to given xml schema");
		}
		return null;
	}

	private void writeFile(IFile file, Shell shell) {
		URL scenarioUrl = null;
		ScenarioManager scenarioManager = null;
		try {
			scenarioUrl = file.getLocationURI().toURL();
		} catch (MalformedURLException e) {
			LOGGER.warning("Cannot open resource IFile: " + file.getFullPath());
			MessageDialog.openError(shell, "Error", "Cannot read selected file!");
			return;
		}
		try {
			scenarioManager = new ScenarioManager();
			scenarioManager.load(scenarioUrl);
			scenarioManager.save(System.out);
		} catch (ScenarioException | IOException e) {
			LOGGER.warning("Cannot parse sceanrio");
			MessageDialog.openError(shell, "Error", "Cannot parse selected file as scenario!");
		}
		
		
	}

}
