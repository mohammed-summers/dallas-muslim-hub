package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "", layout = MainLayout.class)
public class HeroFieldView extends VerticalLayout {

    public HeroFieldView() {
        addClassNames("hero-view");
        setSpacing(false);
        setPadding(true);



        // Hero Content
        VerticalLayout heroContent = createHeroContent();

        // Popular Establishments Section
        VerticalLayout popularSection = createPopularSection();

        add(heroContent, popularSection);
    }

    private VerticalLayout createHeroContent() {
        VerticalLayout heroContent = new VerticalLayout();
        heroContent.addClassNames("hero-content");
        heroContent.setAlignItems(Alignment.CENTER);
        heroContent.setSpacing(false);

        H1 titleLine1 = new H1("Discover Muslim");
        titleLine1.getStyle().setColor("white");

        H1 titleLine2 = new H1("Establishments in Dallas");
        titleLine2.getStyle().setColor("black");

        H4 subTitle = new H4("Your one-stop platform for finding muslim establishments in Dallas, TX");
        subTitle.getStyle().setColor("white");
        subTitle.addClassNames(LumoUtility.TextAlignment.CENTER);

        heroContent.add(titleLine1, titleLine2, subTitle);
        return heroContent;
    }

    private VerticalLayout createPopularSection() {
        VerticalLayout popularSection = new VerticalLayout();
        popularSection.addClassNames("popular-section");

        H2 sectionTitle = new H2("Popular Establishments");
        sectionTitle.addClassNames(LumoUtility.TextAlignment.CENTER);

        popularSection.add(sectionTitle);
        return popularSection;
    }
}