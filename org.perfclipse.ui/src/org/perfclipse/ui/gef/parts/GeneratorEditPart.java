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

package org.perfclipse.ui.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.window.Window;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.GeneratorEditPolicy;
import org.perfclipse.ui.wizards.GeneratorEditWizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO : move implements to the superclass
public class GeneratorEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

	static final Logger log = LoggerFactory.getLogger(GeneratorEditPart.class);

	public GeneratorEditPart(GeneratorModel generatorModel) {
		setModel(generatorModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getGeneratorModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getGeneratorModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	public GeneratorModel getGeneratorModel(){
		return (GeneratorModel) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		
		ColorUtils colorUtils = ColorUtils.getInstance();
		TwoPartRectangle figure = new TwoPartRectangle(getText(), 
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
		return figure;
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN ||
				req.getType() == RequestConstants.REQ_DIRECT_EDIT){
			
			GeneratorEditWizard wizard = new GeneratorEditWizard(getGeneratorModel());
			if (Utils.showWizardDialog(wizard) == Window.OK){
				CompoundCommand command = wizard.getCommand();
				if (!command.isEmpty()){
					getViewer().getEditDomain().getCommandStack().execute(command);
				}
			}
		}
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GeneratorEditPolicy(getGeneratorModel()));

	}
	
	@Override
	protected String getText(){
		return getGeneratorModel().getGenerator().getClazz() 
				+ " (" + getGeneratorModel().getGenerator().getThreads() + ")";
	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		ModelMapper mapper = getGeneratorModel().getMapper();
		if (getGeneratorModel().getGenerator().getRun() != null)
			modelChildren.add(mapper.getModelContainer(getGeneratorModel().getGenerator().getRun()));

		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(GeneratorModel.PROPERTY_CLASS) ||
				evt.getPropertyName().equals(GeneratorModel.PROPERTY_THREADS)){
			refreshVisuals();
		}	
		if (evt.getPropertyName().equals(GeneratorModel.PROPERTY_RUN) ||
				evt.getPropertyName().equals(GeneratorModel.PROPERTY_PROPERTY)){
			refreshChildren();
		}
	}
}
