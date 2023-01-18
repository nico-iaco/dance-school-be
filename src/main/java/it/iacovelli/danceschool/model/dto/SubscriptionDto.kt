package it.iacovelli.danceschool.model.dto

import io.swagger.annotations.ApiModelProperty

/**
 * This Dto represent the subscription of a period
 * @version 1.0
 * @author nicola.iacovelli
 */
class SubscriptionDto {

    @ApiModelProperty(notes = "The number of new subscription")
    var subscriptions: Long? = 0

    constructor()

    constructor(subscriptions : Long) {
        this.subscriptions = subscriptions
    }

    override fun toString(): String {
        return "SubscriptionDto(subscriptions=$subscriptions)"
    }

}
