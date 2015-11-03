package org.amdatu.dependencygraph.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Component and it's dependencies
 */
public class ComponentDescription {
	private final String name;
	private final List<String> dependencies = new ArrayList<>();
	
	public ComponentDescription(String name) {
		this.name = name;
	}
	
	public void addDependency(String dependency) {
		dependencies.add(dependency);
	}

	public String getName() {
		return name;
	}

	public List<String> getDependencies() {
		return dependencies;
	}
}
