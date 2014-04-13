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

package org.perfclipse.ui.swt.events;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.ui.wizards.ValidatorEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class EditValidatorSelectionAdapter extends
		AbstractEditCommandSelectionAdapter {
	
	private List<MessageModel> messages;

	public EditValidatorSelectionAdapter(List<Command> commands,
			TableViewer viewer) {
		super(commands, viewer);
	}

	@Override
	protected AbstractPerfCakeEditWizard createWizard(
			IStructuredSelection selection) {
		List<ValidatorModel> otherValidators = new ArrayList<>();
		for (TableItem i : getViewer().getTable().getItems()){
			if (i.getData() instanceof ValidatorModel){
				otherValidators.add((ValidatorModel) i.getData());
			}
		}
		return new ValidatorEditWizard((ValidatorModel) selection.getFirstElement(), messages, otherValidators);
	}

	public List<MessageModel> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageModel> messages) {
		this.messages = messages;
	}
}