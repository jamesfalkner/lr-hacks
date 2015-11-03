package com.liferay.devhacks;

import freemarker.ext.beans.BeansWrapper;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class LiferayObjectConstructorHack implements TemplateMethodModelEx {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments)
			throws TemplateModelException {

		if (arguments.isEmpty()) {
			throw new TemplateModelException(
					"This method must have at least one argument as the name of " +
							"the class to instantiate");
		}

		Class<?> clazz = null;

		try {
			String className = String.valueOf(arguments.get(0));

			clazz = Class.forName(
					className, true, LiferayObjectConstructorHack.class.getClassLoader());
		}
		catch (Exception e) {
			throw new TemplateModelException(e.getMessage());
		}

		BeansWrapper beansWrapper = BeansWrapper.getDefaultInstance();

		Object object = beansWrapper.newInstance(
				clazz, arguments.subList(1, arguments.size()));

		return beansWrapper.wrap(object);
	}

}