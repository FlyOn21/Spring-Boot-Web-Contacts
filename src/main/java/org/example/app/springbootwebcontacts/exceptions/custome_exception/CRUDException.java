package org.example.app.springbootwebcontacts.exceptions.custome_exception;

import lombok.Getter;

@Getter
public class CRUDException extends RuntimeException{
        private final String fragmentName;

        public CRUDException(String message, String fragmentName) {
            super(message);
            this.fragmentName = fragmentName;
        }
}
