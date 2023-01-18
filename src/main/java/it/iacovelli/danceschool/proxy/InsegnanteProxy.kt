package it.iacovelli.danceschool.proxy

import it.iacovelli.danceschool.exception.InsegnanteNotFoundException
import it.iacovelli.danceschool.model.dto.InsegnanteDto

interface InsegnanteProxy {

    fun getAllActiveTeacher(): List<InsegnanteDto>

    fun addInsegnante(insegnanteDto: InsegnanteDto)

    @Throws(InsegnanteNotFoundException::class)
    fun editInsegnante(insegnanteDto: InsegnanteDto)

    @Throws(InsegnanteNotFoundException::class)
    fun getInsegnanteByFiscalCode(fiscalCode: String): InsegnanteDto

}
