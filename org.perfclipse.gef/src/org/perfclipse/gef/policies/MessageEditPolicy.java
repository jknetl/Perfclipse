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
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.window.Window;
import org.perfcake.model.Header;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.core.commands.AddHeaderCommand;
import org.perfclipse.core.commands.AddValidatorRefCommand;
import org.perfclipse.core.commands.DeleteMessageCommand;
import org.perfclipse.core.commands.DeleteValidatorRefCommand;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.MessagesModel;
import org.perfclipse.wizards.HeaderAddWizard;
import org.perfclipse.wizards.MessageEditWizard;
import org.perfclipse.wizards.ValidatorAttachWizard;
import org.perfclipse.wizards.WizardUtils;

public class MessageEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private MessagesModel messages;
	private MessageModel message;

	public MessageEditPolicy(MessagesModel messages, MessageModel message) {
		super();
		this.messages = messages;
		this.message = message;
	}

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		DeleteMessageCommand deleteMessage = new DeleteMessageCommand(messages, message);
		CompoundCommand command = new CompoundCommand(deleteMessage.getLabel());
		
		if (message.getMessage().getValidatorRef() != null){
			for (ValidatorRef ref : message.getMessage().getValidatorRef()){
				Command c = new DeleteValidatorRefCommand(message, ref);
				command.add(c);
			}
		}
		
		command.add(deleteMessage);
		
		return command;
	}

	@Override
	protected Command createPropertiesCommand(Request request) {
	
		MessageEditWizard wizard = new MessageEditWizard(message);
		if (WizardUtils.showWizardDialog(wizard) == Window.OK){
			CompoundCommand command = wizard.getCommand();
			if (!command.isEmpty()){
				return command;
			}
		}
		return null;
	}

	@Override
	protected Command createAddHeaderCommand(Request request) {
		HeaderAddWizard wizard = new HeaderAddWizard();
		
		if (WizardUtils.showWizardDialog(wizard) != Window.OK)
			return null;
		
		Header h = new ObjectFactory().createHeader();
		h.setName(wizard.getName());
		h.setValue(wizard.getValue());
		
		return new AddHeaderCommand(message, h);
	}

	@Override
	protected Command createAttachValidatorCommand(Request request) {
		if (messages.getMapper().getValidation() == null)
			return null;
		ValidatorAttachWizard wizard = new ValidatorAttachWizard(messages.getMapper().getValidation());
		if (WizardUtils.showWizardDialog(wizard) != Window.OK)
			return null;
		if (wizard.getValidatorRef() == null && wizard.getValidatorRef().isEmpty())
			return null;
		
		CompoundCommand c = new CompoundCommand("Attach validator");
		c.add(wizard.getCommand());
		for (ValidatorRef ref : wizard.getValidatorRef()){
			c.add(new AddValidatorRefCommand(message, ref));
		}
		
		return c;
	}

	
	
}
