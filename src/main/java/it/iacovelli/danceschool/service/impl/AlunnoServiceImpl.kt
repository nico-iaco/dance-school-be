package it.iacovelli.danceschool.service.impl

import it.iacovelli.danceschool.exception.AlunnoAlreadyExistsException
import it.iacovelli.danceschool.exception.AlunnoNotFoundException
import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.mapper.PagamentoMapper
import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Corso
import it.iacovelli.danceschool.model.Iscrizione
import it.iacovelli.danceschool.model.Pagamento
import it.iacovelli.danceschool.model.dto.PagamentoDto
import it.iacovelli.danceschool.repository.AlunnoRepository
import it.iacovelli.danceschool.repository.IscrizioneRepository
import it.iacovelli.danceschool.repository.PagamentoRepository
import it.iacovelli.danceschool.service.AlunnoService
import it.iacovelli.danceschool.service.CorsoService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.time.LocalDate
import jakarta.transaction.Transactional

/**
 * This is the service class which exposes the student services
 * @version 1.0
 * @author nicola.iacovelli
 */
@Service
open class AlunnoServiceImpl(
    /**
     * This is the repository to access to [Alunno] entity
     */
    private var alunnoRepository: AlunnoRepository,
    /**
     * This is the class to access to [Corso] data
     */
    private var corsoService: CorsoService,
    /**
     * This is the repository to access to [Iscrizione] entity
     */
    private var iscrizioneRepository: IscrizioneRepository,
    /**
     * This is the repository to access to [Pagamento] entity
     */
    private var pagamentoRepository: PagamentoRepository,
    private var pagamentoMapper: PagamentoMapper
) : AlunnoService {

    /**
     * This method will add a student to the database
     * @param alunno represent the [Alunno] to save
     * @return the id of the student added into database
     * @throws AlunnoAlreadyExistsException if the student already exists
     */
    @Throws(AlunnoAlreadyExistsException::class)
    override fun addStudent(alunno: Alunno) {
        try {
            alunno.active = true
            val present = alunnoRepository.getAlunnoByFiscalCode(alunno.fiscalCode).isPresent
            if (present) {
                throw AlunnoAlreadyExistsException("L'alunno è già esistente")
            }
            alunnoRepository.save(alunno)
        } catch (e: DataIntegrityViolationException) {
            throw AlunnoAlreadyExistsException(e.message!!)
        }

    }

    /**
     * This method will edit an existing user
     * @param alunno the object with the new information about the student
     * @throws AlunnoNotFoundException if the student was not found
     */
    @Throws(AlunnoNotFoundException::class)
    override fun editStudent(alunno: Alunno) {
        val student = getStudentByFiscalCode(alunno.fiscalCode)
        updateStudent(student, alunno)
        alunnoRepository.save(student)
    }

    /**
     * This method is used to get a student by matricola
     * @param matricola represent the student's ID
     * @return the Dto of the student with that id
     * @throws AlunnoAlreadyExistsException if the student was not found
     */
    @Throws(AlunnoNotFoundException::class)
    override fun getStudentByFiscalCode(fiscalCode: String): Alunno {
        return alunnoRepository.getAlunnoByFiscalCode(fiscalCode).orElseThrow { AlunnoNotFoundException("Alunno non presente nel DB") }
    }

    /**
     * this method is used to get all active students at the moment
     * @return list of student Dto with all students now active
     */
    override fun allActiveStudent(): List<Alunno> {
        return alunnoRepository.getAllByActiveTrue()
    }

    /**
     * This method is used to get all courses of a student
     * @param matricola represent the student's ID
     * @return list of course Dto in which the student is enrolled
     * @throws AlunnoNotFoundException if the student was not found
     */
    @Transactional
    @Throws(AlunnoNotFoundException::class)
    override fun getCoursesOfStudent(fiscalCode: String): List<Corso> {
        val alunno = getStudentByFiscalCode(fiscalCode)
        val iscrizioni = iscrizioneRepository.findAllByStudent(alunno)
        return iscrizioni.stream().map<Corso>{i -> i.course}.toList()
    }

    /**
     * This method will subscribe a student to a course
     * @param matricola represent the student's ID
     * @param id represent the course's ID
     * @throws AlunnoNotFoundException if the student was not found
     * @throws CorsoNotFoundException if the course was not found
     */
    @Throws(AlunnoNotFoundException::class, CorsoNotFoundException::class)
    override fun subscribeStudent(fiscalCode: String, id: Long) {
        val alunno = getStudentByFiscalCode(fiscalCode)
        val corso = corsoService.getCorsoById(id)
        val iscrizione = Iscrizione()
        iscrizione.course = corso
        iscrizione.student = alunno
        iscrizione.subscribeDate = LocalDate.now()
        iscrizioneRepository.save(iscrizione)
    }

    /**
     * This method is used to get the student's payments
     * @param matricola represent the student's ID
     * @return list of payment Dto of the student's payments
     * @throws AlunnoNotFoundException if the student was not found
     */
    @Throws(AlunnoNotFoundException::class)
    override fun getStudentPayments(fiscalCode: String): List<Pagamento> {
        val alunno = getStudentByFiscalCode(fiscalCode)
        return alunnoRepository.getStudentPayments(alunno.fiscalCode)
    }

    /**
     * This method will add a payment to a student
     * @param matricola represent the student's ID
     * @param pagamento represent the [Pagamento] to add
     * @return the id of the payment saved into database
     * @throws AlunnoNotFoundException if the student was not found
     */
    @Throws(AlunnoNotFoundException::class)
    override fun addStudentPayment(pagamentoDto: PagamentoDto): Long {
        val alunno = getStudentByFiscalCode(pagamentoDto.studentId)
        val pagamento : Pagamento = pagamentoMapper.dtoToPagamento(pagamentoDto)
        pagamento.student = alunno
        val save = pagamentoRepository.save(pagamento)
        return save.id
    }

    /**
     * This method will set the active flag on [Alunno] to true o false
     * @param state represent the next state of student
     * @param matricola represent the student's ID
     */
    @Throws(AlunnoNotFoundException::class)
    override fun setAlunnoState(state: Boolean, fiscalCode: String) {
        getStudentByFiscalCode(fiscalCode)
        alunnoRepository.setAlunnoState(state, fiscalCode)
    }

    /**
     * This method will set the new information about the student into the detached instance from the db
     * @param studentToEdit the detached instance of student from the db
     * @param newStudent the instance with new information about the student from the client
     */
    private fun updateStudent(studentToEdit: Alunno, newStudent: Alunno) {
        studentToEdit.name = newStudent.name
        studentToEdit.surname = newStudent.surname
        studentToEdit.active = newStudent.active
        studentToEdit.address = newStudent.address
        studentToEdit.city = newStudent.city
        studentToEdit.cap = newStudent.cap
        studentToEdit.telephone = newStudent.telephone
    }

}
