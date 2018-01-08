package org.iglooproject.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.affix;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.wicketstuff.wiquery.core.javascript.JsStatement;

/**
 * @deprecated From Bootstrap migration guide :<br />
 * 
 * <strong>Dropped the Affix jQuery plugin.</strong>
 * <ul>
 * 	<li>
 * 		We recommend using <code>position: sticky</code> instead. <a href="http://html5please.com/#sticky">See the HTML5 Please entry</a> for details and specific polyfill recommendations.
 * 		One suggestion is to use an <code>@supports</code> rule for implementing it (e.g., <code>@supports (position: sticky) { ... }</code>)/
 * 	</li>
 * 	<li>
 * 		If you were using Affix to apply additional, non-<code>position</code> styles, the polyfills might not support your use case.
 * 		One option for such uses is the third-party <a href="https://github.com/acch/scrollpos-styler">ScrollPos-Styler</a> library.
 * 	</li>
 * </ul>
 */
@Deprecated
public class BootstrapAffixBehavior extends Behavior {

	private static final long serialVersionUID = -5665173800881237350L;

	private static final String BOOTSTRAP_AFFIX = "affix";

	private BootstrapAffixOptions options;

	public BootstrapAffixBehavior(BootstrapAffixOptions options) {
		super();
		this.options = options;
	}

	public JsStatement statement(Component component) {
		return new JsStatement().$(component).chain(BOOTSTRAP_AFFIX, options.getJavaScriptOptions());
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(BootstrapAffixJavaScriptResourceReference.get()));
		response.render(OnDomReadyHeaderItem.forScript(statement(component).render()));
	}

}
