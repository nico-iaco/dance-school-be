package it.iacovelli.danceschool.repository

import it.iacovelli.danceschool.model.Pagamento
import it.iacovelli.danceschool.model.dto.EarningDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface PagamentoRepository : JpaRepository<Pagamento, Long> {

    @Query("select new it.iacovelli.danceschool.model.dto.EarningDto(sum(p.amount)) from Pagamento p where p.paymentDate = :date")
    fun getDailyEarnings(@Param("date") date: LocalDate): EarningDto

    @Query("select new it.iacovelli.danceschool.model.dto.EarningDto(sum(p.amount), :startDate, :endDate) from Pagamento p where p.paymentDate between :startDate AND :endDate")
    fun getPeriodEarnings(@Param("startDate") startDate: LocalDate, @Param("endDate") endDate: LocalDate): EarningDto

}
