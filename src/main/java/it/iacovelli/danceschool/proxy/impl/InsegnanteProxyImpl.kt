package it.iacovelli.danceschool.proxy.impl

import it.iacovelli.danceschool.exception.InsegnanteNotFoundException
import it.iacovelli.danceschool.mapper.InsegnanteMapper
import it.iacovelli.danceschool.model.dto.InsegnanteDto
import it.iacovelli.danceschool.proxy.InsegnanteProxy
import it.iacovelli.danceschool.service.InsegnanteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import kotlin.streams.toList

@Component
open class InsegnanteProxyImpl : InsegnanteProxy {

    @Autowired
    private lateinit var insegnanteService: InsegnanteService

    @Autowired
    private lateinit var insegnanteMapper: InsegnanteMapper

    override fun addInsegnante(insegnanteDto: InsegnanteDto) {
        val insegnante = insegnanteMapper.dtoToInsegnante(insegnanteDto)
        insegnanteService.addInsegnante(insegnante)
    }

    @Throws(InsegnanteNotFoundException::class)
    override fun editInsegnante(insegnanteDto: InsegnanteDto) {
        val insegnante = insegnanteMapper.dtoToInsegnante(insegnanteDto)
        insegnanteService.editInsegnante(insegnante)
    }

    @Throws(InsegnanteNotFoundException::class)
    override fun getInsegnanteByFiscalCode(fiscalCode: String): InsegnanteDto {
        val teacher = insegnanteService.getInsegnanteByFiscalCode(fiscalCode)
        return insegnanteMapper.insegnanteToDto(teacher)
    }

    @Cacheable(cacheNames = ["insegnanti"])
    override fun getAllActiveTeacher(): List<InsegnanteDto> {
        return insegnanteService.allActiveTeacher().stream().map { i -> insegnanteMapper.insegnanteToDto(i)}.toList()
    }

}
