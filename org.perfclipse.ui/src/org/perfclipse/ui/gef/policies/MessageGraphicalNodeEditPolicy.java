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
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.MessageModel;
import org.perfclipse.ui.gef.commands.AddValidatorRefCommand;

/**
 * @author Jakub Knetl
 *
 */
public class MessageGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	private MessageModel message;
	
	/**
	 * @param message
	 */
	public MessageGraphicalNodeEditPolicy(MessageModel message) {
		super();
		this.message = message;
	}

	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		return null;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		if (request.getNewObjectType() == ValidatorRef.class){
			Command command = new AddValidatorRefCommand(message, null);
			request.setStartCommand(command);

			return command;
		}
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return null;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return null;
	}
	
	
}