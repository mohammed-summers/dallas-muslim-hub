package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
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
        TextField descriptionField = new TextField("Description");
        TextField addressField = new TextField("Address");
        TextField cityField = new TextField("City");
        TextField stateField = new TextField("State");
        TextField zipCodeField = new TextField("Zip Code");
        TextField cuisineTypeField = new TextField("Cuisine Type");

        // Create buttons
        Button addButton = new Button("Add", event -> {
            EstablishmentDto newEstablishment = new EstablishmentDto();
            newEstablishment.setName(nameField.getValue());
            newEstablishment.setDescription(descriptionField.getValue());
            newEstablishment.setAddress(addressField.getValue());
            newEstablishment.setCity(cityField.getValue());
            newEstablishment.setState(stateField.getValue());
            newEstablishment.setZipCode(zipCodeField.getValue());
            newEstablishment.setCuisineType(cuisineTypeField.getValue());

            establishmentService.addEstablishment(newEstablishment);
            refreshCards();
            Notification.show("Establishment added!");
        });

        Button filterButton = new Button("Filter", event -> {
            if (nameField.isEmpty() && cuisineTypeField.isEmpty() && cityField.isEmpty() && stateField.isEmpty()) {
                refreshCards();
                return;
            }
            List<EstablishmentDto> filteredEstablishments = establishmentService.filterEstablishment(
                    nameField.getValue(),
                    cuisineTypeField.getValue(),
                    cityField.getValue(),
                    stateField.getValue()
            );
            displayEstablishments(filteredEstablishments);
        });

        // Create layouts
        HorizontalLayout formLayout = new HorizontalLayout(
                nameField, descriptionField, addressField, cityField,
                stateField, zipCodeField, cuisineTypeField
        );
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, filterButton);

        // Add components
        add(formLayout, buttonLayout, cardContainer);

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

        H4 category = new H4(establishment.getCuisineType());
        category.addClassName("card-category");

        // Create header
        Div headerContainer = new Div(title, category);
        headerContainer.addClassName("card-header");

        // Description
        Paragraph description = new Paragraph(establishment.getDescription());
        description.addClassName("card-description");

        // Address
        String fullAddress = String.format("%s, %s, %s %s",
                establishment.getAddress(),
                establishment.getCity(),
                establishment.getState(),
                establishment.getZipCode()
        );
        Paragraph address = new Paragraph(fullAddress);
        address.addClassName("card-address");

        // Action buttons
        Button deleteButton = new Button("Delete", event -> {
            establishmentService.deleteEstablishment(establishment.getName());
            refreshCards();
            Notification.show("Establishment deleted!");
        });

        HorizontalLayout actions = new HorizontalLayout(deleteButton);
        actions.addClassName("card-actions");

        // Add everything to the card
        card.add(headerContainer, description, address, actions);
        return card;
    }
}