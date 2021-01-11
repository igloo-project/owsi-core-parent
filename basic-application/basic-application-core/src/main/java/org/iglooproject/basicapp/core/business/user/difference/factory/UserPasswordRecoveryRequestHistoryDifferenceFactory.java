package org.iglooproject.basicapp.core.business.user.difference.factory;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.iglooproject.basicapp.core.business.user.model.User;
import org.iglooproject.basicapp.core.business.user.model.embeddable.UserPasswordRecoveryRequest;
import org.iglooproject.commons.util.fieldpath.FieldPath;
import org.iglooproject.functional.Predicate2;
import org.iglooproject.functional.Predicates2;
import org.iglooproject.functional.Supplier2;
import org.iglooproject.jpa.more.business.difference.factory.AbstractHistoryDifferenceFactory;
import org.iglooproject.jpa.more.business.difference.factory.DefaultHistoryDifferenceFactory;
import org.iglooproject.jpa.more.business.difference.model.Difference;
import org.iglooproject.jpa.more.business.difference.util.DiffUtils;
import org.iglooproject.jpa.more.business.history.model.AbstractHistoryDifference;
import org.javatuples.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.DiffNode.State;

@Component
public class UserPasswordRecoveryRequestHistoryDifferenceFactory extends AbstractHistoryDifferenceFactory<User> {

	@Autowired
	private DefaultHistoryDifferenceFactory<Unit<UserPasswordRecoveryRequest>> delegate;

	private final ObjectDiffer differ;
	
	public UserPasswordRecoveryRequestHistoryDifferenceFactory() {
		this.differ = DiffUtils.builder()
			.filtering()
				.returnNodesWithState(State.UNTOUCHED)
				.and()
			.build();
	}

	@PostConstruct
	private void init() {
		delegate.setNodeFilter(
			Predicates2.or(
				new Predicate2<DiffNode>() {
					@Override
					public boolean test(DiffNode input) {
						return UserPasswordRecoveryRequest.class.isAssignableFrom(input.getValueType());
					}
				},
				input -> DiffUtils.isItem(input) || !input.hasChildren()
			)
		);
	}

	@Override
	public <HD extends AbstractHistoryDifference<HD, ?>> List<HD> create(
		Supplier2<HD> historyDifferenceSupplier,
		Difference<User> participationRootDifference,
		Collection<DiffNode> nodes
	) {
		Unit<UserPasswordRecoveryRequest> before = Unit.with(participationRootDifference.getBefore().getPasswordRecoveryRequest());
		Unit<UserPasswordRecoveryRequest> after = Unit.with(participationRootDifference.getAfter().getPasswordRecoveryRequest());
		Difference<Unit<UserPasswordRecoveryRequest>> userPasswordRecoveryRequestRootDifference = DiffUtils.diff(before, after, differ);
		return delegate.createChildren(
			historyDifferenceSupplier,
			userPasswordRecoveryRequestRootDifference,
			userPasswordRecoveryRequestRootDifference.getDiffNode(),
			FieldPath.ROOT
		);
	}

}
