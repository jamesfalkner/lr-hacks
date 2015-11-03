/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dependency.graph.rest;

import com.liferay.dependency.graph.rest.json.DependentDescription;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.namespace.PackageNamespace;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
		immediate = true,
		property = {
				"json.web.service.context.name=dependency-graph-rest",
				"json.web.service.context.path=/dependency-graph-rest"
		},
		service = Object.class
)
@JSONWebService
public class Dependencies {

	public List<DependentDescription> packageGraph() {
		List<DependentDescription> result = new ArrayList<>();

		for (Bundle bundle : _bundleContext.getBundles()) {
			DependentDescription description =
					new DependentDescription(bundle.toString(), bundle.getHeaders().toString().replaceAll("\\s+,\\s+", "\n"));

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			List<BundleWire> requiredWires = bundleWiring.getRequiredWires(
					PackageNamespace.PACKAGE_NAMESPACE);

			for (BundleWire bundleWire : requiredWires) {
				description.addDependency(
						bundleWire.getProvider().getBundle().toString());
			}

			result.add(description);
		}

		return result;
	}

	public List<DependentDescription> serviceGraph() {
		List<DependentDescription> result = new ArrayList<>();

		for (Bundle bundle : _bundleContext.getBundles()) {
			String name = bundle.toString();
			String notes = bundle.getHeaders().toString().replaceAll("\\s+,\\s+", "\n");
			DependentDescription description = new DependentDescription(name, notes);

			ServiceReference<?>[] servicesInUse = bundle.getServicesInUse();
			System.out.println("bundle " + bundle.getSymbolicName() + " has " + ((servicesInUse != null)  ? servicesInUse.length : "*undefined*") + " dependencies");
			if (servicesInUse == null) {
				continue;
			}

			for (ServiceReference<?> serviceInUse : servicesInUse) {
				Bundle serviceBundle = serviceInUse.getBundle();

				if ((serviceBundle == null) || bundle.equals(serviceBundle)) {
					continue;
				}

				description.addDependency(serviceBundle.toString());
			}

			result.add(description);
		}

		System.out.println("returning result: " + result);
		return result;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

}