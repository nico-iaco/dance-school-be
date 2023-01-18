package it.iacovelli.danceschool.proxy

import it.iacovelli.danceschool.model.dto.EarningDto

import java.time.LocalDate

interface PagamentoProxy {

    fun getDailyEarnings(date: LocalDate): EarningDto

    fun getPeriodEarnings(startDate: LocalDate, endDate: LocalDate): EarningDto

}
