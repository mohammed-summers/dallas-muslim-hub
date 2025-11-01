package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.router.Route;
import dev.mohdsummers.dallasmuslimhub.service.EstablishmentService;

@Route(value = "supermarkets", layout = MainLayout.class)
public class SupermarketView extends CardFieldView {
    public SupermarketView(EstablishmentService establishmentService) {
        super(establishmentService);
    }
}
