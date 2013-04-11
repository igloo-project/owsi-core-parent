package fr.openwide.core.basicapp.web.application.administration.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.openwide.core.basicapp.core.business.user.model.User;
import fr.openwide.core.basicapp.core.business.user.service.IUserService;
import fr.openwide.core.basicapp.core.util.binding.Binding;
import fr.openwide.core.basicapp.web.application.administration.component.UserMembershipsPanel;
import fr.openwide.core.basicapp.web.application.administration.component.UserProfilPanel;
import fr.openwide.core.basicapp.web.application.administration.template.AdministrationTemplate;
import fr.openwide.core.basicapp.web.application.navigation.util.LinkUtils;
import fr.openwide.core.wicket.more.markup.html.template.model.BreadCrumbElement;
import fr.openwide.core.wicket.more.model.BindingModel;
import fr.openwide.core.wicket.more.model.GenericEntityModel;

public class AdministrationUserDescriptionPage extends AdministrationTemplate {

	private static final long serialVersionUID = -550100874222819991L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdministrationUserDescriptionPage.class);

	@SpringBean
	private IUserService userService;

	private IModel<User> userModel;

	public AdministrationUserDescriptionPage(PageParameters parameters) {
		super(parameters);
		
		Long userId = parameters.get(LinkUtils.ID_PARAMETER).toLong();
		User user = userService.getById(userId);
		
		if (user == null) {
			LOGGER.error("Error on user loading");
			getSession().error(getString("administration.user.error"));
			
			redirect(AdministrationUserPortfolioPage.class);
			return;
		}
		userModel = new GenericEntityModel<Long, User>(user);
		
		addBreadCrumbElement(new BreadCrumbElement(new ResourceModel("navigation.administration.user"),
				AdministrationUserPortfolioPage.class));
		
		addBreadCrumbElement(new BreadCrumbElement(BindingModel.of(userModel, Binding.user().fullName()),
				AdministrationUserDescriptionPage.class, parameters));
		
		add(new Label("pageTitle", BindingModel.of(userModel, Binding.user().fullName())));
		
		add(new UserProfilPanel("profile", userModel));
		add(new UserMembershipsPanel("groups", userModel));
	}

	@Override
	protected Class<? extends WebPage> getSecondMenuPage() {
		return AdministrationUserPortfolioPage.class;
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		
		if (userModel != null) {
			userModel.detach();
		}
	}
}
