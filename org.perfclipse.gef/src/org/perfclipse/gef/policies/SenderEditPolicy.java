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

package org.perfclipse.gef.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.window.Window;
import org.perfclipse.core.model.SenderModel;
import org.perfclipse.wizards.SenderEditWizard;
import org.perfclipse.wizards.WizardUtils;

/**
 * @author Jakub Knetl
 *
 */
public class SenderEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private SenderModel sender;

	public SenderEditPolicy(SenderModel sender) {
		super();
		this.sender = sender;
	}
	
	@Override
	protected Command createPropertiesCommand(Request request) {
		SenderEditWizard wizard = new SenderEditWizard(sender);
		if (WizardUtils.showWizardDialog(wizard) == Window.OK){
			CompoundCommand command = wizard.getCommand();
			if (!command.isEmpty()){
				return command;
			}
		}
		
		return null;
	}
}
