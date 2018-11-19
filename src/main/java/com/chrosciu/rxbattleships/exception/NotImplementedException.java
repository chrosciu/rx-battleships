package com.chrosciu.rxbattleships.exception;

/**
 * Convenient implementation of RuntimeException for signalizing missing implementation
 */
public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super("Not implemented yet!");
    }
}
