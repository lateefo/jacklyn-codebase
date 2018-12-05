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
package com.tcdng.jacklyn.common.controllers;

import java.text.MessageFormat;
import java.util.List;

import com.tcdng.jacklyn.common.business.ReportProvider;
import com.tcdng.jacklyn.common.constants.JacklynApplicationAttributeConstants;
import com.tcdng.jacklyn.common.constants.JacklynSessionAttributeConstants;
import com.tcdng.jacklyn.common.data.ReportOptions;
import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.database.Entity;
import com.tcdng.unify.core.logging.EventType;
import com.tcdng.unify.core.task.TaskMonitor;
import com.tcdng.unify.core.task.TaskSetup;
import com.tcdng.unify.core.util.StringUtils;
import com.tcdng.unify.web.AbstractPageController;
import com.tcdng.unify.web.annotation.Action;
import com.tcdng.unify.web.ui.data.MessageBox;
import com.tcdng.unify.web.ui.data.MessageIcon;
import com.tcdng.unify.web.ui.data.MessageMode;
import com.tcdng.unify.web.ui.data.MessageResult;
import com.tcdng.unify.web.ui.data.SearchBox;
import com.tcdng.unify.web.ui.data.TaskMonitorInfo;
import com.tcdng.unify.web.ui.panel.TableCrudPanel;

/**
 * Common base class for all page controllers.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public abstract class BasePageController extends AbstractPageController {

	public BasePageController(boolean secured, boolean readOnly) {
		super(secured, readOnly);
	}

	@Action
	@Override
	public String confirm() throws UnifyException {
		String msg = getRequestContextUtil().getRequestConfirmMessage();
		String param = getRequestContextUtil().getRequestConfirmParam();
		if (!StringUtils.isBlank(param)) {
			msg = MessageFormat.format(msg, param);
		}

		return showMessageBox(MessageIcon.QUESTION, MessageMode.YES_NO,
				getSessionMessage("messagebox.confirmation"), msg, "/confirmResult");
	}

	@Action
	public String confirmResult() throws UnifyException {
		if (MessageResult.YES.equals(getMessageResult())) {
			return hidePopupFireConfirm();
		}

		return hidePopup();
	}

	/**
	 * Sets up a response that shows a message box with default info icon and OK
	 * button. {@link MessageBox} value of the session attribute
	 * {@link JacklynSessionAttributeConstants.MESSAGEBOX}.
	 * 
	 * @param message
	 *            the message to display
	 * @return response to show application message box
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String showMessageBox(String message) throws UnifyException {
		return showMessageBox(MessageIcon.INFO, MessageMode.OK,
				getSessionMessage("messagebox.message"), message, "/hidePopup");
	}

	/**
	 * Sets up a response that shows a message box with default info icon and OK
	 * button. {@link MessageBox} value of the session attribute
	 * {@link JacklynSessionAttributeConstants.MESSAGEBOX}.
	 * 
	 * @param message
	 *            the message to display
	 * @param actionPath
	 *            the action path
	 * @return response to show application message box
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String showMessageBox(String message, String actionPath) throws UnifyException {
		return showMessageBox(MessageIcon.INFO, MessageMode.OK,
				getSessionMessage("messagebox.message"), message, actionPath);
	}

	/**
	 * Sets up a response that shows a message box with default info icon and OK
	 * button. {@link MessageBox} value of the session attribute
	 * {@link JacklynSessionAttributeConstants.MESSAGEBOX}.
	 * 
	 * @param caption
	 *            the message caption
	 * @param message
	 *            the message to display
	 * @param actionPath
	 *            the action path
	 * @return response to show application message box
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String showMessageBox(String caption, String message, String actionPath)
			throws UnifyException {
		return showMessageBox(MessageIcon.INFO, MessageMode.OK, caption, message, actionPath);
	}

	/**
	 * Sets up a response that shows a message box. The message box is backed by the
	 * {@link MessageBox} value of the session attribute
	 * {@link JacklynSessionAttributeConstants.MESSAGEBOX}.
	 * 
	 * @param messageIcon
	 *            the message icon of enumeration type {@link MessageIcon}
	 * @param messageMode
	 *            the message mode of enumeration type {@link MessageMode}
	 * @param caption
	 *            the message caption
	 * @param message
	 *            the message to display
	 * @param actionPath
	 *            the action path
	 * @return response to show application message box
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String showMessageBox(MessageIcon messageIcon, MessageMode messageMode,
			String caption, String message, String actionPath) throws UnifyException {
		setSessionAttribute(JacklynSessionAttributeConstants.MESSAGEBOX,
				new MessageBox(messageIcon, messageMode, caption, message, getName() + actionPath));
		return "showapplicationmessage";
	}

	/**
	 * Shows a search box.
	 * 
	 * @param resultPath
	 *            the result URL
	 * @param mappings
	 *            the result mappings
	 * @return the show search mapping name
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String showSearchBox(String resultPath, SearchBox.Mapping... mappings)
			throws UnifyException {
		SearchBox searchBoxInfo = new SearchBox(this, resultPath);
		for (SearchBox.Mapping mapping : mappings) {
			searchBoxInfo.addMapping(mapping);
		}

		setSessionAttribute(JacklynSessionAttributeConstants.SEARCHBOX, searchBoxInfo);
		return "showapplicationsearch";
	}

	/**
	 * Launches a task and shows a monitoring box.
	 * 
	 * @param taskSetup
	 *            the task setup
	 * @param captionKey
	 *            the task monitor box caption key
	 * @return the show application monitor box result mapping name
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String launchTaskWithMonitorBox(TaskSetup taskSetup, String captionKey)
			throws UnifyException {
		return launchTaskWithMonitorBox(taskSetup, captionKey, null, null);
	}

	/**
	 * Launches a task and shows a monitoring box.
	 * 
	 * @param taskSetup
	 *            the task setup
	 * @param captionKey
	 *            the task monitor box caption key
	 * @param onSuccessPath
	 *            optional on task success forward to path
	 * @param onFailurePath
	 *            optional on task faile forward to path
	 * @return the show application monitor box result mapping name
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String launchTaskWithMonitorBox(TaskSetup taskSetup, String captionKey,
			String onSuccessPath, String onFailurePath) throws UnifyException {
		TaskMonitor taskMonitor = launchTask(taskSetup);
		if (taskSetup.getEventCode() != null) {
			logUserEvent(taskSetup.getEventCode(), taskSetup.getEventDetails());
		}

		TaskMonitorInfo taskMonitorInfo = new TaskMonitorInfo(taskMonitor,
				getSessionMessage(captionKey), onSuccessPath, onFailurePath);
		setSessionAttribute(JacklynSessionAttributeConstants.TASKMONITORINFO, taskMonitorInfo);
		return "showapplicationtaskmonitor";
	}

	/**
	 * Sets up a response that shows a report options box. The report options box is
	 * backed by supplied {@link ReportOptions} value of the session attribute
	 * {@link JacklynSessionAttributeConstants.REPORTOPTIONS}.
	 * 
	 * @param reportOptions
	 *            report options data object
	 * @return response to show application report options box
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected String showReportOptionsBox(ReportOptions reportOptions) throws UnifyException {
		setSessionAttribute(JacklynSessionAttributeConstants.REPORTOPTIONS, reportOptions);
		return "showapplicationreportoptions";
	}

	/**
	 * Logs a user event using event code with optional details.
	 * 
	 * @param eventName
	 *            the event code
	 * @param details
	 *            the event details
	 * @throws UnifyException
	 *             if event code is unknown. if an error occurs.
	 */
	protected void logUserEvent(String eventName, String... details) throws UnifyException {
		getRequestContextUtil().logUserEvent(eventName, details);
	}

	/**
	 * Logs a user event using event code with details.
	 * 
	 * @param eventName
	 *            the event code
	 * @param details
	 *            the event details
	 * @throws UnifyException
	 *             if event code is unknown. if an error occurs.
	 */
	protected void logUserEvent(String eventName, List<String> details) throws UnifyException {
		getRequestContextUtil().logUserEvent(eventName, details);
	}

	/**
	 * Logs a user event using supplied event and record type.
	 * 
	 * @param eventType
	 *            the event type
	 * @param entityClass
	 *            the record type
	 * @throws UnifyException
	 *             If an error occurs.
	 */
	protected void logUserEvent(EventType eventType, Class<? extends Entity> entityClass)
			throws UnifyException {
		getRequestContextUtil().logUserEvent(eventType, entityClass);
	}

	/**
	 * Logs a user event with associated record.
	 * 
	 * @param eventType
	 *            the event type
	 * @param record
	 *            the record object
	 * @param isNewRecord
	 *            indicates supplied record is new
	 * @throws UnifyException
	 *             if an error occurs.
	 */
	protected void logUserEvent(EventType eventType, Entity record, boolean isNewRecord)
			throws UnifyException {
		getRequestContextUtil().logUserEvent(eventType, record, isNewRecord);
	}

	/**
	 * Logs a user event with associated old and new record.
	 * 
	 * @param eventType
	 *            the event type
	 * @param oldRecord
	 *            the old record
	 * @param newRecord
	 *            the new record. Can be null
	 * @throws UnifyException
	 *             if audit type with supplied action is unknown. If an error
	 *             occurs.
	 */
	protected <T extends Entity> void logUserEvent(EventType eventType, T oldRecord, T newRecord)
			throws UnifyException {
		getRequestContextUtil().logUserEvent(eventType, oldRecord, newRecord);
	}

	/**
	 * Returns a table CRUD panel using supplied name.
	 * 
	 * @param shortName
	 *            the short name
	 * @throws UnifyException
	 *             if an error occurs
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Entity> TableCrudPanel<T> getTableCrudPanel(String shortName)
			throws UnifyException {
		return (TableCrudPanel<T>) getPageWidgetByShortName(TableCrudPanel.class, shortName);
	}

	/**
	 * Returns message result obtained from request.
	 * 
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected MessageResult getMessageResult() throws UnifyException {
		return getRequestTarget(MessageResult.class);
	}

	/**
	 * Returns the common report provider.
	 * 
	 * @throws UnifyException
	 *             if an error occurs
	 */
	protected ReportProvider getCommonReportProvider() throws UnifyException {
		return (ReportProvider) getApplicationAttribute(
				JacklynApplicationAttributeConstants.COMMON_REPORT_PROVIDER);
	}
}