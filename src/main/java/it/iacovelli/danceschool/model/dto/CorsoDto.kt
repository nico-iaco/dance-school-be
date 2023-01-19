package it.iacovelli.danceschool.model.dto

import io.swagger.annotations.ApiModelProperty
import it.iacovelli.danceschool.model.Corso
import java.io.Serializable

/**
 * This is the Dto of [Corso]
 * @see Corso
 *
 * @version 1.4
 * @author nicola.iacovelli
 */
class CorsoDto : Serializable {

    @ApiModelProperty(notes = "The database generated course ID")
    var id: Long = 0

    @ApiModelProperty(notes = "The course name")
    lateinit var name: String

    @ApiModelProperty(notes = "The course description")
    var description: String? = null

    @ApiModelProperty(notes = "The number of student enrolled")
    var subscribedStudent: Int = 0

    var teacher: InsegnanteDto? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CorsoDto) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "CorsoDto(id=$id, name=$name, description=$description, subscribedStudent=$subscribedStudent)"
    }


}
