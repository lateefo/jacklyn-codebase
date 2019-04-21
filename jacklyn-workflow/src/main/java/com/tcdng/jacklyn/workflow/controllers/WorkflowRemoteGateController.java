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

package com.tcdng.jacklyn.workflow.controllers;

import java.util.List;

import com.tcdng.jacklyn.common.annotation.Managed;
import com.tcdng.jacklyn.common.controllers.BaseRemoteCallController;
import com.tcdng.jacklyn.shared.workflow.WorkflowRemoteCallNameConstants;
import com.tcdng.jacklyn.shared.workflow.data.GetToolingEnrichmentLogicParams;
import com.tcdng.jacklyn.shared.workflow.data.GetToolingEnrichmentLogicResult;
import com.tcdng.jacklyn.shared.workflow.data.GetToolingItemClassifierLogicParams;
import com.tcdng.jacklyn.shared.workflow.data.GetToolingItemClassifierLogicResult;
import com.tcdng.jacklyn.shared.workflow.data.GetToolingPolicyLogicParams;
import com.tcdng.jacklyn.shared.workflow.data.GetToolingPolicyLogicResult;
import com.tcdng.jacklyn.shared.workflow.data.PublishWfCategoryParams;
import com.tcdng.jacklyn.shared.workflow.data.PublishWfCategoryResult;
import com.tcdng.jacklyn.shared.workflow.data.SubmitPackableDocParams;
import com.tcdng.jacklyn.shared.workflow.data.ToolingEnrichmentLogicItem;
import com.tcdng.jacklyn.shared.workflow.data.ToolingItemClassifierLogicItem;
import com.tcdng.jacklyn.shared.workflow.data.ToolingPolicyLogicItem;
import com.tcdng.jacklyn.workflow.business.WorkflowService;
import com.tcdng.jacklyn.workflow.constants.WorkflowModuleNameConstants;
import com.tcdng.jacklyn.workflow.data.WfTaggedMappingDef;
import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.Configurable;
import com.tcdng.unify.core.data.PackableDoc;
import com.tcdng.unify.core.data.TaggedMessage;
import com.tcdng.unify.web.TaggedMessageResult;
import com.tcdng.unify.web.annotation.GatewayAction;

/**
 * Workflow module remote call controller.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Managed(module = WorkflowModuleNameConstants.WORKFLOW_MODULE)
@Component("/workflow/gate")
public class WorkflowRemoteGateController extends BaseRemoteCallController {

    @Configurable
    private WorkflowService workflowService;

    @GatewayAction(
            name = WorkflowRemoteCallNameConstants.PUBLISH_WORKFLOW_CATEGORY,
            description = "$m{workflow.gate.remotecall.publishwfcategory}")
    public PublishWfCategoryResult publishWfCategory(PublishWfCategoryParams params) throws UnifyException {
        workflowService.executeWorkflowCategoryPublicationTask(null, params.getWfCategoryXml(), params.isActivate());
        return new PublishWfCategoryResult();
    }

    @GatewayAction(
            name = WorkflowRemoteCallNameConstants.SUBMIT_WORKFLOW_PACKABLEDOC,
            description = "$m{workflow.gate.remotecall.submitworkflowdocument}")
    public TaggedMessageResult submitWorkflowDocument(SubmitPackableDocParams msg) throws UnifyException {
        TaggedMessage taggedMessage = msg.getTaggedMessage();
        WfTaggedMappingDef wfTaggedMappingDef = workflowService.getRuntimeWfTaggedMappingDef(taggedMessage.getTag());
        PackableDoc packableDoc =
                PackableDoc.unpack(wfTaggedMappingDef.getWfDocDef().getDocConfig(), taggedMessage.getMessage());
        workflowService.submitToWorkflow(wfTaggedMappingDef.getWfTemplateDef().getGlobalName(), packableDoc);
        return TaggedMessageResult.SUCCESS;
    }

    @GatewayAction(
            name = WorkflowRemoteCallNameConstants.GET_TOOLING_ITEMCLASSIFIER_LOGIC_LIST,
            description = "$m{workflow.gate.remotecall.gettoolingclassifierlogic}")
    public GetToolingItemClassifierLogicResult getToolingItemClassifierLogicList(
            GetToolingItemClassifierLogicParams params) throws UnifyException {
        List<ToolingItemClassifierLogicItem> list = workflowService.findToolingItemClassifierLogicTypes();
        return new GetToolingItemClassifierLogicResult(list);
    }

    @GatewayAction(
            name = WorkflowRemoteCallNameConstants.GET_TOOLING_ENRICHMENT_LOGIC_LIST,
            description = "$m{workflow.gate.remotecall.gettoolingenrichmentlogic}")
    public GetToolingEnrichmentLogicResult getToolingEnrichmentLogicList(GetToolingEnrichmentLogicParams params)
            throws UnifyException {
        List<ToolingEnrichmentLogicItem> list = workflowService.findToolingEnrichmentLogicTypes();
        return new GetToolingEnrichmentLogicResult(list);
    }

    @GatewayAction(
            name = WorkflowRemoteCallNameConstants.GET_TOOLING_POLICY_LOGIC_LIST,
            description = "$m{workflow.gate.remotecall.gettoolingpolicylogic}")
    public GetToolingPolicyLogicResult getToolingPolicyLogicList(GetToolingPolicyLogicParams params)
            throws UnifyException {
        List<ToolingPolicyLogicItem> list = workflowService.findToolingPolicyLogicTypes();
        return new GetToolingPolicyLogicResult(list);
    }
}
