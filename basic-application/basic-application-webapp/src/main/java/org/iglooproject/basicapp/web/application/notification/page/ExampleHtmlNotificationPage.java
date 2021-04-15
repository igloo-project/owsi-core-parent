package org.iglooproject.basicapp.web.application.notification.page;

import java.util.Date;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.iglooproject.basicapp.core.business.user.model.User;
import org.iglooproject.basicapp.core.util.binding.Bindings;
import org.iglooproject.basicapp.web.application.BasicApplicationApplication;
import org.iglooproject.basicapp.web.application.administration.template.AdministrationUserDetailTemplate;
import org.iglooproject.basicapp.web.application.common.renderer.UserEnabledRenderer;
import org.iglooproject.basicapp.web.application.notification.template.AbstractHtmlNotificationPage;
import org.iglooproject.wicket.markup.html.basic.CoreLabel;
import org.iglooproject.wicket.more.markup.html.basic.DateLabel;
import org.iglooproject.wicket.more.markup.html.bootstrap.common.behavior.BootstrapColorBehavior;
import org.iglooproject.wicket.more.model.BindingModel;
import org.iglooproject.wicket.more.util.DatePattern;

public class ExampleHtmlNotificationPage extends AbstractHtmlNotificationPage<User> {
	
	private static final long serialVersionUID = -2406171975975069084L;
	
	public ExampleHtmlNotificationPage(IModel<User> userModel, IModel<Date> dateModel) {
		super(userModel);
		
		add(
			new WebMarkupContainer("intro")
				.add(
					new CoreLabel("user", userModel)
						.showPlaceholder(),
					new DateLabel("date", dateModel, DatePattern.SHORT_DATE)
						.showPlaceholder(),
					new DateLabel("time", dateModel, DatePattern.TIME)
						.showPlaceholder()
				)
		);
		
		add(
			BasicApplicationApplication.get().getHomePageLinkDescriptor()
				.bypassPermissions()
				.link("link")
				.setAbsolute(true),
			BasicApplicationApplication.get().getHomePageLinkDescriptor()
				.bypassPermissions()
				.link("url")
				.setAbsolute(true)
				.setBody(BasicApplicationApplication.get().getHomePageLinkDescriptor()::fullUrl)
		);
		
		add(
			AdministrationUserDetailTemplate.mapper()
				.ignoreParameter2()
				.map(userModel)
				.bypassPermissions()
				.link("userLink")
				.setAbsolute(true)
				.setBody(BindingModel.of(userModel, Bindings.user().username())),
			new CoreLabel("firstname", BindingModel.of(userModel, Bindings.user().firstName())),
			new CoreLabel("lastname", BindingModel.of(userModel, Bindings.user().lastName())),
			new CoreLabel("enabled", UserEnabledRenderer.get().asModel(userModel))
				.showPlaceholder()
				.add(
					BootstrapColorBehavior.text(UserEnabledRenderer.get().asModel(userModel).getColorModel())
				),
			new DateLabel("lastLoginDate", BindingModel.of(userModel, Bindings.user().lastLoginDate()), DatePattern.SHORT_DATETIME)
		);
	}

}
