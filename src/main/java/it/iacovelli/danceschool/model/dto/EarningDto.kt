package it.iacovelli.danceschool.model.dto

import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.time.Period

/**
 * This is the Dto that represent the earnings of a period.
 * @version 1.1
 * @author nicola.iacovelli
 */
class EarningDto {

    @ApiModelProperty(notes = "The earnings returned")
    var earnings: Double = 0.0

    @ApiModelProperty(notes = "The period of the request")
    var period: Period? = null

    constructor(earnings: Double?) {
        this.earnings = earnings ?: 0.0
        this.period = Period.ofDays(1)
    }

    constructor(earnings: Double?, start: LocalDate, end: LocalDate) {
        this.earnings = earnings ?: 0.0
        this.period = Period.between(start, end)
    }

    override fun toString(): String {
        return "EarningDto(earnings=$earnings, period=$period)"
    }


}
