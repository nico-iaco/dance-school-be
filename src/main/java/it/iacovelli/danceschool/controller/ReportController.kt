package it.iacovelli.danceschool.controller

import io.swagger.annotations.ApiOperation
import it.iacovelli.danceschool.helper.BaseResponse
import it.iacovelli.danceschool.model.dto.EarningDto
import it.iacovelli.danceschool.model.dto.SubscriptionDto
import it.iacovelli.danceschool.proxy.CorsoProxy
import it.iacovelli.danceschool.proxy.PagamentoProxy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDate
import java.time.Period

@RestController
@RequestMapping("/report")
class ReportController {

    @Autowired
    private lateinit var pagamentoProxy: PagamentoProxy

    @Autowired
    private lateinit var corsoProxy: CorsoProxy

    @GetMapping("/earning/daily/{year}/{month}/{day}")
    @ApiOperation(value = "Get the earning of a day")
    fun dailyEarnings(@PathVariable("year") year: Int, @PathVariable("month") month: Int, @PathVariable("day") day: Int): BaseResponse<EarningDto> {
        val response = BaseResponse<EarningDto>()
        val d = LocalDate.of(year, month, day)
        val dailyEarnings = pagamentoProxy!!.getDailyEarnings(d)
        response.body = dailyEarnings
        return response
    }

    @GetMapping("/earning/period/{start}/{end}")
    @ApiOperation(value = "Get the earning of a period")
    fun periodEarnings(@PathVariable("start") start: String, @PathVariable("end") end: String): BaseResponse<EarningDto> {
        val response = BaseResponse<EarningDto>()
        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)
        val periodEarnings = pagamentoProxy!!.getPeriodEarnings(startDate, endDate)
        periodEarnings.period = Period.between(startDate, endDate)
        response.body = periodEarnings
        return response
    }

    @GetMapping("/subscription/month/{month}/year/{year}")
    @ApiOperation(value = "Get the subscriptions of a month")
    fun getSubscriptionForMonth(@PathVariable("month") month: String, @PathVariable("year") year: String): BaseResponse<SubscriptionDto> {
        val response = BaseResponse<SubscriptionDto>()
        val numberSubscribersForMonth = corsoProxy!!.getNumberSubscribersForMonth(month, year)
        response.body = numberSubscribersForMonth
        return response
    }

    @GetMapping("/subscription/year/{year}")
    @ApiOperation(value = "Get the subscriptions of the year")
    fun getSubscriptionForYear(@PathVariable("year") year: String): BaseResponse<SubscriptionDto> {
        val response = BaseResponse<SubscriptionDto>()
        val numberSubscribersForYear = corsoProxy!!.getNumberSubscribersForYear(year)
        response.body = numberSubscribersForYear
        return response
    }

}
