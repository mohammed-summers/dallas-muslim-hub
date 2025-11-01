package dev.mohdsummers.dallasmuslimhub.views.main;

import com.vaadin.flow.router.Route;
import dev.mohdsummers.dallasmuslimhub.service.EstablishmentService;

@Route(value = "cafe", layout = MainLayout.class)
public class CafeView extends CardFieldView {
    public CafeView(EstablishmentService establishmentService) {
        super(establishmentService);
    }
}
