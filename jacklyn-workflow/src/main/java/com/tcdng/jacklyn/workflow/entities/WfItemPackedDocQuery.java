/*
 * Copyright 2018-2020 The Code Department.
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
package com.tcdng.jacklyn.workflow.entities;

import java.util.Collection;

import com.tcdng.jacklyn.common.entities.BaseTimestampedEntityQuery;

/**
 * Workflow item packed document query.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class WfItemPackedDocQuery extends BaseTimestampedEntityQuery<WfItemPackedDoc> {

    public WfItemPackedDocQuery() {
        super(WfItemPackedDoc.class);
    }

    public WfItemPackedDocQuery wfItemId(Long wfItemId) {
        return (WfItemPackedDocQuery) addEquals("wfItemId", wfItemId);
    }

    public WfItemPackedDocQuery wfItemIdIn(Collection<Long> wfItemId) {
        return (WfItemPackedDocQuery) addAmongst("wfItemId", wfItemId);
    }

}
