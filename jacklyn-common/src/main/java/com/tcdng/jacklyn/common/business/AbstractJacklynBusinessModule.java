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
package com.tcdng.jacklyn.common.business;

import java.util.ArrayList;
import java.util.List;

import com.tcdng.jacklyn.common.constants.JacklynApplicationAttributeConstants;
import com.tcdng.jacklyn.common.constants.JacklynSessionAttributeConstants;
import com.tcdng.unify.core.ApplicationComponents;
import com.tcdng.unify.core.UnifyComponent;
import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.UserToken;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.Configurable;
import com.tcdng.unify.core.annotation.Tooling;
import com.tcdng.unify.core.business.AbstractBusinessModule;
import com.tcdng.unify.core.system.ParameterBusinessModule;
import com.tcdng.unify.core.util.AnnotationUtils;
import com.tcdng.unify.core.util.ReflectUtils;

/**
 * Abstract base class for Jacklyn business module implementations.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public abstract class AbstractJacklynBusinessModule extends AbstractBusinessModule
		implements JacklynBusinessModule {

	private static final Class<?>[] TOOLING_DESCRIBABLE_PARAM_TYPE = { String.class, String.class };

	@Configurable(ApplicationComponents.APPLICATION_PARAMETERBUSINESSMODULE)
	private ParameterBusinessModule parameterBusinessModule;

	@Override
	public void clearCache() throws UnifyException {

	}

	protected ParameterBusinessModule getParameterBusinessModule() {
		return parameterBusinessModule;
	}

	@Override
	protected UserToken getUserToken() throws UnifyException {
		UserToken userToken = super.getUserToken();
		if (userToken == null) {
			return (UserToken) getApplicationAttribute(
					JacklynApplicationAttributeConstants.DEFAULT_SYSTEM_USERTOKEN);
		}
		return userToken;
	}

	protected Long getUserBranchId() throws UnifyException {
		return (Long) getSessionAttribute(JacklynSessionAttributeConstants.BRANCHID);
	}

	protected <T, U extends UnifyComponent> List<T> getToolingTypes(Class<T> itemClass,
			Class<U> type) throws UnifyException {
		List<T> list = new ArrayList<T>();
		for (Class<? extends U> typeClass : getAnnotatedClasses(type, Tooling.class)) {
			Component ca = typeClass.getAnnotation(Component.class);
			if (ca != null) {
				Tooling ta = typeClass.getAnnotation(Tooling.class);
				list.add(ReflectUtils.newInstance(itemClass, TOOLING_DESCRIBABLE_PARAM_TYPE,
						AnnotationUtils.getComponentName(typeClass),
						resolveApplicationMessage(ta.value())));
			}
		}
		return list;
	}
}