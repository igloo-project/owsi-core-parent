package org.iglooproject.basicapp.core.business.user.difference.service;

import org.bindgen.BindingRoot;
import org.iglooproject.basicapp.core.business.user.difference.factory.UserPasswordRecoveryRequestHistoryDifferenceFactory;
import org.iglooproject.basicapp.core.business.user.model.User;
import org.iglooproject.basicapp.core.util.binding.Bindings;
import org.iglooproject.commons.util.binding.ICoreBinding;
import org.iglooproject.commons.util.fieldpath.FieldPath;
import org.iglooproject.jpa.more.business.difference.differ.ExtendedCollectionDiffer;
import org.iglooproject.jpa.more.business.difference.factory.IHistoryDifferenceFactory;
import org.iglooproject.jpa.more.business.difference.service.AbstractGenericEntityDifferenceServiceImpl;
import org.iglooproject.jpa.more.business.difference.util.CollectionElementsFunctionProxyInitializer;
import org.iglooproject.jpa.more.business.difference.util.DiffUtils;
import org.iglooproject.jpa.more.business.difference.util.FunctionProxyInitializer;
import org.iglooproject.jpa.more.business.difference.util.IProxyInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Equivalence;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

@Service
public class UserDifferenceServiceImpl extends AbstractGenericEntityDifferenceServiceImpl<User> implements IUserDifferenceService {

	@Autowired
	private UserPasswordRecoveryRequestHistoryDifferenceFactory userPasswordRecoveryRequestHistoryDifferenceFactory;

	@Override
	protected Iterable<? extends ICoreBinding<? extends User, ?>> getSimpleInitializationFieldsBindings() {
		return ImmutableList.of(
			Bindings.user().username(),
			Bindings.user().firstName(),
			Bindings.user().lastName(),
			Bindings.user().email(),
			Bindings.user().locale(),
			Bindings.user().groups(),
			Bindings.user().authorities()
		);
	}
	
	@Override
	protected Iterable<? extends BindingRoot<? super User, ?>> getMinimalDifferenceFieldsBindings() {
		return ImmutableList.of(
			Bindings.user().username(),
			Bindings.user().firstName(),
			Bindings.user().lastName(),
			Bindings.user().email()
		);
	}

	@Override
	protected Iterable<? extends IProxyInitializer<? super User>> initializeInitializers() {
		return ImmutableList.of(
			new CollectionElementsFunctionProxyInitializer<>(
				Bindings.user().groups(),
				new FunctionProxyInitializer<>(
					Bindings.userGroup().authorities()
				)
			),
			new CollectionElementsFunctionProxyInitializer<>(
				Bindings.user().authorities(),
				new FunctionProxyInitializer<>(
						Bindings.authority().customPermissionNames()
				)
			)
		);
	}

	@Override
	protected ExtendedCollectionDiffer initializeCollectionDiffer(ExtendedCollectionDiffer differ) {
		return differ
			.addStrategy(
				DiffUtils.isField(Bindings.user().groups()),
				Equivalence.equals()
			)
			.addStrategy(
				DiffUtils.isField(Bindings.user().authorities()),
				Equivalence.equals()
			);
	}

	@Override
	protected Multimap<IHistoryDifferenceFactory<User>, FieldPath> getSpecificHistoryDifferenceFactories() {
		return ImmutableMultimap.<IHistoryDifferenceFactory<User>, FieldPath>builder()
				.putAll(
					userPasswordRecoveryRequestHistoryDifferenceFactory,
					FieldPath.fromBinding(Bindings.user().passwordRecoveryRequest())
				)
				.build();
	}

}
