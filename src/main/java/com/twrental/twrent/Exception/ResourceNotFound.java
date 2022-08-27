package com.twrental.twrent.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//I didn't need it, but I left it just for the future!
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String name;
    private String field;
    private Object value;

    public ResourceNotFound(String name, String field, Object value) {
        super(String.format(" %s not found with %s : '%s' ", name,field,value));
        this.name = name;
        this.field = field;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}

