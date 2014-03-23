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

package org.perfclipse.ui.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.perfclipse.model.HeaderModel;

/**
 * @author Jakub Knetl
 *
 */
public class HeaderPage extends AbstractPerfCakePage {

	private static final String HEADER_PAGE_NAME = "Property";

	private Composite container;
	private Label nameLabel;
	private Text nameText;

	private Label valueLabel;
	private Text valueText;
	
	private HeaderModel header;

	
	
	public HeaderPage(){
		this(HEADER_PAGE_NAME, false);
	}
	
	public HeaderPage(HeaderModel header){
		this(HEADER_PAGE_NAME, true);
		this.header = header;
	}
	

	/**
	 * @param pageName
	 * @param edit
	 */
	private HeaderPage(String pageName, boolean edit) {
		super(pageName, edit);
	}
	
	@Override
	public void createControl(Composite parent) {
		setTitle("Property wizard");
		setDescription("Fill in name and value.");
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData data;
		layout.numColumns = 2;
		container.setLayout(layout);
		
		nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Name: ");
		
		nameText = new Text(container, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		nameText.setLayoutData(data);
		
		
		valueLabel = new Label(container, SWT.NONE);
		valueLabel.setText("Value: ");
		
		valueText = new Text(container, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		valueText.setLayoutData(data);
		
		setControl(container);
	}

	
	
	@Override
	protected void fillCurrentValues() {
		nameText.setText(header.getHeader().getName());
		valueText.setText(header.getHeader().getValue());
		super.fillCurrentValues();
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public Text getNameText() {
		return nameText;
	}

	public Label getValueLabel() {
		return valueLabel;
	}

	public Text getValueText() {
		return valueText;
	}
	
	

}