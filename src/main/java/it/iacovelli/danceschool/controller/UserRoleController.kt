package it.iacovelli.danceschool.controller

import com.google.firebase.auth.FirebaseAuth
import io.swagger.annotations.ApiOperation
import it.iacovelli.danceschool.helper.BaseResponse
import it.iacovelli.danceschool.helper.Message
import it.iacovelli.danceschool.helper.MessageType
import it.iacovelli.danceschool.model.type.UserType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.ExecutionException

@RestController
@RequestMapping("/roles")
class UserRoleController {

    private fun setRole(userId: String, role: UserType): Message {
        var result: Message
        val auth = FirebaseAuth.getInstance()
        try {
            val claims = HashMap<String, Any>()
            claims["role"] = role.toString()
            auth.setCustomUserClaimsAsync(userId, claims).get()
            val message = Message("Il ruolo è stato aggiornato")
            message.code = HttpStatus.OK.toString()
            message.messageType = MessageType.Info
            result = message
        } catch (e: InterruptedException) {
            result = Message.getSystemErrorMessage(text = e.message!!)
        } catch (e: ExecutionException) {
            result = Message.getSystemErrorMessage(e.message!!)
        }

        return result
    }

    @PostMapping("/student/{userId}")
    @ApiOperation(value = "Set a firebase user to USER role")
    fun setUserRole(@PathVariable("userId") userId: String): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        response.addMessage(setRole(userId, UserType.STUDENT))
        return response
    }

    @PostMapping("/owner/{userId}")
    @ApiOperation(value = "Set a firebase user to OWNER role")
    fun setOwnerRole(@PathVariable("userId") userId: String): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        response.addMessage(setRole(userId, UserType.OWNER))
        return response
    }

    @PostMapping("/secretary/{userId}")
    @ApiOperation(value = "Set a firebase user to SECRETARY role")
    fun setSecretaryRole(@PathVariable("userId") userId: String): BaseResponse<Void> {
        val response = BaseResponse<Void>()
        response.addMessage(setRole(userId, UserType.SECRETARY))
        return response
    }

}
