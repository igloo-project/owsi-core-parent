package org.iglooproject.basicapp.web.application.notification.page;

import java.util.Date;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.iglooproject.basicapp.core.business.user.model.User;
import org.iglooproject.basicapp.core.util.ResourceKeyGenerator;
import org.iglooproject.basicapp.core.util.binding.Bindings;
import org.iglooproject.basicapp.web.application.notification.template.AbstractHtmlNotificationPage;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.link.descriptor.generator.IPageLinkGenerator;
import org.iglooproject.wicket.more.model.BindingModel;

public class UserPasswordRecoveryRequestHtmlNotificationPage<T extends User> extends AbstractHtmlNotificationPage<T> {

	private static final long serialVersionUID = -6941290354402094613L;

	public UserPasswordRecoveryRequestHtmlNotificationPage(ResourceKeyGenerator resourceKeyGenerator,
			IModel<T> objectModel, IModel<User> authorModel, IModel<Date> dateModel, IPageLinkGenerator linkGenerator) {
		this(resourceKeyGenerator, resourceKeyGenerator, objectModel, authorModel, dateModel, linkGenerator);
	}

	public UserPasswordRecoveryRequestHtmlNotificationPage(ResourceKeyGenerator resourceKeyGenerator,
			ResourceKeyGenerator defaultResourceKeyGenerator, IModel<T> objectModel, IModel<User> authorModel,
			IModel<Date> dateModel, IPageLinkGenerator linkGenerator) {
		super(objectModel);
		
		add(
			new CoreLabel(
				"title",
				new StringResourceModel(resourceKeyGenerator.resourceKey("title"))
					.setDefaultValue(
						new StringResourceModel(defaultResourceKeyGenerator.resourceKey("title"))
					)
			)
		);
		
		add(
			new CoreLabel(
				"intro",
				new StringResourceModel(resourceKeyGenerator.resourceKey("intro"), objectModel)
					.setParameters(dateModel, authorModel)
					.setDefaultValue(
						new StringResourceModel(defaultResourceKeyGenerator.resourceKey("intro"), objectModel)
							.setParameters(dateModel, authorModel)
					)
			)
		);
		
		add(
			new CoreLabel(
				"text",
				new StringResourceModel(resourceKeyGenerator.resourceKey("text"), objectModel)
					.setParameters(dateModel, authorModel)
					.setDefaultValue(
						new StringResourceModel(defaultResourceKeyGenerator.resourceKey("text"), objectModel)
							.setParameters(dateModel, authorModel)
					)
			)
		);
		
		add(
			linkGenerator
				.bypassPermissions()
				.link("link")
				.setAbsolute(true)
				.setBody(
					new StringResourceModel(resourceKeyGenerator.resourceKey("link.label"), objectModel)
						.setParameters(dateModel, authorModel)
						.setDefaultValue(
							new StringResourceModel(defaultResourceKeyGenerator.resourceKey("link.label"), objectModel)
								.setParameters(dateModel, authorModel)
						)
				),
			linkGenerator
				.bypassPermissions()
				.link("url")
				.setAbsolute(true)
				.setBody(linkGenerator::fullUrl)
		);
		
		add(
			new CoreLabel("username", BindingModel.of(objectModel, Bindings.user().username()))
		);
	}

}
