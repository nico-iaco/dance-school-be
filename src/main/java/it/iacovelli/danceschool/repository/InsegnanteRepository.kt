package it.iacovelli.danceschool.repository

import it.iacovelli.danceschool.model.Insegnante
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InsegnanteRepository : JpaRepository<Insegnante, Long> {

    fun getAllByActiveTrue(): List<Insegnante>

    @Modifying
    @Query("UPDATE Insegnante i SET i.active = :state WHERE i.fiscalCode = :fiscalCode")
    fun setInsegnanteState(@Param("fiscalCode") fiscalCode: String, @Param("state") state: Boolean)

    fun findInsegnanteByFiscalCode(fiscalCode: String): Optional<Insegnante>

}
