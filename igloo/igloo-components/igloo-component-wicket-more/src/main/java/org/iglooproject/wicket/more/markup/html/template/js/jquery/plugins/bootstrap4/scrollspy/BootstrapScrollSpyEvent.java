package org.iglooproject.wicket.more.markup.html.template.js.jquery.plugins.bootstrap4.scrollspy;

import org.wicketstuff.wiquery.core.events.EventLabel;

public enum BootstrapScrollSpyEvent implements EventLabel {

	ACTIVATE("activate.bs.scrollspy");

	private final String eventLabel;

	private BootstrapScrollSpyEvent(String eventLabel) {
		this.eventLabel = eventLabel;
	}

	@Override
	public String getEventLabel() {
		return eventLabel;
	}

}
