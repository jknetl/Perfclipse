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

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.perfclipse.model.HeaderModel;
import org.perfclipse.model.MessageModel;
import org.perfclipse.ui.gef.commands.DeleteHeaderCommand;

/**
 * @author Jakub Knetl
 *
 */
public class DeleteHeaderSelectionAdapter extends
		AbstractCommandSelectionAdapter {

	private TableViewer viewer;
	private MessageModel message;
	private HeaderModel header;
	
	
	/**
	 * @param commands
	 * @param viewer
	 * @param message
	 * @param header
	 */
	public DeleteHeaderSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessageModel message) {
		super(commands);
		this.viewer = viewer;
		this.message = message;
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection){
			StructuredSelection structSelection = (StructuredSelection) selection;
			Iterator<?> it = structSelection.iterator();
			while (it.hasNext()){
				header = (HeaderModel) it.next();
				super.handleCommand();
				viewer.remove(header);
			}
		}
		super.widgetSelected(e);
	}


	@Override
	protected Command getCommand() {
		if (message != null && header != null){
			new DeleteHeaderCommand(message, header.getHeader());
		}
		return null;
	}

}