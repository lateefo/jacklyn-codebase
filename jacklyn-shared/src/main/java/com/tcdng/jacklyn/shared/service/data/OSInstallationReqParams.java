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
package com.tcdng.jacklyn.shared.service.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.tcdng.jacklyn.shared.service.ServiceRemoteCallNameConstants;
import com.tcdng.unify.web.RemoteCallParams;

/**
 * OS installation request parameters.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@XmlRootElement
public class OSInstallationReqParams extends RemoteCallParams {

	private String osName;

	public OSInstallationReqParams(String osName) {
		super(ServiceRemoteCallNameConstants.OS_REQUEST_INSTALL);
		this.osName = osName;
	}

	public OSInstallationReqParams() {
		super(ServiceRemoteCallNameConstants.OS_REQUEST_INSTALL);
	}

	public String getOsName() {
		return osName;
	}

	@XmlElement(required = true)
	public void setOsName(String osName) {
		this.osName = osName;
	}
}