package org.iglooproject.basicapp.core.business.referencedata.model;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;
import org.iglooproject.basicapp.core.business.common.model.embeddable.LocalizedText;
import org.iglooproject.jpa.more.business.referencedata.model.GenericLocalizedReferenceData;
import org.iglooproject.jpa.search.util.HibernateSearchAnalyzer;

import com.querydsl.core.annotations.QueryInit;

@MappedSuperclass
public class LocalizedReferenceData<E extends LocalizedReferenceData<?>> extends GenericLocalizedReferenceData<E, LocalizedText> implements ILocalizedReferenceDataBindingInterface {

	private static final long serialVersionUID = -1779439527249543663L;

	public static final String LABEL = "label";
	public static final String LABEL_PREFIX = LABEL + ".";
	public static final String LABEL_FR_AUTOCOMPLETE = LABEL_PREFIX + LocalizedText.FR_AUTOCOMPLETE;
	public static final String LABEL_FR_SORT = LABEL_PREFIX + LocalizedText.FR_SORT;
	public static final String LABEL_EN_AUTOCOMPLETE = LABEL_PREFIX + LocalizedText.EN_AUTOCOMPLETE;
	public static final String LABEL_EN_SORT = LABEL_PREFIX + LocalizedText.EN_SORT;

	public static final String CODE = "code";
	public static final String CODE_SORT = "codeSort";

	@Embedded
	@IndexedEmbedded(prefix = LABEL_PREFIX)
	@QueryInit("*")
	private LocalizedText label;

	public LocalizedReferenceData() {
		this(new LocalizedText());
	}

	public LocalizedReferenceData(LocalizedText label) {
		setLabel(label);
	}

	@Override
	public LocalizedText getLabel() {
		if (label == null) {
			label = new LocalizedText();
		}
		return label;
	}

	@Override
	public void setLabel(LocalizedText label) {
		this.label = (label == null ? null : new LocalizedText(label));
	}

	@Override
	@Fields({
		@Field(name = CODE, analyzer = @Analyzer(definition = HibernateSearchAnalyzer.KEYWORD)),
		@Field(name = CODE_SORT, analyzer = @Analyzer(definition = HibernateSearchAnalyzer.TEXT_SORT))
	})
	@SortableField(forField = CODE_SORT)
	public String getCode() {
		return null;
	}

}