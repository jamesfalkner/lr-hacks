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

public class ServiceLocatorHack {

	public static ServiceLocatorHack getInstance() {
		return _instance;
	}

	public Object findService(String serviceName) {
		Object bean = null;

		try {
			bean = PortalBeanLocatorUtil.locate(_getServiceName(serviceName));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	public Object findService(String servletContextName, String serviceName) {
		Object bean = null;

		try {
			bean = PortletBeanLocatorUtil.locate(
					servletContextName, _getServiceName(serviceName));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	private ServiceLocatorHack() {
	}

	public static final String VELOCITY_SUFFIX = ".velocity";

	private String _getServiceName(String serviceName) {
		if (!serviceName.endsWith(VELOCITY_SUFFIX)) {
			serviceName += VELOCITY_SUFFIX;
		}

		return serviceName;
	}

	private static Log _log = LogFactoryUtil.getLog(ServiceLocatorHack.class);

	private static ServiceLocatorHack _instance = new ServiceLocatorHack();

}