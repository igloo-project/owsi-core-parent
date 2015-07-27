package fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.state;

import fr.openwide.core.jpa.more.business.sort.ISort;
import fr.openwide.core.wicket.more.condition.Condition;

public interface IAddedToolbarCoreElementState<T, S extends ISort<?>> extends IAddedToolbarElementState<T, S> {

	@Override
	IAddedToolbarCoreElementState<T, S> when(Condition condition);

	@Override
	IAddedToolbarCoreElementState<T, S> withClass(String cssClass);

	@Override
	IAddedToolbarCoreElementState<T, S> colspan(long colspan);

	@Override
	IAddedToolbarCoreElementState<T, S> full();

}
