package org.iglooproject.wicket.more.jqplot.data.adapter;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.iglooproject.wicket.more.jqplot.data.provider.IJQPlotDataProvider;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import nl.topicus.wqplot.data.BaseSeries;
import nl.topicus.wqplot.options.PlotOptions;
import nl.topicus.wqplot.options.PlotSeries;
import nl.topicus.wqplot.options.PlotTick;

public abstract class AbstractJQPlotContinuousKeysDataAdapter<S, K, V extends Number>
		extends AbstractJQPlotDataAdapter<S, K, V, K> {
	
	private static final long serialVersionUID = 3961697302069579609L;
	
	private final String formatString;

	public AbstractJQPlotContinuousKeysDataAdapter(IJQPlotDataProvider<S, K, V> dataProvider, String formatString) {
		super(dataProvider);
		this.formatString = formatString;
	}

	@Override
	public void configure(PlotOptions options, Map<? extends S, PlotSeries> seriesMap, Map<? extends K, PlotTick> keysMap, Locale locale) {
		options.getAxes().getXaxis().getTickOptions()
				.setFormatString(formatString)
				.setShowGridline(true);
	}
	
	@Override
	public Collection<K> getKeysTicks() {
		// Keys ticks are generated by JQPlot itself. They must not appear in JQPlot options.
		return ImmutableSet.<K>of();
	}

	@Override
	public Collection<BaseSeries<K, V>> getObject(Locale locale) {
		Iterable<? extends K> keys = super.getKeys();
		
		Collection<BaseSeries<K, V>> result = Lists.newArrayList();
		for (S series : getSeries()) {
			BaseSeries<K, V> seriesData = createSeriesData(series, keys);
			result.add(seriesData);
		}
		return result;
	}
	
	private BaseSeries<K, V> createSeriesData(S series, Iterable<? extends K> keys) {
		BaseSeries<K, V> seriesData = new BaseSeries<>();
		for (K key : keys) {
			V value = getValue(series, key);
			if (value != null) {
				seriesData.addEntry(key, value);
			}
		}
		return seriesData;
	}
}
