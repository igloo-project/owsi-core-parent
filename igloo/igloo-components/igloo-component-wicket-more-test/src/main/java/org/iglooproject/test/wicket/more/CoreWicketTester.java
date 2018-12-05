package org.iglooproject.test.wicket.more;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;

public class CoreWicketTester extends WicketTester {

	public CoreWicketTester(final WebApplication application) {
		super(application);
	}

	/**
	 * Assert that a Component is visible and of type clazz
	 */
	public <T extends Component> void assertVisible(String path, Class<T> clazz) {
		assertVisible(path);
		assertComponent(path, clazz);
	}

	/**
	 * Assert that a Component is visible and enabled
	 */
	public void assertUsability(String path) {
		assertVisible(path);
		assertEnabled(path);
	}

	/**
	 * Assert that a Component is visible, enabled and of type clazz
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T assertUsability(String path, Class<T> clazz) {
		assertVisible(path, clazz);
		assertEnabled(path);
		return (T) getComponentFromLastRenderedPage(path);
	}

	/**
	 * Assert that a Component is visible and disabled
	 */
	@Override
	public void assertDisabled(String path) {
		assertVisible(path);
		super.assertDisabled(path);
	}

	/**
	 * Assert that a Component is visible, disabled and of type clazz
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T assertDisabled(String path, Class<T> clazz) {
		assertVisible(path, clazz);
		assertDisabled(path);
		return (T) getComponentFromLastRenderedPage(path);
	}

}
