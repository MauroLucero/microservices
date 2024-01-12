package com.zenith.inventory_service.models.entities.dtos;

public record BaseResponse(String[] errorMessages) {

    public boolean hasErrors(){
        return errorMessages != null && errorMessages.length > 0;
    }
}
