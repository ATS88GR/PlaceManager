package com.education.projects.places.manager.exception;

import java.util.UUID;

public class PlaceNotFoundException extends Exception {
    public PlaceNotFoundException(UUID placeId) {
        super("The place with id " + placeId + " wasn't found");
    }
}
