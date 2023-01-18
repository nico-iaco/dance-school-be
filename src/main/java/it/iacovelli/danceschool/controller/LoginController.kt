package it.iacovelli.danceschool.controller

import io.swagger.annotations.ApiOperation
import it.iacovelli.danceschool.helper.BaseResponse
import it.iacovelli.danceschool.helper.MessageType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/security")
class LoginController {

    @GetMapping("/login")
    @ApiOperation(value = "Login API request")
    fun login(): BaseResponse<Void> {
        return BaseResponse<Void>().addMessage(HttpStatus.OK, "Sei loggato", MessageType.Info)
    }

    @GetMapping("/logout")
    @ApiOperation(value = "Logout API request")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): BaseResponse<Void> {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth != null) {
            SecurityContextLogoutHandler().logout(request, response, auth)
        }
        return BaseResponse<Void>().addMessage(HttpStatus.OK, "Sei disconesso", MessageType.Info)
    }

}
