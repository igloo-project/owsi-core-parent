package fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.state;

import fr.openwide.core.jpa.more.business.sort.ISort;
import fr.openwide.core.wicket.more.condition.Condition;

public interface IAddedToolbarLabelElementState<T, S extends ISort<?>> extends IAddedToolbarElementState<T, S> {

	@Override
	IAddedToolbarLabelElementState<T, S> when(Condition condition);

	@Override
	IAddedToolbarLabelElementState<T, S> withClass(String cssClass);

	@Override
	IAddedToolbarLabelElementState<T, S> colspan(long colspan);

	@Override
	IAddedToolbarLabelElementState<T, S> full();

}
