package com.faendir.acra.ui.view;

import com.faendir.acra.mongod.data.DataManager;
import com.faendir.acra.mongod.model.App;
import com.faendir.acra.mongod.model.Permission;
import com.faendir.acra.security.SecurityUtils;
import com.faendir.acra.ui.view.base.NamedView;
import com.faendir.acra.ui.view.tabs.BugTab;
import com.faendir.acra.ui.view.tabs.DeObfuscationTab;
import com.faendir.acra.ui.view.tabs.PropertiesTab;
import com.faendir.acra.ui.view.tabs.ReportTab;
import com.faendir.acra.ui.view.tabs.StatisticsTab;
import com.faendir.acra.util.Style;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lukas
 * @since 13.05.2017
 */
@UIScope
@Component
public class AppView extends NamedView {

    private final DataManager dataManager;

    @Autowired
    public AppView(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public String getName() {
        return "app";
    }

    @Override
    public String fragmentSuffix() {
        return "/" + BugTab.CAPTION;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String[] parameters = event.getParameters().split("/");
        App app = dataManager.getApp(parameters[0]);
        TabSheet tabSheet = new TabSheet(new BugTab(app.getId(), getNavigationManager(), dataManager),
                new ReportTab(app.getId(), getNavigationManager(), dataManager),
                new StatisticsTab(app.getId(), dataManager), new DeObfuscationTab(app.getId(), dataManager));
        if(SecurityUtils.hasPermission(app.getId(), Permission.Level.ADMIN)){
            tabSheet.addComponent(new PropertiesTab(app, dataManager, getNavigationManager()));
        }
        tabSheet.setSizeFull();
        VerticalLayout content = new VerticalLayout(tabSheet);
        content.setSizeFull();
        Style.apply(content, Style.NO_PADDING, Style.PADDING_LEFT, Style.PADDING_RIGHT, Style.PADDING_BOTTOM);
        setCompositionRoot(content);
        setSizeFull();
        if (parameters.length >= 2) {
            for (com.vaadin.ui.Component component : tabSheet) {
                if (component.getCaption().equals(parameters[1])) {
                    tabSheet.setSelectedTab(component);
                    break;
                }
            }
        }
        tabSheet.addSelectedTabChangeListener(e -> getUI().getPage()
                .setUriFragment(getName() + "/" + app.getId() + "/" + tabSheet.getSelectedTab().getCaption(), false));
    }
}