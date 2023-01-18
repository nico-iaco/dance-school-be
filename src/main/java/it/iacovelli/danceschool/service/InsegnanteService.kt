package it.iacovelli.danceschool.service

import it.iacovelli.danceschool.exception.InsegnanteNotFoundException
import it.iacovelli.danceschool.model.Insegnante

interface InsegnanteService {

    fun allActiveTeacher(): List<Insegnante>

    fun addInsegnante(insegnante: Insegnante)

    @Throws(InsegnanteNotFoundException::class)
    fun editInsegnante(insegnante: Insegnante)

    @Throws(InsegnanteNotFoundException::class)
    fun getInsegnanteByFiscalCode(fiscalCode: String): Insegnante

}
