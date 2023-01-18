package it.iacovelli.danceschool.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import it.iacovelli.danceschool.exception.AlunnoAlreadyExistsException
import it.iacovelli.danceschool.exception.AlunnoNotFoundException
import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.helper.BaseResponse
import it.iacovelli.danceschool.helper.MessageType
import it.iacovelli.danceschool.model.dto.AlunnoDto
import it.iacovelli.danceschool.model.dto.CorsoDto
import it.iacovelli.danceschool.model.dto.PagamentoDto
import it.iacovelli.danceschool.proxy.AlunnoProxy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student")
@Api(value = "Alunno Management System")
class AlunnoController {

    @Autowired
    private lateinit var alunnoProxy: AlunnoProxy

    @GetMapping("/")
    @ApiOperation(value = "Get all active student")
    fun allActiveStudents(): BaseResponse<List<AlunnoDto>> {
        val response = BaseResponse<List<AlunnoDto>>()
        val allActiveStudent = alunnoProxy.allActiveStudent()
        response.body = allActiveStudent
        return response
    }

    @PutMapping("/")
    @ApiOperation(value = "Add a student into DB")
    fun addStudent(@RequestBody alunno: AlunnoDto): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        try {
            alunnoProxy.addStudent(alunno)
            response.addMessage(HttpStatus.CREATED, "Alunno creato", MessageType.Info)
        } catch (e: AlunnoAlreadyExistsException) {
            response.addErrorMessage(HttpStatus.BAD_REQUEST, e.message)
        }
        return response
    }

    @PostMapping("/")
    @ApiOperation(value = "Edit a student")
    fun editStudent(@RequestBody alunno: AlunnoDto): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        try {
            alunnoProxy.editStudent(alunno)
            response.addMessage(HttpStatus.OK, "Lo studente è stato modificato correttamente", MessageType.Info)
        } catch (e: AlunnoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }
        return response
    }

    @GetMapping("/{fiscalCode}")
    @ApiOperation(value = "Get a student by matricola")
    fun getStudent(@PathVariable("fiscalCode") fiscalCode: String): BaseResponse<AlunnoDto> {
        val response = BaseResponse<AlunnoDto>()
        try {
            val alunno = alunnoProxy.getStudentByFiscalCode(fiscalCode)
            response.body = alunno
        } catch (e: AlunnoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, "Studente non trovato")
        }

        return response
    }

    @GetMapping("{fiscalCode}/activate")
    @ApiOperation(value = "Set a user to active")
    fun activateStudent(@PathVariable("fiscalCode") fiscalCode: String): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        alunnoProxy.setAlunnoState(true, fiscalCode)
        return response.addMessage(HttpStatus.OK, "Studente attivato", MessageType.Info)
    }

    @GetMapping("{fiscalCode}/deactivate")
    @ApiOperation(value = "Set a user to inactive")
    fun deactivateStudent(@PathVariable("fiscalCode") fiscalCode: String): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        alunnoProxy.setAlunnoState(false, fiscalCode)
        return response.addMessage(HttpStatus.OK, "Studente disattivato", MessageType.Info)
    }

    @GetMapping("/{fiscalCode}/course")
    @ApiOperation(value = "Get all courses of a student")
    fun getCourseOfStudent(@PathVariable("fiscalCode") fiscalCode: String): BaseResponse<List<CorsoDto>> {
        val response = BaseResponse<List<CorsoDto>>()
        try {
            val courseOfStudent = alunnoProxy.getCoursesOfStudent(fiscalCode)
            response.body = courseOfStudent
        } catch (e: AlunnoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }

        return response
    }

    @PostMapping("/{fiscalCode}/course/{id}")
    @ApiOperation(value = "Subscribe a student to a course")
    fun subscribeStudentToCourse(@PathVariable("fiscalCode") fiscalCode: String, @PathVariable("id") id: Long): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        try {
            alunnoProxy.subscribeStudent(fiscalCode, id)
            response.addMessage(HttpStatus.CREATED, "L'alunno è stato iscritto al corso", MessageType.Info)
        } catch (e: AlunnoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        } catch (e: CorsoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }

        return response
    }

    @PostMapping("/{fiscalCode}/payment")
    @ApiOperation(value = "Get all payments of a student")
    fun getStudentPayments(@PathVariable("fiscalCode") fiscalCode: String): BaseResponse<List<PagamentoDto>> {
        val response = BaseResponse<List<PagamentoDto>>()
        try {
            val studentPayments = alunnoProxy.getStudentPayments(fiscalCode)
            response.body = studentPayments
        } catch (e: AlunnoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }

        return response
    }

    @PutMapping("/payment")
    @ApiOperation(value = "Add a student's payment")
    fun addStudentPayment(@RequestBody pagamentoDto: PagamentoDto): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        try {
            alunnoProxy.addStudentPayment(pagamentoDto)
            response.addMessage(HttpStatus.CREATED, "Pagamento creato", MessageType.Info)
        } catch (e: AlunnoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }

        return response
    }

}
