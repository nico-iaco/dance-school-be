package it.iacovelli.danceschool.exception

/**
 * This exception is thrown when the user searched was not found in the database
 * @version 1.0
 * @author nicola.iacovelli
 */
class CorsoNotFoundException(message: String) : RuntimeException(message)
