package fr.openwide.core.wicket.more.markup.html.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.odlabs.wiquery.core.IWiQueryPlugin;
import org.odlabs.wiquery.core.javascript.JsStatement;
import org.odlabs.wiquery.core.javascript.JsUtils;
import org.odlabs.wiquery.core.resources.CoreJavaScriptResourceReference;

import fr.openwide.core.wicket.behavior.ClassAttributeAppender;
import fr.openwide.core.wicket.markup.html.util.css3pie.Css3PieHeadBehavior;
import fr.openwide.core.wicket.more.markup.html.CoreWebPage;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.tipsy.Tipsy;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.tipsy.TipsyBehavior;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.tipsy.TipsyCloseOnLoadJavascriptResourceReference;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.tipsy.TipsyOptionGravity;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.tipsy.TipsyOptionTrigger;
import fr.openwide.core.wicket.more.markup.html.template.model.BreadCrumbElement;

public abstract class AbstractWebPageTemplate extends CoreWebPage implements IWiQueryPlugin {

	private static final long serialVersionUID = -5598937641577320345L;

	private static final String META_TITLE_SEPARATOR = " › ";
	
	private static final String TIPSY_DATA_TOOLTIP = "data-tooltip";
	private static final String TIPSY_DATA_FORM_FIELD_HELP = "data-form-field-help";
	
	private List<String> pageTitleElements = new ArrayList<String>();
	
	private List<BreadCrumbElement> breadCrumbElements = new ArrayList<BreadCrumbElement>();
	
	private boolean contributeTipsyCloseOnLoad = false;
	
	public AbstractWebPageTemplate(PageParameters parameters) {
		super(parameters);
	}
	
	protected void addMenuElement(Class<? extends Page> selectedPageClass, String name, Class<? extends Page> pageClass) {
		addMenuElement(this, selectedPageClass, name, pageClass, null, true);
	}
	
	protected void addMenuElement(Class<? extends Page> selectedPageClass, String name, Class<? extends Page> pageClass,
			boolean isVisible) {
		addMenuElement(this, selectedPageClass, name, pageClass, null, isVisible);
	}
	
	protected void addMenuElement(Class<? extends Page> selectedPageClass, String name, Class<? extends Page> pageClass,
			PageParameters parameters) {
		addMenuElement(this, selectedPageClass, name, pageClass, parameters, true);
	}
	
	protected void addMenuElement(Class<? extends Page> selectedPageClass, String name, Class<? extends Page> pageClass,
			PageParameters parameters, boolean isVisible) {
		addMenuElement(this, selectedPageClass, name, pageClass, parameters, isVisible);
	}
	
	protected void addMenuElement(MarkupContainer menuContainer,
			Class<? extends Page> selectedPageClass,
			String name,
			Class<? extends Page> pageClass,
			PageParameters parameters,
			boolean isVisible) {
		BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(name + "MenuLinkContainer", pageClass, parameters);
		link.setVisible(isVisible && isPageAccessible(pageClass));
		
		MarkupContainer container = new WebMarkupContainer(name + "MenuLink");
		if (pageClass.equals(selectedPageClass)) {
			link.add(new ClassAttributeAppender("selected"));
		}
		link.add(container);
		
		menuContainer.add(link);
	}
	
	protected boolean isPageAccessible(Class<? extends Page> pageClass) {
		return Session.get().getAuthorizationStrategy().isInstantiationAuthorized(pageClass);
	}
	
	protected abstract Class<? extends WebPage> getFirstMenuPage();
	
	protected abstract Class<? extends WebPage> getSecondMenuPage();
	
	protected void addBreadCrumbElement(BreadCrumbElement breadCrumbElement) {
		breadCrumbElements.add(breadCrumbElement);
	}
	
	protected IModel<List<BreadCrumbElement>> getBreadCrumbElementsModel() {
		return new PropertyModel<List<BreadCrumbElement>>(this, "breadCrumbElements");
	}
	
	public List<BreadCrumbElement> getBreadCrumbElements() {
		return this.breadCrumbElements;
	}
	
	protected abstract String getRootPageTitleLabelKey();
	
	protected IModel<String> getHeadPageTitleModel() {
		return new PropertyModel<String>(this, "headPageTitle");
	}
	
	protected void addPageTitleElement(String key) {
		this.pageTitleElements.add(key);
	}
	
	public String getHeadPageTitle() {
		StringBuilder sb = new StringBuilder(getLocalizer().getString(getRootPageTitleLabelKey(), this));
		if (pageTitleElements.size() > 0) {
			for (String pageTitleElement : pageTitleElements) {
				sb.append(META_TITLE_SEPARATOR);
				sb.append(getLocalizer().getString(pageTitleElement, this, pageTitleElement));
			}
		} else {
			boolean oneElementBreadcrumb = (breadCrumbElements.size() == 1);
			
			for (BreadCrumbElement breadCrumbElement : breadCrumbElements) {
				if (oneElementBreadcrumb || !getApplication().getHomePage().equals(breadCrumbElement.getPageClass())) {
					sb.append(META_TITLE_SEPARATOR);
					sb.append(getLocalizer().getString(breadCrumbElement.getLabelKey(), this, breadCrumbElement.getLabelKey()));
				}
			}
		}
		
		return sb.toString();
	}
	
	protected void enableDefaultTipsyTooltips() {
		enableTipsyTooltips("[" + TIPSY_DATA_TOOLTIP + "]", TIPSY_DATA_TOOLTIP);
		enableTipsyTooltips("[" + TIPSY_DATA_FORM_FIELD_HELP + "]", TIPSY_DATA_FORM_FIELD_HELP,
				TipsyOptionGravity.WEST, TipsyOptionTrigger.FOCUS);
	}
	
	protected void enableTipsyTooltips(String selector, String tooltipAttributeName) {
		enableCloseTipsyOnLoad();
		enableTipsyTooltips(selector, tooltipAttributeName, TipsyOptionGravity.NORTH, TipsyOptionTrigger.HOVER);
	}
	
	protected void enableTipsyTooltips(String selector, String tooltipAttributeName, TipsyOptionGravity gravity, TipsyOptionTrigger trigger) {
		final Tipsy tipsy = new Tipsy();
		tipsy.setFade(true);
		tipsy.setLive(true);
		tipsy.setTitle(JsUtils.quotes(tooltipAttributeName));
		tipsy.setGravity(gravity);
		tipsy.setTrigger(trigger);
			
		add(new TipsyBehavior(selector, tipsy));
	}
	
	protected void enableCss3Pie(String[] styles) {
		add(new Css3PieHeadBehavior(styles));
	}
	
	protected void enableCloseTipsyOnLoad() {
		contributeTipsyCloseOnLoad = true;
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		if (contributeTipsyCloseOnLoad) {
			response.renderJavaScriptReference(CoreJavaScriptResourceReference.get());
			response.renderJavaScriptReference(TipsyCloseOnLoadJavascriptResourceReference.get());
		}
	}

	@Override
	public JsStatement statement() {
		return null;
	}
}