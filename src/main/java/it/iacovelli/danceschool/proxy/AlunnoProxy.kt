package it.iacovelli.danceschool.proxy

import it.iacovelli.danceschool.exception.AlunnoAlreadyExistsException
import it.iacovelli.danceschool.exception.AlunnoNotFoundException
import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.model.dto.AlunnoDto
import it.iacovelli.danceschool.model.dto.CorsoDto
import it.iacovelli.danceschool.model.dto.PagamentoDto

interface AlunnoProxy {

    fun allActiveStudent(): List<AlunnoDto>

    @Throws(AlunnoAlreadyExistsException::class)
    fun addStudent(alunnoDto: AlunnoDto)

    @Throws(AlunnoNotFoundException::class)
    fun getStudentByFiscalCode(fiscalCode: String): AlunnoDto

    @Throws(AlunnoNotFoundException::class)
    fun editStudent(alunnoDto: AlunnoDto)

    @Throws(AlunnoNotFoundException::class)
    fun getCoursesOfStudent(fiscalCode: String): List<CorsoDto>

    @Throws(AlunnoNotFoundException::class, CorsoNotFoundException::class)
    fun subscribeStudent(fiscalCode: String, id: Long)

    @Throws(AlunnoNotFoundException::class)
    fun getStudentPayments(fiscalCode: String): List<PagamentoDto>

    @Throws(AlunnoNotFoundException::class)
    fun addStudentPayment(pagamentoDto: PagamentoDto): Long

    @Throws(AlunnoNotFoundException::class)
    fun setAlunnoState(state: Boolean, fiscalCode: String)

}
