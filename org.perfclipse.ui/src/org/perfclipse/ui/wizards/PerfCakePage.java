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

package org.perfclipse.ui.wizards;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.reflect.PerfClipseScannerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Knetl
 *
 */
public abstract class PerfCakePage extends WizardPage {
	
	static Logger logger = LoggerFactory.getLogger(PerfCakePage.class);

	public PerfCakePage(String pageName) {
		super(pageName);
	}

	public PerfCakePage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}
	
	protected PerfCakeComponents getPerfCakeComponents(){
		PerfCakeComponents components = null;
		try {
			components = PerfCakeComponents.getInstance();
		} catch (PerfClipseScannerException e) {
			logger.error("Cannot parse PerfCake components", e);
			MessageDialog.openError(getShell(), "Cannot parse PerfCake components",
					"Automatically loaded components from PerfCake will not be available");
		}
		
		return components;
	}
	
	/**
	 * Method which updates control of the wizard.
	 * It should be called from widget listeners when user change input.
	 * It could e. g. be used for marking page complete.
	 */
	protected void updateControls(){
		// do nothing
	}
	
	protected static class UpdateSelectionAdapter extends SelectionAdapter{
		
		PerfCakePage page;
		public UpdateSelectionAdapter(PerfCakePage page){
			this.page = page;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			page.updateControls();
		}
		
	}

}
