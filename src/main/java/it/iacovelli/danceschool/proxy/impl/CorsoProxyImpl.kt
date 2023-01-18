package it.iacovelli.danceschool.proxy.impl

import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.mapper.AlunnoMapper
import it.iacovelli.danceschool.mapper.CorsoMapper
import it.iacovelli.danceschool.model.dto.AlunnoDto
import it.iacovelli.danceschool.model.dto.CorsoDto
import it.iacovelli.danceschool.model.dto.SubscriptionDto
import it.iacovelli.danceschool.proxy.CorsoProxy
import it.iacovelli.danceschool.service.CorsoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional
import kotlin.streams.toList

@Component
open class CorsoProxyImpl : CorsoProxy {

    @Autowired
    private val corsoService: CorsoService? = null

    @Autowired
    private val corsoMapper : CorsoMapper? = null

    @Autowired
    private val alunnoMapper : AlunnoMapper? = null

    override fun addCourse(corsoDto: CorsoDto, fiscalCode: String): Long {
        val corso = corsoMapper!!.dtoToCorso(corsoDto)
        return corsoService!!.addCourse(corso, fiscalCode)
    }

    @Throws(CorsoNotFoundException::class)
    override fun editCourse(corsoDto: CorsoDto) {
        val corso = corsoMapper!!.dtoToCorso(corsoDto)
        corsoService!!.editCorso(corso)
    }

    @Transactional
    @Throws(CorsoNotFoundException::class)
    override fun getCorsoById(id: Long): CorsoDto {
        val corsoById = corsoService!!.getCorsoById(id)
        return corsoMapper!!.corsoToDto(corsoById)
    }

    @Transactional
    override fun getAllActiveCourse(): List<CorsoDto> {
        return corsoService!!.allActiveCourse().stream().map { c -> corsoMapper!!.corsoToDto(c) }.toList()
    }

    @Throws(CorsoNotFoundException::class)
    override fun setCorsoActivated(state: Boolean, courseId: Long) {
        corsoService!!.setCorsoActivated(state, courseId)
    }

    @Throws(CorsoNotFoundException::class)
    override fun getStudentsOfCourse(id: Long): List<AlunnoDto> {
        return corsoService!!.getStudentsOfCourse(id).stream().map { a -> alunnoMapper!!.alunnoToDto(a) }.toList()
    }

    override fun getNumberSubscribersForMonth(month: String, year: String): SubscriptionDto {
        return corsoService!!.getNumberSubscribersForMonth(month, year)
    }

    override fun getNumberSubscribersForYear(year: String): SubscriptionDto {
        return corsoService!!.getNumberSubscribersForYear(year)
    }

}
