package it.iacovelli.danceschool.service.impl

import it.iacovelli.danceschool.model.Pagamento
import it.iacovelli.danceschool.model.dto.EarningDto
import it.iacovelli.danceschool.repository.PagamentoRepository
import it.iacovelli.danceschool.service.PagamentoService
import org.springframework.stereotype.Service

import java.time.LocalDate

/**
 * This is the service class which exposes the payment services
 * @version 1.0
 * @author nicola.iacovelli
 */
@Service
open class PagamentoServiceImpl(
    /**
     * This is the repository to access to [Pagamento] entity
     */
    private var pagamentoRepository: PagamentoRepository
) : PagamentoService {

    /**
     * This method is used to get the daily income
     * @param date represent which day you want to now
     * @return the [EarningDto] which represent the earnings of the day
     */
    override fun getDailyEarnings(date: LocalDate): EarningDto {
        return pagamentoRepository.getDailyEarnings(date)
    }

    /**
     * This method is used to get the earnings of a period
     * @param startDate represent from which date you want to know the earnings
     * @param endDate represent until which date you want to know the earnings
     * @return the [EarningDto] which represent the earnings of the period
     */
    override fun getPeriodEarnings(startDate: LocalDate, endDate: LocalDate): EarningDto {
        return pagamentoRepository.getPeriodEarnings(startDate, endDate)
    }

}
