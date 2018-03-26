package org.iglooproject.wicket.more.markup.repeater.table.builder.state;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.iglooproject.commons.util.binding.ICoreBinding;
import org.iglooproject.functional.SerializableFunction2;
import org.iglooproject.jpa.more.business.sort.ISort;
import org.iglooproject.wicket.more.date.pattern.ILocalDatePattern;
import org.iglooproject.wicket.more.date.pattern.ILocalDateTimePattern;
import org.iglooproject.wicket.more.date.pattern.ILocalTimePattern;
import org.iglooproject.wicket.more.date.pattern.IZonedDateTimePattern;
import org.iglooproject.wicket.more.markup.html.bootstrap.common.renderer.BootstrapRenderer;
import org.iglooproject.wicket.more.markup.repeater.table.builder.action.ActionColumnBuilder;
import org.iglooproject.wicket.more.markup.repeater.table.column.ICoreColumn;
import org.iglooproject.wicket.more.rendering.Renderer;
import org.iglooproject.wicket.more.util.IDatePattern;


public interface IColumnState<T, S extends ISort<?>> extends IBuildState<T, S> {

	IAddedColumnState<T, S> addColumn(IColumn<T, S> column);

	IAddedCoreColumnState<T, S> addColumn(ICoreColumn<T, S> column);

	IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel);

	IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel, Renderer<? super T> renderer);

	<C> IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, C> binding);

	<C> IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, C> binding, Renderer<? super C> renderer);

	<C> IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel, SerializableFunction2<? super T, C> function);

	<C> IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel, SerializableFunction2<? super T, C> function,
			Renderer<? super C> renderer);

	IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, ? extends Date> binding, IDatePattern datePattern);

	IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, ? extends LocalDateTime> binding, ILocalDateTimePattern pattern);

	IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, ? extends LocalDate> binding, ILocalDatePattern pattern);

	IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, ? extends LocalTime> binding, ILocalTimePattern pattern);

	IAddedLabelColumnState<T, S> addLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, ? extends ZonedDateTime> binding, IZonedDateTimePattern pattern);

	<C> IAddedBootstrapBadgeColumnState<T, S, C> addBootstrapBadgeColumn(IModel<String> headerModel,
			ICoreBinding<? super T, C> binding, BootstrapRenderer<? super C> renderer);

	<C> IAddedBootstrapBadgeColumnState<T, S, C> addBootstrapBadgeColumn(IModel<String> headerModel,
			SerializableFunction2<? super T, C> function, BootstrapRenderer<? super C> renderer);

	/**
	 * @deprecated Bootstrap Labels no longer exist in Bootstrap 4 and are replaced by Bootstrap Badge instead.
	 */
	@Deprecated
	<C> IAddedCoreColumnState<T, S> addBootstrapLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, C> binding, BootstrapRenderer<? super C> renderer);

	/**
	 * @deprecated Bootstrap Labels no longer exist in Bootstrap 4 and are replaced by Bootstrap Badge instead.
	 */
	@Deprecated
	<C> IAddedCoreColumnState<T, S> addBootstrapLabelColumn(IModel<String> headerModel,
			SerializableFunction2<? super T, C> function, BootstrapRenderer<? super C> renderer);

	<C> IAddedBooleanLabelColumnState<T, S> addBooleanLabelColumn(IModel<String> headerModel,
			ICoreBinding<? super T, Boolean> binding);

	ActionColumnBuilder<T, IAddedCoreColumnState<T, S>> addActionColumn();

	ActionColumnBuilder<T, IAddedCoreColumnState<T, S>> addActionColumn(IModel<String> headerLabelModel);

}
