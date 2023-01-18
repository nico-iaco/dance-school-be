package it.iacovelli.danceschool.repository

import it.iacovelli.danceschool.model.Corso
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CorsoRepository : JpaRepository<Corso, Long> {

    fun getAllByActiveTrue() : List<Corso>

    @Query("select c from Corso c where c.id = :id")
    fun getCorsoById(@Param("id") id: Long): Optional<Corso>

    @Modifying
    @Query("update Corso c set c.active = :state where c.id = :courseId")
    fun setCourseState(@Param("state") state: Boolean, @Param("courseId") courseId: Long)
}
