package com.liferay.devhacks; /**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Raymond Augé
 */
public class UtilLocatorHack {

	public static UtilLocatorHack getInstance() {
		return _instance;
	}

	public Object findUtil(String utilName) {
		Object bean = null;

		try {
			bean = PortalBeanLocatorUtil.locate(_getUtilName(utilName));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	public Object findUtil(String servletContextName, String utilName) {
		Object bean = null;

		try {
			bean = PortletBeanLocatorUtil.locate(
					servletContextName, _getUtilName(utilName));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	private UtilLocatorHack() {
	}

	private String _getUtilName(String utilName) {
		if (!utilName.endsWith(ServiceLocatorHack.VELOCITY_SUFFIX)) {
			utilName += ServiceLocatorHack.VELOCITY_SUFFIX;
		}

		return utilName;
	}

	private static Log _log = LogFactoryUtil.getLog(UtilLocatorHack.class);

	private static UtilLocatorHack _instance = new UtilLocatorHack();

}