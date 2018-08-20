package org.iglooproject.test.wicket.more.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.bindgen.java.lang.StringBinding;
import org.junit.Assert;
import org.junit.Test;

import org.iglooproject.test.wicket.more.AbstractWicketMoreTestCase;
import org.iglooproject.wicket.more.model.BindingModel;

public class TestSelfModel extends AbstractWicketMoreTestCase {

	/**
	 * Root-only binding model loading
	 */
	@Test
	public void testSelfModel() {
		String string = "maChaine";
		IModel<String> stringModel = BindingModel.of(Model.of(string), new StringBinding());
		Assert.assertEquals(string, stringModel.getObject());
	}

}
