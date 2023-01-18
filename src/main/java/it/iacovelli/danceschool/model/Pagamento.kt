package it.iacovelli.danceschool.model

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

/**
 * This is the Pagamento model
 * @version 1.1
 * @author nicola.iacovelli
 */
@Entity
class Pagamento : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "amount")
    var amount: Double = 0.toDouble()

    @Column(name = "payment_date", columnDefinition = "DATE")
    var paymentDate: LocalDate? = null

    @Column(name = "related_course", length = 100)
    var relatedCourse: String? = null

    @ManyToOne
    @JoinColumn(name = "student")
    var student: Alunno? = null

    override fun toString(): String {
        return "Pagamento(id=$id, amount=$amount, paymentDate=$paymentDate, relatedCourse=$relatedCourse, student=$student)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Pagamento) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
