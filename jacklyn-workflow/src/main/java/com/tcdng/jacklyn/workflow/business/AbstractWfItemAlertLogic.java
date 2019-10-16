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

package com.tcdng.jacklyn.workflow.business;

import com.tcdng.jacklyn.notification.business.NotificationService;
import com.tcdng.jacklyn.system.business.SystemService;
import com.tcdng.unify.core.annotation.Configurable;

/**
 * Convenient abstract base class for workflow item alert logic.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public abstract class AbstractWfItemAlertLogic extends AbstractWfItemPolicy implements WfItemAlertLogic {

    @Configurable
    private SystemService systemService;

    @Configurable
    private NotificationService notificationService;

    protected SystemService getSystemService() {
        return systemService;
    }

    protected NotificationService getNotificationService() {
        return notificationService;
    }
}
