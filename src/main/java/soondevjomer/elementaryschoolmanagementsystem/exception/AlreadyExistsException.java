package soondevjomer.elementaryschoolmanagementsystem.exception;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String name, String field, String value) {
        super(name + " with " + field + " of " + value + " is already exists.");
    }
}
