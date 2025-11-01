package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.router.Route;
import dev.mohdsummers.dallasmuslimhub.service.EstablishmentService;

@Route(value = "restaurants", layout = MainLayout.class)
public class RestaurantView extends CardFieldView {
    public RestaurantView(EstablishmentService establishmentService) {
        super(establishmentService);
    }
}
