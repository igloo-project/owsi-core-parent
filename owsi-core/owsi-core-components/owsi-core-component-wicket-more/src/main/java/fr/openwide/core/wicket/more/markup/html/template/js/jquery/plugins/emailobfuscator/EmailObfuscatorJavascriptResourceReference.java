package fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.emailobfuscator;

import org.apache.wicket.request.resource.ResourceReference;
import org.odlabs.wiquery.core.resources.WiQueryJavaScriptResourceReference;

public final class EmailObfuscatorJavascriptResourceReference extends WiQueryJavaScriptResourceReference {

	private static final long serialVersionUID = 1381244952264979392L;

	private static final EmailObfuscatorJavascriptResourceReference INSTANCE = new EmailObfuscatorJavascriptResourceReference();

	private EmailObfuscatorJavascriptResourceReference() {
		super(EmailObfuscatorJavascriptResourceReference.class, "jquery.email-obfuscator.js");
	}

	public static ResourceReference get() {
		return INSTANCE;
	}

}
