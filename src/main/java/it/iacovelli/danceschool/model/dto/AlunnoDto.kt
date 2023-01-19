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
    var fiscalCode: String = ""

    @ApiModelProperty(notes = "The student name")
    var name: String = ""

    @ApiModelProperty(notes = "The student surname")
    var surname: String = ""

    @ApiModelProperty(notes = "Where the student were born")
    var birthdayPlace: String = ""

    @ApiModelProperty(notes = "The fiscal code of the parent")
    var parentFiscalCode: String = ""

    @ApiModelProperty(notes = "The city where the student lives in")
    var city: String = ""

    @ApiModelProperty(notes = "The address of the student")
    var address: String = ""

    @ApiModelProperty(notes = "The gender of the student")
    var gender: GenderType = GenderType.NOT_BINARY

    @ApiModelProperty(notes = "The CAP of the student's city")
    var cap: String = ""

    @ApiModelProperty(notes = "When the student were born")
    var birthday: LocalDate = LocalDate.now()

    @ApiModelProperty(notes = "The telephone number of the parent or the student")
    var telephone: String = ""

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
        result = 31 * result + (fiscalCode.hashCode())
        return result
    }

    override fun toString(): String {
        return "AlunnoDto(fiscalCode=$fiscalCode, name=$name, surname=$surname, birthdayPlace=$birthdayPlace,  parentFiscalCode=$parentFiscalCode, city=$city, address=$address, gender=$gender, cap=$cap, birthday=$birthday, telephone=$telephone, coursesNumber=$coursesNumber)"
    }


}
