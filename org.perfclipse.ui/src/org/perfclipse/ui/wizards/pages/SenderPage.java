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

package org.perfclipse.ui.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.perfcake.model.Property;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.SenderModel;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.ComboUtils;
import org.perfclipse.ui.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.ui.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.ui.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.jface.StringComboViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Knetl
 *
 */
public class SenderPage extends AbstractPerfCakePage {
	
	private static final String SENDER_PAGE_NAME = "Sender";
	private Composite container;
	private Label senderLabel;
	private StringComboViewer senderTypeViewer;
	static final Logger log = LoggerFactory.getLogger(SenderPage.class);
	
	private TableViewer propertyViewer;
	private TableViewerControl propertyViewerControls;
	
	private SenderModel sender;
	private List<PropertyModel> properties;
	
	public SenderPage(){
		this(SENDER_PAGE_NAME, false);
	}
	
	public SenderPage(SenderModel sender){
		this(SENDER_PAGE_NAME, true);
		this.sender = sender;
		
		ModelMapper mapper = sender.getMapper();

		properties = new ArrayList<>(sender.getProperty().size());

		for (Property p : sender.getProperty()){
			properties.add((PropertyModel) mapper.getModelContainer(p));
		}
	}
	
	private SenderPage(String pageName, boolean edit) {
		super(pageName, edit);
		setTitle("Sender specification");
		setDescription("Set sender type and sender properties");
	}
	
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		container.setLayout(layout);
		
		senderLabel = new Label(container, SWT.NONE);
		senderLabel.setText("Choose sender");
		
		PerfCakeComponents components = getPerfCakeComponents();

		senderTypeViewer = new StringComboViewer(container, components.getSenderNames() );

		senderTypeViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		GridData senderComboGridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		senderComboGridData.horizontalSpan = 2;
		senderTypeViewer.getControl().setLayoutData(senderComboGridData);
		
		propertyViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		
		propertyViewerControls = new TableViewerControl(container, true, SWT.NONE);
		GridData tableControlsData = new GridData();
		tableControlsData.verticalAlignment = SWT.BEGINNING;
		propertyViewerControls.setLayoutData(tableControlsData);

		propertyViewerControls.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getEditingSupportCommands(), propertyViewer, sender));
		propertyViewerControls.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getEditingSupportCommands(), propertyViewer, sender));
		propertyViewerControls.getEditButton().addSelectionListener(
				new EditPropertySelectionAdapter(getEditingSupportCommands(), propertyViewer));
		
		final Table propertyTable = propertyViewer.getTable();
		GridData propertyTableData = Utils.getTableViewerGridData();
		propertyTableData.horizontalSpan = 2;
		propertyTable.setLayoutData(propertyTableData);
		
		setControl(container);
		super.createControl(parent);
	}
	
	
	
	@Override
	protected void fillDefaultValues() {
		StructuredSelection sel = new StructuredSelection(senderTypeViewer.getElementAt(0));
		senderTypeViewer.setSelection(sel);
	}

	@Override
	protected void fillCurrentValues() {
		
		if (sender.getSender().getClazz() != null)
			ComboUtils.select(senderTypeViewer, sender.getSender().getClazz());
		
		if (properties != null){
			propertyViewer.setInput(properties);
		}
	}

	@Override
	protected void updateControls() {
		StructuredSelection sel = (StructuredSelection) senderTypeViewer.getSelection();
		if (sel == null ||
				"".equals(sel.getFirstElement())){
			setDescription("Select sender type!");
			setPageComplete(false);
		}else{
			setDescription("Complete!");
			setPageComplete(true);
		}
		

		super.updateControls();
	}

	public String getSenderName(){
		StructuredSelection sel = (StructuredSelection) senderTypeViewer.getSelection();
		return (String) sel.getFirstElement();
	}

	public TableViewer getPropertyViewer() {
		return propertyViewer;
	}
	
}
