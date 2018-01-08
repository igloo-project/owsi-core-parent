package org.iglooproject.wicket.more.markup.html.template.js.jquery.plugins.bootstrap4.confirm.statement;

import org.apache.wicket.Component;
import org.wicketstuff.wiquery.core.javascript.JsStatement;

public final class BootstrapConfirmStatement {

	public static final JsStatement confirm(Component component) {
		return new JsStatement().$(component).chain(BootstrapConfirm.confirm()).append(";");
	}
	
	private BootstrapConfirmStatement() {
	}

}
