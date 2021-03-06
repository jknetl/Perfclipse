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

package org.perfclipse.gef.parts;

import org.eclipse.draw2d.IFigure;
import org.perfclipse.gef.figures.ILabeledFigure;
import org.perfclipse.gef.figures.TwoPartRectangle;


public abstract class AbstractPerfCakeNodeEditPart extends  AbstractPerfCakeEditPart{


	@Override
	public IFigure getContentPane() {
		if (getFigure() instanceof TwoPartRectangle){
			return ((TwoPartRectangle) getFigure()).getContentLayer();
		}
		return super.getContentPane();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();

		if (getFigure() instanceof ILabeledFigure){
			((ILabeledFigure) getFigure()).getLabel().setText(getText());
		}
	}

	/**
	 * 
	 * @return text displayed on the figure
	 */
	protected String getText(){
		return "";
	}
}
