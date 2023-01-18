package it.iacovelli.danceschool.repository

import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Corso
import it.iacovelli.danceschool.model.Iscrizione
import it.iacovelli.danceschool.model.dto.SubscriptionDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface IscrizioneRepository : JpaRepository<Iscrizione, Long> {

    fun findAllByCourse(@Param("course") course: Corso): List<Iscrizione>

    fun findAllByStudent(@Param("student") student: Alunno): List<Iscrizione>

    @Query("select new it.iacovelli.danceschool.model.dto.SubscriptionDto(count(i)) from Iscrizione i where i.subscribeDate LIKE CONCAT(:year, '-', :month, '-%') ")
    fun getNumberSubscriptionForMonth(@Param("month") month: String, @Param("year") year: String): SubscriptionDto

    @Query("select new it.iacovelli.danceschool.model.dto.SubscriptionDto(count(i)) from Iscrizione i where i.subscribeDate like concat(:year, '-%') ")
    fun getNumberSubscriptionForYear(@Param("year") year: String): SubscriptionDto
}
