package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dev.mohdsummers.dallasmuslimhub.dto.EstablishmentDto;
import dev.mohdsummers.dallasmuslimhub.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("card-field-view")
public class CardFieldView extends VerticalLayout {
    private final EstablishmentService establishmentService;
    private final Div cardContainer;

    @Autowired
    public CardFieldView(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
        this.cardContainer = new Div();
        cardContainer.addClassName("card-container");

        // Create input fields
        TextField nameField = new TextField("Name");
        TextField cuisineTypeField = new TextField("Cuisine Type");
        TextField cityField = new TextField("City");
        TextField zipCodeField = new TextField("Zip Code");



        Button filterButton = new Button("Filter", event -> {
            if (nameField.isEmpty() && cuisineTypeField.isEmpty() && cityField.isEmpty()) {
                refreshCards();
                return;
            }
            List<EstablishmentDto> filteredEstablishments = establishmentService.filterEstablishment(
                    nameField.getValue(),
                    cuisineTypeField.getValue(),
                    cityField.getValue(),
                    zipCodeField.getValue()
            );
            displayEstablishments(filteredEstablishments);
        });

        // Create layouts
        HorizontalLayout formLayout = new HorizontalLayout(
                nameField, cityField, zipCodeField, cuisineTypeField, filterButton
        );
        formLayout.setAlignItems(Alignment.END);
//        HorizontalLayout buttonLayout = new HorizontalLayout(filterButton);

        // Add components
        add(formLayout, cardContainer);

        // Initial load
        refreshCards();
    }

    private void refreshCards() {
        List<EstablishmentDto> establishments = establishmentService.getAllEstablishments();
        displayEstablishments(establishments);
    }

    private void displayEstablishments(List<EstablishmentDto> establishments) {
        cardContainer.removeAll();
        establishments.forEach(establishment -> {
            Div card = createEstablishmentCard(establishment);
            cardContainer.add(card);
        });
    }

    private Div createEstablishmentCard(EstablishmentDto establishment) {
        Div card = new Div();
        card.addClassName("business-card");

        // Create title and category
        H3 title = new H3(establishment.getName());
        title.addClassName("card-title");

        Paragraph cuisineType = new Paragraph(establishment.getCuisine());
        cuisineType.addClassName("card-cuisine-type");

        // Create header
        Div headerContainer = new Div(title, cuisineType);
        headerContainer.addClassName("card-header");

        // Description container
        Div descriptionContainer = new Div();
        Paragraph description = new Paragraph(establishment.getDescription());
        description.addClassName("card-description");

        Button expandButton = new Button("Show more");
        expandButton.addClassName("expand-button");

        expandButton.addClickListener(event -> {
            if (description.hasClassName("expanded")) {
                description.removeClassName("expanded");
                expandButton.setText("Show more");
            } else {
                description.addClassName("expanded");
                expandButton.setText("Show less");
            }
        });

        descriptionContainer.add(description, expandButton);


        Icon addressIcon = new Icon(VaadinIcon.MAP_MARKER);
        addressIcon.addClassName("card-address-icon");


        // Address header and content
        String fullAddress = String.format("%s, %s, %s %s",
                establishment.getAddress(),
                establishment.getCity(),
                establishment.getState(),
                establishment.getZipCode()
        );

        Paragraph address = new Paragraph(fullAddress);
        address.addClassName("card-address");

        HorizontalLayout addressLayout = new HorizontalLayout(addressIcon, address);
        addressLayout.setAlignItems(Alignment.CENTER);
        addressLayout.setSpacing(false);
        addressLayout.addClassName(".card-phone-number");


        // Website with icon
        HorizontalLayout websiteLayout = new HorizontalLayout();
        websiteLayout.setAlignItems(Alignment.CENTER);
        websiteLayout.setSpacing(false);
        websiteLayout.addClassName("card-website");

        Icon websiteIcon = new Icon(VaadinIcon.GLOBE_WIRE);
        websiteIcon.addClassName("card-website-icon");

        Anchor websiteLink = new Anchor(establishment.getWebsite(), "Visit Website");
        websiteLink.setTarget("_blank");

        if (establishment.getWebsite() != null && !establishment.getWebsite().isEmpty()) {
            websiteLayout.add(websiteIcon, websiteLink);
        } else {
            websiteLayout.add(websiteIcon, new Paragraph("Website not available"));
        }

        // Phone number with icon
        HorizontalLayout phoneLayout = new HorizontalLayout();
        phoneLayout.setAlignItems(Alignment.CENTER);
        phoneLayout.setSpacing(false);
        phoneLayout.addClassName("card-phone-number");

        Icon phoneIcon = new Icon(VaadinIcon.PHONE);
        phoneIcon.addClassName("card-phone-icon");

        Paragraph phoneNumber = new Paragraph(establishment.getPhoneNumber() != null ? establishment.getPhoneNumber() : "Phone number not available");
        phoneLayout.add(phoneIcon, phoneNumber);

        // Action buttons
        Button deleteButton = new Button("Delete", event -> {
            establishmentService.deleteEstablishment(establishment.getName());
            refreshCards();
            Notification.show("Establishment deleted!");
        });

        HorizontalLayout actions = new HorizontalLayout(deleteButton);
        actions.addClassName("card-actions");

        // Add everything to the card
        card.add(headerContainer, descriptionContainer, addressLayout, websiteLayout, phoneLayout, actions);
        return card;
    }
}