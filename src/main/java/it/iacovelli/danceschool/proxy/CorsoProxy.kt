package it.iacovelli.danceschool.proxy

import it.iacovelli.danceschool.exception.CorsoNotFoundException
import it.iacovelli.danceschool.model.dto.AlunnoDto
import it.iacovelli.danceschool.model.dto.CorsoDto
import it.iacovelli.danceschool.model.dto.SubscriptionDto

interface CorsoProxy {

    fun getAllActiveCourse(): List<CorsoDto>

    fun addCourse(corsoDto: CorsoDto, fiscalCode: String): Long

    @Throws(CorsoNotFoundException::class)
    fun editCourse(corsoDto: CorsoDto)

    @Throws(CorsoNotFoundException::class)
    fun getCorsoById(id: Long): CorsoDto

    @Throws(CorsoNotFoundException::class)
    fun setCorsoActivated(state: Boolean, courseId: Long)

    @Throws(CorsoNotFoundException::class)
    fun getStudentsOfCourse(id: Long): List<AlunnoDto>

    fun getNumberSubscribersForMonth(month: String, year: String): SubscriptionDto

    fun getNumberSubscribersForYear(year: String): SubscriptionDto

}
