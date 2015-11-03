package org.amdatu.dependencygraph;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.amdatu.dependencygraph.json.ComponentDescription;
import org.apache.felix.dm.Component;
import org.apache.felix.dm.Dependency;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

@Path("dependencies")
public class DependenciesResource {

	/**
	 * @return Lists all components registered in DM with their dependencies.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComponentDescription> graph() {

		List<ComponentDescription> result = new ArrayList<>();

		for (Object managerObj : DependencyManager.getDependencyManagers()) {
			DependencyManager manager = (DependencyManager) managerObj;

			for (Object o : manager.getComponents()) {
				Component c = (Component) o;
				ServiceRegistration serviceRegistration = c.getServiceRegistration();

				if (serviceRegistration == null) {
					continue;
				}

				ServiceReference reference = serviceRegistration.getReference();
				
				String[] objectClass = (String[]) reference.getProperty("ObjectClass");
				String componentName = createComponentName(objectClass);
				
				//Use BSN for services that are registered with an Object interface
				if(componentName.equals("java.lang.Object")) {
					componentName = reference.getBundle().getSymbolicName();
				}

				ComponentDescription description = new ComponentDescription(componentName);

				List<?> dependencies = c.getDependencies();
				for (Object dependencyObj : dependencies) {
					Dependency dep = (Dependency) dependencyObj;

					try {
						String[] dependencyName = (String[]) dep.getProperties().get("objectClass");

						description.addDependency(createComponentName(dependencyName));
					} catch (IllegalStateException ex) {
						//Ignore dependency when it's name can't be figured out.
					}
				}

				result.add(description);
			}
		}

		return result;

	}

	private String createComponentName(String[] objectClass) {
		if(objectClass == null) {
			return "";
		}
		StringBuilder componentName = new StringBuilder();
		
		for (String name : objectClass) {
			if (componentName.length() > 0) {
				componentName.append(" ,");
			}

			componentName.append(name);
		}
		return componentName.toString();
	}
}
