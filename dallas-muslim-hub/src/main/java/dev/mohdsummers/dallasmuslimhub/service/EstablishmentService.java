package dev.mohdsummers.dallasmuslimhub.service;

import dev.mohdsummers.dallasmuslimhub.dto.EstablishmentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstablishmentService {
    List<EstablishmentDto> getAllEstablishments();
    EstablishmentDto getEstablishmentByType(String establishmentName);
    String addEstablishment(EstablishmentDto establishmentDto);
    String updateEstablishment(EstablishmentDto establishmentDto);
    String deleteEstablishment(String restaurantName);
    List<EstablishmentDto> filterEstablishment(String name, String cuisineType, String city, String state);
}
