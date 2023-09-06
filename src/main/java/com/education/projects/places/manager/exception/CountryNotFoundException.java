package com.education.projects.places.manager.exception;

import java.util.UUID;

public class CountryNotFoundException extends Exception{
    public CountryNotFoundException(UUID countryId) {
        super("The country with id " + countryId + " wasn't found");
    }
}
