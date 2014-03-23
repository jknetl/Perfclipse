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

package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.window.Window;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.wizards.MessagesEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class MessagesEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private MessagesModel messages;

	/**
	 * @param messages
	 */
	public MessagesEditPolicy(MessagesModel messages) {
		super();
		this.messages = messages;
	}

	@Override
	protected Command createPropertiesCommand() {
		MessagesEditWizard wizard = new MessagesEditWizard(messages);
		if (Utils.showWizardDialog(wizard) == Window.OK){
			CompoundCommand command = wizard.getCommand();
		
			if (!command.isEmpty()){
				return command;
			}
		}

		return null;

	}
	
}