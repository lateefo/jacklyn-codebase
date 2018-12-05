/*
 * Copyright 2018 The Code Department
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.tcdng.jacklyn.workflow.data;

import com.tcdng.unify.core.data.PackableDoc;

/**
 * Manual workflow item.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class ManualWfItem implements ViewableWfItem {

	private WfStepDef wfStepDef;

	private PackableDoc pd;

	public ManualWfItem(WfStepDef wfStepDef, PackableDoc pd) {
		this.wfStepDef = wfStepDef;
		this.pd = pd;
	}

	@Override
	public String getTitle() {
		return wfStepDef.getDescription();
	}

	@Override
	public String getDocViewer() {
		return wfStepDef.getDocViewer();
	}

	@Override
	public PackableDoc getPd() {
		return pd;
	}

	@Override
	public WfStepDef getWfStepDef() {
		return wfStepDef;
	}
}