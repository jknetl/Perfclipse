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

package org.perfclipse.gef.policies.directedit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.core.commands.EditReporterTypeCommand;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.gef.figures.ILabeledFigure;

/**
 * @author Jakub Knetl
 *
 */
public class RenameReporterDirectEditPolicy extends ClassDirectEditPolicy {
	
	private ReporterModel model;
	
	

	public RenameReporterDirectEditPolicy(ReporterModel model,
			ILabeledFigure labeledFigure) {
		super(labeledFigure);
		if (model == null){
			throw new IllegalArgumentException("Model cannot be null");
		}
		this.model = model;
	}



	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		String newName = (String) request.getCellEditor().getValue();
		if (newName != null){
			return new EditReporterTypeCommand(model, newName);
		}
		return null;
	}

}
