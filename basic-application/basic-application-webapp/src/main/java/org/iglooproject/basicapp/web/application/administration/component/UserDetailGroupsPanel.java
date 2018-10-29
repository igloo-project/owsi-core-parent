package org.iglooproject.basicapp.web.application.administration.component;

import static org.iglooproject.basicapp.web.application.property.BasicApplicationWebappPropertyIds.PORTFOLIO_ITEMS_PER_PAGE_DESCRIPTION;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.iglooproject.basicapp.core.business.user.model.User;
import org.iglooproject.basicapp.core.business.user.model.UserGroup;
import org.iglooproject.basicapp.core.business.user.service.IUserGroupService;
import org.iglooproject.basicapp.web.application.administration.form.UserGroupDropDownSingleChoice;
import org.iglooproject.basicapp.web.application.administration.model.UserGroupDataProvider;
import org.iglooproject.basicapp.web.application.administration.page.AdministrationUserGroupDetailPage;
import org.iglooproject.basicapp.web.application.common.renderer.ActionRenderers;
import org.iglooproject.basicapp.web.application.common.util.CssClassConstants;
import org.iglooproject.spring.property.service.IPropertyService;
import org.iglooproject.wicket.behavior.ClassAttributeAppender;
import org.iglooproject.wicket.markup.html.panel.GenericPanel;
import org.iglooproject.wicket.more.markup.html.action.IOneParameterAjaxAction;
import org.iglooproject.wicket.more.markup.html.factory.AbstractParameterizedComponentFactory;
import org.iglooproject.wicket.more.markup.html.factory.IDetachableFactory;
import org.iglooproject.wicket.more.markup.html.feedback.FeedbackUtils;
import org.iglooproject.wicket.more.markup.html.form.LabelPlaceholderBehavior;
import org.iglooproject.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel.AddInPlacement;
import org.iglooproject.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import org.iglooproject.wicket.more.model.GenericEntityModel;
import org.iglooproject.wicket.more.util.model.Detachables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDetailGroupsPanel extends GenericPanel<User> {

	private static final long serialVersionUID = -517286662347263793L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailGroupsPanel.class);

	@SpringBean
	private IUserGroupService userGroupService;
	
	@SpringBean
	private IPropertyService propertyService;
	
	private final IModel<? extends User> userModel;
	
	private final UserGroupDataProvider dataProvider;
	
	public UserDetailGroupsPanel(String id, final IModel<? extends User> userModel) {
		super(id, userModel);
		
		this.userModel = userModel;
		this.dataProvider = new UserGroupDataProvider(userModel);
		
		add(
				DataTableBuilder.start(dataProvider, dataProvider.getSortModel())
						.addLabelColumn(new ResourceModel("business.userGroup.name"))
								.withLink(AdministrationUserGroupDetailPage.MAPPER)
								.withClass("text text-md")
						.addActionColumn()
								.addConfirmAction(ActionRenderers.remove())
										.title(new ResourceModel("administration.userGroup.detail.users.action.remove.confirmation.title"))
										.content(new IDetachableFactory<IModel<UserGroup>, IModel<String>>() {
											private static final long serialVersionUID = 1L;
											@Override
											public IModel<String> create(IModel<UserGroup> parameter) {
												return new StringResourceModel("administration.userGroup.detail.users.action.remove.confirmation.content")
														.setParameters(UserDetailGroupsPanel.this.getModelObject().getFullName(), parameter.getObject().getName());
											}
										})
										.confirm()
										.onClick(new IOneParameterAjaxAction<IModel<UserGroup>>() {
											private static final long serialVersionUID = 1L;
											@Override
											public void execute(AjaxRequestTarget target, IModel<UserGroup> parameter) {
												try {
													UserGroup userGroup = parameter.getObject();
													User user = userModel.getObject();
													
													userGroupService.removeUser(userGroup, user);
													Session.get().success(getString("common.success"));
													throw new RestartResponseException(getPage());
												} catch (RestartResponseException e) {
													throw e;
												} catch (Exception e) {
													LOGGER.error("Unknown error occured while removing a user from a usergroup", e);
													Session.get().error(getString("common.error.unexpected"));
													FeedbackUtils.refreshFeedback(target, getPage());
												}
											}
										})
										.hideLabel()
								.withClassOnElements(CssClassConstants.BTN_TABLE_ROW_ACTION)
								.end()
								.withClass("actions actions-1x")
						.bootstrapCard()
								.addIn(AddInPlacement.FOOTER_MAIN,  new AbstractParameterizedComponentFactory<Component, Component>() {
									private static final long serialVersionUID = 1L;
									@Override
									public Component create(String wicketId, final Component table ) {
										return new UserGroupAddFragment(wicketId)
											.add(new ClassAttributeAppender("add-in-quick-add"));
									}
								})
								.ajaxPager(AddInPlacement.HEADING_RIGHT)
								.count("administration.user.detail.groups.count")
						.build("results", propertyService.get(PORTFOLIO_ITEMS_PER_PAGE_DESCRIPTION))
		);
	}
	
	private class UserGroupAddFragment extends Fragment {
		
		private static final long serialVersionUID = 1L;
		
		public UserGroupAddFragment(String id) {
			super(id, "addGroup", UserDetailGroupsPanel.this);
			
			IModel<UserGroup> userGroupModel = new GenericEntityModel<>();
			
			add(
					new Form<UserGroup>("form", userGroupModel)
							.add(
									new UserGroupDropDownSingleChoice("userGroup", userGroupModel)
											.setRequired(true)
											.setLabel(new ResourceModel("business.userGroup"))
											.add(new LabelPlaceholderBehavior()),
									new AjaxButton("submit") {
										private static final long serialVersionUID = 1L;
										@Override
										protected void onSubmit(AjaxRequestTarget target) {
											try {
												User user = UserDetailGroupsPanel.this.getModelObject();
												UserGroup userGroup = userGroupModel.getObject();
												
												if (!user.getGroups().contains(userGroup)) {
													userGroupService.addUser(userGroup, user);
													Session.get().success(getString("common.success"));
												} else {
													LOGGER.warn("User already added to this group.");
													Session.get().warn(getString("administration.userGroup.detail.users.action.add.error.duplicate"));
												}
												
												userGroupModel.setObject(null);
												userGroupModel.detach();
												target.add(getPage());
											} catch (Exception e) {
												LOGGER.error("Error when adding user to user group.", e);
												Session.get().error(getString("common.error.unexpected"));
											}
											FeedbackUtils.refreshFeedback(target, getPage());
										}
										@Override
										protected void onError(AjaxRequestTarget target) {
											FeedbackUtils.refreshFeedback(target, getPage());
										}
									}
							)
			);
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(userModel, dataProvider);
	}
	
}
