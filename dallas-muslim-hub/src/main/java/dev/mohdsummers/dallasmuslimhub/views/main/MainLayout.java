package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;


public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
    }

    private void createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.addClassNames("header");

        // Logo
        H1 logo = new H1("DHS");
        logo.addClassNames("logo");

        // Navigation Links
        HorizontalLayout nav = new HorizontalLayout();
        nav.addClassNames("nav-links");

        RouterLink home = new RouterLink("Home", HeroFieldView.class);
        RouterLink restaurants = new RouterLink("Restaurants", RestaurantView.class);
        RouterLink supermarkets = new RouterLink("Supermarkets", SupermarketView.class);
        RouterLink cafes = new RouterLink("Cafe", CafeView.class);
        RouterLink clubs = new RouterLink("Clubs", ClubView.class);

        nav.add(home, restaurants, supermarkets, cafes, clubs);

        // Login Button
        Span loginButton = new Span("Login");
        loginButton.addClassNames("login-button");

        header.add(logo, nav, loginButton);
        addToNavbar(header);
    }
}
