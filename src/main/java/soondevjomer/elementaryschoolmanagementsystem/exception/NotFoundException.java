package soondevjomer.elementaryschoolmanagementsystem.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String name, String field, String value) {
        super(name + " with " + field + " of " + value + " not found.");
    }
}
