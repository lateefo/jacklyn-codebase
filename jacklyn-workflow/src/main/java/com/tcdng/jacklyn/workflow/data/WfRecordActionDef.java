/*
 * Copyright 2018-2019 The Code Department.
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

import com.tcdng.jacklyn.shared.workflow.WorkflowRecordActionType;

/**
 * Workflow record action definition.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class WfRecordActionDef extends BaseWfDef {

    private static final long serialVersionUID = 6228193786297652259L;

    private WorkflowRecordActionType actionType;

    private WfDocBeanMappingDef beanMapping;

    public WfRecordActionDef(String name, String description, WorkflowRecordActionType actionType,
            WfDocBeanMappingDef beanMapping) {
        super(name, description);
        this.actionType = actionType;
        this.beanMapping = beanMapping;
    }

    public WorkflowRecordActionType getActionType() {
        return actionType;
    }

    public WfDocBeanMappingDef getBeanMapping() {
        return beanMapping;
    }
}
