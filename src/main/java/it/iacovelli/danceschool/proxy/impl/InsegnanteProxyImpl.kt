package it.iacovelli.danceschool.proxy.impl

import it.iacovelli.danceschool.exception.InsegnanteNotFoundException
import it.iacovelli.danceschool.mapper.InsegnanteMapper
import it.iacovelli.danceschool.model.dto.InsegnanteDto
import it.iacovelli.danceschool.proxy.InsegnanteProxy
import it.iacovelli.danceschool.service.InsegnanteService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
open class InsegnanteProxyImpl(
    private var insegnanteService: InsegnanteService,
    private var insegnanteMapper: InsegnanteMapper
) : InsegnanteProxy {

    override fun addInsegnante(insegnanteDto: InsegnanteDto) {
        val insegnante = insegnanteMapper.dtoToInsegnante(insegnanteDto)
        insegnanteService.addInsegnante(insegnante)
    }

    @Throws(InsegnanteNotFoundException::class)
    override fun editInsegnante(insegnanteDto: InsegnanteDto) {
        val insegnante = insegnanteService.getInsegnanteByFiscalCode(insegnanteDto.fiscalCode)
        insegnanteMapper.updateInsegnanteFromDto(insegnanteDto, insegnante)
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
