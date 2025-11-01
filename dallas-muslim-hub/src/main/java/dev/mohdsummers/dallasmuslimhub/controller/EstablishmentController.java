package dev.mohdsummers.dallasmuslimhub.controller;

import dev.mohdsummers.dallasmuslimhub.dto.EstablishmentDto;
import dev.mohdsummers.dallasmuslimhub.service.EstablishmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping
    @Operation(summary = "Get all establishments", description = "Retrieves a list of all establishments")
    public ResponseEntity<List<EstablishmentDto>> getAllEstablishments() {
        return new ResponseEntity<>(establishmentService.getAllEstablishments(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get establishment by name", description = "Retrieve an establishment using its name")
    public ResponseEntity<EstablishmentDto> getEstablishmentByName(@PathVariable String name) {
        return new ResponseEntity<>(establishmentService.getEstablishmentByType(name), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add a new establishment", description = "Create a new establishment entry")
    @ApiResponse(responseCode = "201", description = "Establishment created successfully")
    public ResponseEntity<String> addEstablishment(@RequestBody EstablishmentDto establishmentDto) {
        return new ResponseEntity<>(establishmentService.addEstablishment(establishmentDto), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update an establishment", description = "Update an existing establishment's details")
    public ResponseEntity<String> updateEstablishment(@RequestBody EstablishmentDto establishmentDto) {
        return new ResponseEntity<>(establishmentService.updateEstablishment(establishmentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Delete an establishment", description = "Delete an establishment by its name")
    public ResponseEntity<String> deleteEstablishment(@PathVariable String name) {
        return new ResponseEntity<>(establishmentService.deleteEstablishment(name), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search establishments", description = "Search establishments using various parameters")
    public ResponseEntity<List<EstablishmentDto>> searchEstablishment(
            @RequestParam(required = false) String name,
            @RequestParam(value = "cuisine", required = false) String cuisineType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {
        List<EstablishmentDto> establishmentDtoList = establishmentService.filterEstablishment(
                name, cuisineType, city, state);
        return new ResponseEntity<>(establishmentDtoList, HttpStatus.OK);
    }
}