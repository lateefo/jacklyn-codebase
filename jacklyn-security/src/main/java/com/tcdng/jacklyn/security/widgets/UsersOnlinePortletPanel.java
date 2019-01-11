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

package com.tcdng.jacklyn.security.widgets;

import com.tcdng.jacklyn.system.widgets.AbstractQuickPercentagePortletPanel;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.UplAttribute;
import com.tcdng.unify.core.annotation.UplAttributes;
import com.tcdng.unify.core.annotation.UplBinding;

/**
 * Users online portlet panel.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component(name = "ui-usersonlineportlet", description = "$m{security.usersonline.portlet}")
@UplBinding("web/security/upl/usersonlineportletpanel.upl")
@UplAttributes({
        @UplAttribute(name = "provider", type = String.class, defaultValue = "usersonline-portletprovider") })
public class UsersOnlinePortletPanel extends AbstractQuickPercentagePortletPanel {

}