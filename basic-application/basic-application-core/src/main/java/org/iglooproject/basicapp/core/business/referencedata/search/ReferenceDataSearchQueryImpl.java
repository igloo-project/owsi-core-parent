package org.iglooproject.basicapp.core.business.referencedata.search;

import org.apache.lucene.search.SortField;
import org.iglooproject.basicapp.core.business.referencedata.model.ReferenceData;
import org.iglooproject.jpa.more.business.referencedata.search.GenericReferenceDataSearchQueryImpl;
import org.iglooproject.jpa.more.business.sort.ISort;
import org.iglooproject.spring.util.StringUtils;
import org.iglooproject.spring.util.lucene.search.LuceneUtils;

public abstract class ReferenceDataSearchQueryImpl
		<
		T extends ReferenceData<? super T>,
		S extends ISort<SortField>,
		Q extends IReferenceDataSearchQuery<T, S, Q>
		>
		extends GenericReferenceDataSearchQueryImpl<T, S, Q> implements IReferenceDataSearchQuery<T, S, Q> {

	public ReferenceDataSearchQueryImpl(Class<? extends T> clazz) {
		super(clazz);
	}

	@Override
	public Q label(String label) {
//		must(matchAutocompleteIfGiven(label, ImmutableList.of(ReferenceData.LABEL_FR, ReferenceData.LABEL_EN)));
		if (StringUtils.isEmpty(label)) {
			return thisAsQ();
		}
		
		must(
			getDefaultQueryBuilder().bool()
				.should(
					getDefaultQueryBuilder().simpleQueryString()
						.onField(ReferenceData.LABEL_FR)
						.withAndAsDefaultOperator()
						.matching(LuceneUtils.getAutocompleteQuery(label, 2))
						.createQuery()
				)
				.should(
					getDefaultQueryBuilder().simpleQueryString()
						.onField(ReferenceData.LABEL_FR_FULL)
						.boostedTo(2f)
						.withAndAsDefaultOperator()
						.matching(LuceneUtils.getAutocompleteQuery(label, 2))
						.createQuery()
				)
				.createQuery()
		);
		
		return thisAsQ();
	}

	@Override
	public Q code(String code) {
		must(matchIfGiven(ReferenceData.CODE, code));
		return thisAsQ();
	}

}
