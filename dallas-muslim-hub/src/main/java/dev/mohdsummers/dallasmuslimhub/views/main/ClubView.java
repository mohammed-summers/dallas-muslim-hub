package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.router.Route;
import dev.mohdsummers.dallasmuslimhub.service.EstablishmentService;

@Route(value = "club", layout = MainLayout.class)
public class ClubView extends CardFieldView {
    public ClubView(EstablishmentService establishmentService) {
        super(establishmentService);
    }
}
