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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.layout.ScenarioFreeformLayout;

public class ScenarioEditPart extends AbstractPerfCakeEditPart {

//	private static final int BORDER_PADDING = 1;

	
	public ScenarioEditPart(ScenarioModel scenarioModel){
		setModel(scenarioModel);
	}

	@Override
	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		ScenarioFreeformLayout layout = new ScenarioFreeformLayout();
		figure.setLayoutManager(layout);
//		figure.setBorder(new MarginBorder(BORDER_PADDING));

		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		modelChildren.add(getScenarioModel().getGenerator());
		modelChildren.add(getScenarioModel().getSender());
		if (getScenarioModel().getMessages() != null)
			modelChildren.add(getScenarioModel().getMessages());
		if (getScenarioModel().getValidation() != null)
			modelChildren.add(getScenarioModel().getValidation());
		if (getScenarioModel().getReporting() != null)
			modelChildren.add(getScenarioModel().getReporting());
		return modelChildren;
	}
	
	public ScenarioModel getScenarioModel(){
		return (ScenarioModel) getModel();
	}
	
	@Override
	protected void addChild(EditPart child, int index){
		ScenarioFreeformLayout layout = (ScenarioFreeformLayout) getFigure().getLayoutManager();

		if (child instanceof AbstractGraphicalEditPart){
			
			Rectangle constraint;
			if ((constraint = layout.getDefaultConstraint(child)) != null){
				layout.setConstraint(((AbstractGraphicalEditPart) child).getFigure(), constraint);
			}
		}
		super.addChild(child, index);
		if (child instanceof AbstractGraphicalEditPart){
			if (child instanceof ValidationEditPart)
				layout.resizeSiblings(child); 
			//TODO: move to refresh?
		}
		
	}
}
