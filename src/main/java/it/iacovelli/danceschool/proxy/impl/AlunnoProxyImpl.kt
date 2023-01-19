package it.iacovelli.danceschool.proxy.impl

import it.iacovelli.danceschool.exception.AlunnoAlreadyExistsException
import it.iacovelli.danceschool.exception.AlunnoNotFoundException
import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.mapper.AlunnoMapper
import it.iacovelli.danceschool.mapper.CorsoMapper
import it.iacovelli.danceschool.mapper.PagamentoMapper
import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.dto.AlunnoDto
import it.iacovelli.danceschool.model.dto.CorsoDto
import it.iacovelli.danceschool.model.dto.PagamentoDto
import it.iacovelli.danceschool.proxy.AlunnoProxy
import it.iacovelli.danceschool.service.AlunnoService
import org.springframework.stereotype.Component

@Component
open class AlunnoProxyImpl(
    private var alunnoService: AlunnoService,
    private var alunnoMapper: AlunnoMapper,
    private var corsoMapper: CorsoMapper,
    private var pagamentoMapper: PagamentoMapper
) : AlunnoProxy {

    override fun allActiveStudent(): List<AlunnoDto> {
        return alunnoService.allActiveStudent().stream().map { a -> alunnoMapper.alunnoToDto(a)}.toList()
    }

    @Throws(AlunnoAlreadyExistsException::class)
    override fun addStudent(alunnoDto: AlunnoDto) {
        val alunno : Alunno = alunnoMapper.dtoToAlunno(alunnoDto)
        alunnoService.addStudent(alunno)
    }

    @Throws(AlunnoNotFoundException::class)
    override fun getStudentByFiscalCode(fiscalCode: String): AlunnoDto {
        val student = alunnoService.getStudentByFiscalCode(fiscalCode)
        return alunnoMapper.alunnoToDto(student)
    }

    @Throws(AlunnoNotFoundException::class)
    override fun editStudent(alunnoDto: AlunnoDto) {
        val alunno = alunnoService.getStudentByFiscalCode(alunnoDto.fiscalCode)
        alunnoMapper.updateAlunnoFromDto(alunnoDto, alunno)
        alunnoService.editStudent(alunno)
    }

    @Throws(AlunnoNotFoundException::class)
    override fun getCoursesOfStudent(fiscalCode: String): List<CorsoDto> {
        return alunnoService.getCoursesOfStudent(fiscalCode).stream().map { c -> corsoMapper.corsoToDto(c) }.toList()
    }

    @Throws(AlunnoNotFoundException::class, CorsoNotFoundException::class)
    override fun subscribeStudent(fiscalCode: String, id: Long) {
        alunnoService.subscribeStudent(fiscalCode, id)
    }

    @Throws(AlunnoNotFoundException::class)
    override fun getStudentPayments(fiscalCode: String): List<PagamentoDto> {
        return alunnoService.getStudentPayments(fiscalCode).stream().map { p -> pagamentoMapper.pagamentoToDto(p) }.toList()
    }

    @Throws(AlunnoNotFoundException::class)
    override fun addStudentPayment(pagamentoDto: PagamentoDto): Long {
        return alunnoService.addStudentPayment(pagamentoDto)
    }

    @Throws(AlunnoNotFoundException::class)
    override fun setAlunnoState(state: Boolean, fiscalCode: String) {
        alunnoService.setAlunnoState(state, fiscalCode)
    }

}
