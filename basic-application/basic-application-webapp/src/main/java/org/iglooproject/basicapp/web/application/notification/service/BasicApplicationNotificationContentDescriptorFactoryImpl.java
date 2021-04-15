package org.iglooproject.basicapp.web.application.notification.service;

import java.util.Date;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.iglooproject.basicapp.core.business.notification.service.IBasicApplicationNotificationContentDescriptorFactory;
import org.iglooproject.basicapp.core.business.user.model.User;
import org.iglooproject.basicapp.core.util.ResourceKeyGenerator;
import org.iglooproject.basicapp.core.util.binding.Bindings;
import org.iglooproject.basicapp.web.application.notification.page.ExampleHtmlNotificationPage;
import org.iglooproject.basicapp.web.application.notification.page.ExampleInlineCssHtmlNotificationPage;
import org.iglooproject.basicapp.web.application.notification.page.UserPasswordRecoveryRequestHtmlNotificationPage;
import org.iglooproject.basicapp.web.application.security.password.page.SecurityPasswordCreationPage;
import org.iglooproject.basicapp.web.application.security.password.page.SecurityPasswordResetPage;
import org.iglooproject.spring.notification.model.INotificationContentDescriptor;
import org.iglooproject.wicket.more.link.descriptor.generator.IPageLinkGenerator;
import org.iglooproject.wicket.more.link.descriptor.mapper.ITwoParameterLinkDescriptorMapper;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.notification.service.AbstractNotificationContentDescriptorFactory;
import org.iglooproject.wicket.more.notification.service.IWicketContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

@Service("BasicApplicationNotificationPanelRendererService")
public class BasicApplicationNotificationContentDescriptorFactoryImpl
		extends AbstractNotificationContentDescriptorFactory
		implements IBasicApplicationNotificationContentDescriptorFactory {
	
	@Autowired
	public BasicApplicationNotificationContentDescriptorFactoryImpl(IWicketContextProvider contextProvider) {
		super(contextProvider);
	}

	@Override
	public INotificationContentDescriptor example(final User user, final Date date) {
		return new AbstractSimpleWicketNotificationDescriptor("notification.panel.example") {
			@Override
			public Object getSubjectParameter() {
				return user;
			}
			@Override
			public Iterable<?> getSubjectPositionalParameters() {
				return ImmutableList.of(user.getFullName(), date);
			}
			@Override
			public Page createPage() {
				return new ExampleHtmlNotificationPage(GenericEntityModel.of(user), Model.of(date));
			}
		};
	}

	@Override
	public INotificationContentDescriptor exampleInlineCss(final User user, final Date date) {
		return new AbstractSimpleWicketNotificationDescriptor("notification.panel.example") {
			@Override
			public Object getSubjectParameter() {
				return user;
			}
			@Override
			public Iterable<?> getSubjectPositionalParameters() {
				return ImmutableList.of(user.getFullName(), date);
			}
			@Override
			public Page createPage() {
				return new ExampleInlineCssHtmlNotificationPage();
			}
		};
	}

	@Override
	public INotificationContentDescriptor userPasswordRecoveryRequest(final User user) {
		final ITwoParameterLinkDescriptorMapper<? extends IPageLinkGenerator, User, String> mapper;
		switch (user.getPasswordRecoveryRequest().getType()) {
		case CREATION:
			mapper = SecurityPasswordCreationPage.MAPPER;
			break;
		case RESET:
			mapper = SecurityPasswordResetPage.MAPPER;
			break;
		default:
			throw new IllegalStateException("Recovery request type unknown.");
		}
		
		final ResourceKeyGenerator keyGenerator =
			ResourceKeyGenerator.of("notification.panel.user.password.recovery.request")
				.append(user.getPasswordRecoveryRequest().getType().name())
				.append(user.getPasswordRecoveryRequest().getInitiator().name());
		
		return new AbstractSimpleWicketNotificationDescriptor(keyGenerator.resourceKey()) {
			@Override
			public IModel<?> getSubjectParameter() {
				return GenericEntityModel.of(user);
			}
			@Override
			public Page createPage() {
				IModel<User> userModel = GenericEntityModel.of(user);
				return new UserPasswordRecoveryRequestHtmlNotificationPage<>(
					keyGenerator,
					userModel, userModel, Model.of(new Date()),
					mapper.map(userModel, BindingModel.of(userModel, Bindings.user().passwordRecoveryRequest().token()))
				);
			}
		};
	}

}
