package dev.mohdsummers.dallasmuslimhub.service;

import dev.mohdsummers.dallasmuslimhub.dto.EstablishmentDto;
import dev.mohdsummers.dallasmuslimhub.entity.Establishment;
import dev.mohdsummers.dallasmuslimhub.exception.ResourceNotFoundException;
import dev.mohdsummers.dallasmuslimhub.repository.EstablishmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentServiceImpl(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    @Override
    public List<EstablishmentDto> getAllEstablishments() {
        List<Establishment> establishments = establishmentRepository.findAll();
        return establishments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EstablishmentDto getEstablishmentByType(String establishmentName) {
        Establishment establishment = establishmentRepository.findEstablishmentByName(establishmentName)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment", "name", establishmentName));
        return convertToDto(establishment);
    }

    @Override
    public String addEstablishment(EstablishmentDto establishmentDto) {
        Establishment establishment = new Establishment();
        BeanUtils.copyProperties(establishmentDto, establishment);
        establishmentRepository.save(establishment);
        return "Establishment has been added";
    }

    @Override
    public String updateEstablishment(EstablishmentDto establishmentDto) {
        Establishment retrievedEstablishment = establishmentRepository.findEstablishmentByName(establishmentDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Establishment", "name", establishmentDto.getName()));

        BeanUtils.copyProperties(establishmentDto, retrievedEstablishment, "id", "createdAt", "updatedAt");
        establishmentRepository.save(retrievedEstablishment);
        return "Establishment has been updated";
    }

    @Override
    public String deleteEstablishment(String restaurantName) {
        Establishment establishment = establishmentRepository.findEstablishmentByName(restaurantName)
                .orElseThrow(() -> new ResourceNotFoundException("Establishment", "name", restaurantName));
        establishmentRepository.delete(establishment);
        return "Establishment has been deleted";
    }

    @Override
    public List<EstablishmentDto> filterEstablishment(String name, String cuisineType, String city, String zipCode) {
        // Implement your filtering logic here
        return establishmentRepository.findAll().stream()
                .filter(e -> (name == null || e.getName().contains(name)) &&
                        (cuisineType == null || e.getCuisine().equals(cuisineType)) &&
                        (city == null || e.getCity().equals(city)) &&
                        (zipCode == null || e.getState().equals(zipCode)))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EstablishmentDto convertToDto(Establishment establishment) {
        EstablishmentDto dto = new EstablishmentDto();
        BeanUtils.copyProperties(establishment, dto);
        return dto;
    }
}