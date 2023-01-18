package it.iacovelli.danceschool.exception

/**
 * This exception is thrown when the user searched was not found in the database
 */
class AlunnoNotFoundException(message: String) : RuntimeException(message)
