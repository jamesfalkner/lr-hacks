package com.liferay.dependency.graph.rest.json;

import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.List;

public class DependentDescription {

	public DependentDescription(String name, String notes) {
		_name = name;
		_notes = notes;
	}

	public void addDependency(String dependency) {
		if (_dependencies.contains(dependency)) {
			return;
		}

		_dependencies.add(dependency);
	}

	@JSON
	public String getName() {
		return _name;
	}

	@JSON
	public String getNotes() {
		return _notes;
	}

	@JSON
	public List<String> getDependencies() {
		return _dependencies;
	}

	private final List<String> _dependencies = new ArrayList<>();
	private final String _name;
	private final String _notes;

}