package dev.mohdsummers.dallasmuslimhub.dto;

import dev.mohdsummers.dallasmuslimhub.entity.Chain;
import dev.mohdsummers.dallasmuslimhub.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstablishmentDto {
    private Integer id;
    private String name;
    private String category;
    private String cuisineType;
    private String description;
    private String phoneNumber;
    private String website;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String googleMapUrl;
    private Chain chain;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}