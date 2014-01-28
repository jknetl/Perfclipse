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

package org.perfclipse.ui.editors;


import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.slf4j.LoggerFactory;

public class ScenarioMultiPageEditor extends MultiPageEditorPart implements IResourceChangeListener{
	
	

	private static final String XML_EDITOR_TAB_LABEL = "XML editor";

	private static final String DESIGN_EDITOR_TAB_LABEL = "Design editor";

	final static org.slf4j.Logger log = LoggerFactory.getLogger(ScenarioMultiPageEditor.class);

	private StructuredTextEditor textEditor;
	private int textEditorIndex;
	
	private ScenarioDesignEditor designEditor;
	private int designEditorIndex;

	public ScenarioMultiPageEditor(){
		super();

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException{
		
		if (!(input instanceof IFileEditorInput)){
			throw new RuntimeException("Wrong editor input. Must be file editor input");
		}
		super.init(site, input);
	}
	
	@Override
	protected void createPages() {
		createDesignEditorPage();
		createTextEditorPage();
		setPartName(getEditorInput().getName());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditor(textEditorIndex).doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		IEditorPart editor = getEditor(textEditorIndex);
		editor.doSaveAs();
		setPageText(textEditorIndex, editor.getTitle());
		setPartName(getEditorInput().getName());
		setInput(editor.getEditorInput());

	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	private void createDesignEditorPage() {
		try {
			designEditor = new ScenarioDesignEditor();
			IFileEditorInput input = (IFileEditorInput) getEditorInput();
			ScenarioDesignEditorInput editorInput = new ScenarioDesignEditorInput(input.getFile());
			designEditorIndex = addPage(designEditor, editorInput);
			setPageText(designEditorIndex, DESIGN_EDITOR_TAB_LABEL);
		} catch (PartInitException e) {
			log.warn("Cannot create Scenario design editor: " + e);
			ErrorDialog.openError(getSite().getShell(), "Error",
					"Error creating scenario design editor.", e.getStatus());
		}


	}

	private void createTextEditorPage() {
		try{
		textEditor = new StructuredTextEditor();
		textEditorIndex = addPage(textEditor, getEditorInput());
		setPageText(textEditorIndex, XML_EDITOR_TAB_LABEL);
		} catch (PartInitException e){
			log.warn("Cannot create Scenario text editor: " + e);
			ErrorDialog.openError(getSite().getShell(), "Error",
					"Error creating scenario text editor.", e.getStatus());
			
		}
	}

	/**
	 * Closes all project files on project close.
	 */
	@Override
	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				@Override
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i<pages.length; i++){
						if(((FileEditorInput) textEditor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							IEditorPart editorPart = pages[i].findEditor(textEditor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
	}
	

}