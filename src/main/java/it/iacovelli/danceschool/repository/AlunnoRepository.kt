package it.iacovelli.danceschool.repository

import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.Pagamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AlunnoRepository : JpaRepository<Alunno, Long> {

    fun getAllByActiveTrue() : List<Alunno>

    fun getAlunnoByFiscalCode(fiscalCode: String): Optional<Alunno>

    @Query("select a.payments from Alunno a where a.fiscalCode = :fiscalCode")
    fun getStudentPayments(fiscalCode: String): List<Pagamento>

    @Modifying
    @Query("UPDATE Alunno a set a.active = :state where a.fiscalCode = :fiscalCode")
    fun setAlunnoState(@Param("state") state: Boolean, @Param("fiscalCode") fiscalCode: String)

}
