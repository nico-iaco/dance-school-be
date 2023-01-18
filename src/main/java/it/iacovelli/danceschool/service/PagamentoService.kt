package it.iacovelli.danceschool.service

import it.iacovelli.danceschool.model.dto.EarningDto

import java.time.LocalDate

interface PagamentoService {

    fun getDailyEarnings(date: LocalDate): EarningDto

    fun getPeriodEarnings(startDate: LocalDate, endDate: LocalDate): EarningDto

}
