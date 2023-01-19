package it.iacovelli.danceschool.service

import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Corso
import it.iacovelli.danceschool.model.dto.SubscriptionDto

interface CorsoService {

    fun allActiveCourse(): List<Corso>

    fun addCourse(corso: Corso, fiscalCode: String): Long

    fun editCorso(corso: Corso)

    @Throws(CorsoNotFoundException::class)
    fun getCorsoById(id: Long): Corso

    @Throws(CorsoNotFoundException::class)
    fun setCorsoActivated(state: Boolean, courseId: Long)

    @Throws(CorsoNotFoundException::class)
    fun getStudentsOfCourse(id: Long): List<Alunno>

    fun getNumberSubscribersForMonth(month: String, year: String): SubscriptionDto

    fun getNumberSubscribersForYear(year: String): SubscriptionDto

}
