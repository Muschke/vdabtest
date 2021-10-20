package be.vdab.frituurfrida.exceptions;

import be.vdab.frituurfrida.repositories.SausRepository;

public class SausRepositoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SausRepositoryException(String message) {
        super(message);
    }
}
