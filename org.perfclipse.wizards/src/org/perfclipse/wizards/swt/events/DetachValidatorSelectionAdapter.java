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

package org.perfclipse.wizards.swt.events;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.perfclipse.core.commands.DeleteValidatorRefCommand;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.ValidatorRefModel;

/**
 * @author Jakub Knetl
 *
 */
public class DetachValidatorSelectionAdapter extends
		AbstractDeleteCommandSelectionAdapter {

	private MessageModel message;
	private ValidatorRefModel ref;

	public DetachValidatorSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessageModel message) {
		super(commands, viewer);
		this.message = message;
	}

	@Override
	protected Command getCommand() {
		if (message != null && ref != null){
			return new DeleteValidatorRefCommand(message, ref.getValidatorRef());
		}
		return null;
	}

	@Override
	public void handleDeleteData(Object element) {
		ref = (ValidatorRefModel) element;
		
	}
}
