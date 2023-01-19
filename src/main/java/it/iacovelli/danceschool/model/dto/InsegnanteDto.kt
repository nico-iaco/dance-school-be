package it.iacovelli.danceschool.model.dto

import it.iacovelli.danceschool.model.Insegnante
import it.iacovelli.danceschool.model.type.GenderType
import java.io.Serializable
import java.time.LocalDate

/**
 *  This is the dto of [Insegnante]
 *  @see Insegnante
 *
 *  @version 1.3
 *  @author nicola.iacovelli
 */
class InsegnanteDto : Serializable {

    var active: Boolean = true

    lateinit var name: String

    lateinit var surname: String

    lateinit var birthdayPlace: String

    lateinit var fiscalCode: String

    var gender: GenderType = GenderType.NOT_BINARY

    var birthday: LocalDate = LocalDate.now()

    lateinit var telephone: String

    var salary: Double = 0.0

    var taughtCourses: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InsegnanteDto) return false

        if (fiscalCode != other.fiscalCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fiscalCode.hashCode()
        result = 31 * result + (fiscalCode.hashCode())
        return result
    }

    override fun toString(): String {
        return "InsegnanteDto(fiscalCode=$fiscalCode, active=$active, name=$name, surname=$surname, birthdayPlace=$birthdayPlace, gender=$gender, birthday=$birthday, telephone=$telephone, salary=$salary, taughtCourses=$taughtCourses)"
    }


}
