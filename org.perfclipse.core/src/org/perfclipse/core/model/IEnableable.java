/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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

package org.perfclipse.core.model;

/**
 * Interface that implements switch behaviour. It means that model
 * can be enabled or disabled
 * @author Jakub Knetl
 *
 */
public interface IEnableable {

	/**
	 * Sets flag enabled or disabled
	 * @param enabled
	 */
	public abstract void setEnabled(boolean enabled);
	
	/**
	 * 
	 * @return if flag is enabled or disabled
	 */
	public abstract boolean isEnabled();
}
