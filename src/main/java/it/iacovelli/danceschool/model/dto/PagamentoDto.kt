package it.iacovelli.danceschool.model.dto

import io.swagger.annotations.ApiModelProperty
import it.iacovelli.danceschool.model.Pagamento
import java.io.Serializable
import java.time.LocalDate

/**
 * This is the Dto of [Pagamento]
 * @see Pagamento
 *
 * @version 1.0
 * @author nicola.iacovelli
 */
class PagamentoDto : Serializable {

    @ApiModelProperty(notes = "The database generated payment ID")
    var id: Long? = null

    @ApiModelProperty(notes = "The amount of the payment")
    var amount: Double = 0.0

    @ApiModelProperty(notes = "When the payment was done")
    var paymentDate: LocalDate = LocalDate.now()

    @ApiModelProperty(notes = "The reason of the payment")
    var relatedCourse: String = ""

    @ApiModelProperty(notes = "The ID of the student which made the payment")
    var studentId: String = ""

    constructor(id: Long?, amount: Double, paymentDate: LocalDate, relatedCourse: String, studentId: String) {
        this.id = id
        this.amount = amount
        this.paymentDate = paymentDate
        this.relatedCourse = relatedCourse
        this.studentId = studentId
    }

    constructor(amount: Double) {
        this.amount = amount
    }

    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PagamentoDto) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "PagamentoDto(id=$id, amount=$amount, paymentDate=$paymentDate, relatedCourse='$relatedCourse', studentId=$studentId)"
    }


}
