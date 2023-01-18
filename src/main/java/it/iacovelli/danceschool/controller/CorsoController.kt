package it.iacovelli.danceschool.controller

import io.swagger.annotations.ApiOperation
import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.helper.BaseResponse
import it.iacovelli.danceschool.helper.MessageType
import it.iacovelli.danceschool.model.dto.AlunnoDto
import it.iacovelli.danceschool.model.dto.CorsoDto
import it.iacovelli.danceschool.proxy.CorsoProxy
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/course")
class CorsoController(private var corsoProxy: CorsoProxy) {

    @GetMapping("/")
    @ApiOperation(value = "Get all active course")
    fun allActiveCourses(): BaseResponse<List<CorsoDto>> {
        val response = BaseResponse<List<CorsoDto>>()
        val allActiveCourse = corsoProxy.getAllActiveCourse()
        response.body = allActiveCourse
        return response
    }

    @PutMapping("/teacher/{fiscalCode}")
    @ApiOperation(value = "Add a course into DB")
    fun addCourse(@RequestBody corso: CorsoDto, @PathVariable("fiscalCode") fiscalCode: String): BaseResponse<Long> {
        val response = BaseResponse<Long>()
        val courseId = corsoProxy.addCourse(corso, fiscalCode)
        response.body = courseId
        response.addMessage(HttpStatus.CREATED, "Corso creato", MessageType.Info)
        return response
    }

    @PostMapping("/")
    @ApiOperation("Edit a course")
    fun editCourse(@RequestBody corso: CorsoDto): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        try {
            corsoProxy.editCourse(corso)
            response.addMessage(HttpStatus.OK, "Il corso è stato modificato correttamente", MessageType.Info)
        } catch (e: CorsoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }

        return response
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a course by Id")
    fun getCorso(@PathVariable("id") id: Long): BaseResponse<CorsoDto> {
        val response = BaseResponse<CorsoDto>()
        try {
            val corso = corsoProxy.getCorsoById(id)
            response.body = corso
        } catch (e: CorsoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }

        return response
    }

    @GetMapping("/{id}/activate")
    @ApiOperation(value = "Set a course to active")
    fun activateCourse(@PathVariable("id") courseId: Long): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        corsoProxy.setCorsoActivated(true, courseId)
        return response.addMessage(HttpStatus.OK, "Il corso è stato attivato", MessageType.Info)
    }

    @GetMapping("/{id}/deactivate")
    @ApiOperation(value = "Set a course to inactive")
    fun deactivateCourse(@PathVariable("id") courseId: Long): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        corsoProxy.setCorsoActivated(false, courseId)
        return response.addMessage(HttpStatus.OK, "Il corso è stato disattivato", MessageType.Info)
    }

    @GetMapping("/{id}/enrolled")
    @ApiOperation(value = "Get all students of a course")
    fun getStudentsOfCourse(@PathVariable("id") id: Long): BaseResponse<List<AlunnoDto>> {
        val response = BaseResponse<List<AlunnoDto>>()
        try {
            val studentsOfCourse = corsoProxy.getStudentsOfCourse(id)
            response.body = studentsOfCourse
        } catch (e: CorsoNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message)
        }

        return response
    }

}
