package com.education.projects.places.manager.exception;

import lombok.Getter;

@Getter
public class EmptyException extends Exception{
    public EmptyException(){
        super("The list is empty");
    }
}
