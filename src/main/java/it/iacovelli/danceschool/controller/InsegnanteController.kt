package it.iacovelli.danceschool.controller

import it.iacovelli.danceschool.exception.InsegnanteNotFoundException
import it.iacovelli.danceschool.helper.BaseResponse
import it.iacovelli.danceschool.model.dto.InsegnanteDto
import it.iacovelli.danceschool.proxy.InsegnanteProxy
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teacher")
class InsegnanteController(private var insegnanteProxy: InsegnanteProxy) {

    @PutMapping("/")
    fun addInsegnante(@RequestBody insegnante: InsegnanteDto): BaseResponse<Void> {
        val response: BaseResponse<Void> = BaseResponse();
        insegnanteProxy.addInsegnante(insegnante);
        return response;
    }

    @PostMapping("/")
    fun editInsegnante(@RequestBody newInsegnante: InsegnanteDto): BaseResponse<Void> {
        val response: BaseResponse<Void> = BaseResponse();
        try {
            insegnanteProxy.editInsegnante(newInsegnante);
        } catch (e: InsegnanteNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message);
        }
        return response;
    }

    @GetMapping("/")
    fun getAllActiveTeacher(): BaseResponse<List<InsegnanteDto>> {
        val response: BaseResponse<List<InsegnanteDto>> = BaseResponse();
        val allActiveTeacher = insegnanteProxy.getAllActiveTeacher();
        response.body = allActiveTeacher;
        return response;
    }

    @GetMapping("/matricola/{fiscalCode}")
    fun getInsegnante(@PathVariable("fiscalCode") fiscalCode: String): BaseResponse<InsegnanteDto> {
        val response: BaseResponse<InsegnanteDto> = BaseResponse();
        try {
            val insegnante = insegnanteProxy.getInsegnanteByFiscalCode(fiscalCode);
            response.body = insegnante;
        } catch (e: InsegnanteNotFoundException) {
            response.addErrorMessage(HttpStatus.NOT_FOUND, e.message);
        }
        return response;
    }

}