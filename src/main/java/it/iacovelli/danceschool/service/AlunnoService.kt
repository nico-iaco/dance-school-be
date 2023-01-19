package it.iacovelli.danceschool.service

import it.iacovelli.danceschool.exception.AlunnoAlreadyExistsException
import it.iacovelli.danceschool.exception.AlunnoNotFoundException
import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Corso
import it.iacovelli.danceschool.model.Pagamento
import it.iacovelli.danceschool.model.dto.PagamentoDto

interface AlunnoService {

    fun allActiveStudent(): List<Alunno>

    @Throws(AlunnoAlreadyExistsException::class)
    fun addStudent(alunno: Alunno)

    fun editStudent(alunno: Alunno)

    @Throws(AlunnoNotFoundException::class)
    fun getStudentByFiscalCode(fiscalCode: String): Alunno

    @Throws(AlunnoNotFoundException::class)
    fun getCoursesOfStudent(fiscalCode: String): List<Corso>

    @Throws(AlunnoNotFoundException::class, CorsoNotFoundException::class)
    fun subscribeStudent(fiscalCode: String, id: Long)

    @Throws(AlunnoNotFoundException::class)
    fun getStudentPayments(fiscalCode: String): List<Pagamento>

    @Throws(AlunnoNotFoundException::class)
    fun addStudentPayment(pagamentoDto: PagamentoDto): Long

    @Throws(AlunnoNotFoundException::class)
    fun setAlunnoState(state: Boolean, fiscalCode: String)
}
