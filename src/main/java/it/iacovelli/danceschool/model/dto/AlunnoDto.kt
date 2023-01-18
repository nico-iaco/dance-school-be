package it.iacovelli.danceschool.model.dto

import io.swagger.annotations.ApiModelProperty
import it.iacovelli.danceschool.model.Alunno
import it.iacovelli.danceschool.model.type.GenderType
import java.io.Serializable
import java.time.LocalDate

/**
 * This is the Dto of [Alunno]
 * @see Alunno
 *
 * @version 1.1
 * @author nicola.iacovelli
 */
class AlunnoDto : Serializable {

    @ApiModelProperty(notes = "The fiscal code of the student")
    lateinit var fiscalCode: String

    @ApiModelProperty(notes = "The student name")
    var name: String? = null

    @ApiModelProperty(notes = "The student surname")
    var surname: String? = null

    @ApiModelProperty(notes = "Where the student were born")
    var birthdayPlace: String? = null

    @ApiModelProperty(notes = "The fiscal code of the parent")
    var parentFiscalCode: String? = null

    @ApiModelProperty(notes = "The city where the student lives in")
    var city: String? = null

    @ApiModelProperty(notes = "The address of the student")
    var address: String? = null

    @ApiModelProperty(notes = "The gender of the student")
    var gender: GenderType? = null

    @ApiModelProperty(notes = "The CAP of the student's city")
    var cap: String? = null

    @ApiModelProperty(notes = "When the student were born")
    var birthday: LocalDate? = LocalDate.now()

    @ApiModelProperty(notes = "The telephone number of the parent or the student")
    var telephone: String? = null

    @ApiModelProperty(notes = "The number of courses enrolled in")
    var coursesNumber: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AlunnoDto) return false

        if (fiscalCode != other.fiscalCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fiscalCode.hashCode()
        result = 31 * result + (fiscalCode.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AlunnoDto(fiscalCode=$fiscalCode, name=$name, surname=$surname, birthdayPlace=$birthdayPlace,  parentFiscalCode=$parentFiscalCode, city=$city, address=$address, gender=$gender, cap=$cap, birthday=$birthday, telephone=$telephone, coursesNumber=$coursesNumber)"
    }


}
