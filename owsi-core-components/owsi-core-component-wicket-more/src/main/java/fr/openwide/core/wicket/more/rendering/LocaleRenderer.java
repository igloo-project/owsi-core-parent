package fr.openwide.core.wicket.more.rendering;

import java.util.Locale;

import org.apache.wicket.Session;

public class LocaleRenderer extends Renderer<Locale> {

	private static final long serialVersionUID = 3616234068185075603L;
	
	private static LocaleRenderer INSTANCE = new LocaleRenderer();
	public static LocaleRenderer get() {
		return INSTANCE;
	}

	/**
	 * @deprecated Use {@link #get()} instead.
	 */
	@Deprecated
	protected LocaleRenderer() { }

	@Override
	public String render(Locale value, Locale locale) {
		return locale != null ? locale.getDisplayName(Session.get().getLocale()) : null;
	}

}
