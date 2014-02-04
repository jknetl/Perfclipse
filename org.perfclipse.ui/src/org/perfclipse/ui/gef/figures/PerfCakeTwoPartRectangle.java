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

package org.perfclipse.ui.gef.figures;


import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;


public class PerfCakeTwoPartRectangle extends PerfCakeRoundedRectangle {
	
	private Figure headerLayer;
	private Figure contentLayer;

	public PerfCakeTwoPartRectangle(String name, Dimension defaultSize) {
		super(name);
		FlowLayout layout = new FlowLayout();
		layout.setMajorAlignment(FlowLayout.ALIGN_CENTER);
		setLayoutManager(layout);
		
		headerLayer = new Figure();
		contentLayer = new Figure();
		
		contentLayer.setPreferredSize(defaultSize.width - PerfCakeRoundedRectangle.getClientAreaInsets().getWidth(), 0);

		FlowLayout headerLayout = new FlowLayout();
		headerLayout.setMajorAlignment(FlowLayout.ALIGN_CENTER);
		FlowLayout contentLayout = new FlowLayout();
		
		headerLayer.setLayoutManager(headerLayout);
		contentLayer.setLayoutManager(contentLayout);
		
		add(headerLayer);
		add(contentLayer);
		
		Label nameLabel = new Label();
		nameLabel.setText(name);
		headerLayer.add(nameLabel);
		
	}

	public Figure getHeaderLayer() {
		return headerLayer;
	}

	public Figure getContentLayer() {
		return contentLayer;
	}
}