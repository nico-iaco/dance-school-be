package it.iacovelli.danceschool.proxy.impl

import it.iacovelli.danceschool.model.dto.EarningDto
import it.iacovelli.danceschool.proxy.PagamentoProxy
import it.iacovelli.danceschool.service.PagamentoService
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
class PagamentoProxyImpl(private var pagamentoService: PagamentoService) : PagamentoProxy {

    override fun getDailyEarnings(date: LocalDate): EarningDto {
        return pagamentoService.getDailyEarnings(date)
    }

    override fun getPeriodEarnings(startDate: LocalDate, endDate: LocalDate): EarningDto {
        return pagamentoService.getPeriodEarnings(startDate, endDate)
    }
}
