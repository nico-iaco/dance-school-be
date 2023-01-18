package it.iacovelli.danceschool.exception

import org.springframework.dao.DataIntegrityViolationException

/**
 * This exception is thrown when the user try to insert a student already present in the database
 * @version 1.0
 * @author nicola.iacovelli
 */
class AlunnoAlreadyExistsException(msg: String) : DataIntegrityViolationException(msg)
