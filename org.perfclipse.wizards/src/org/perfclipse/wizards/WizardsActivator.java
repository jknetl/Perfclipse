package org.perfclipse.wizards;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.perfclipse.core.logging.Logger;

/**
 * The activator class controls the plug-in life cycle
 */
public class WizardsActivator extends AbstractUIPlugin {

	private Logger logger;

	// The plug-in ID
	public static final String PLUGIN_ID = "org.perfclipse.wizards"; //$NON-NLS-1$

	// The shared instance
	private static WizardsActivator plugin;
	
	/**
	 * The constructor
	 */
	public WizardsActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static WizardsActivator getDefault() {
		return plugin;
	}

	public Logger getLogger() {
		return logger;
	}
	
	

}
