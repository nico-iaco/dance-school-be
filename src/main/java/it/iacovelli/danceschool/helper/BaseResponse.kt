package it.iacovelli.danceschool.helper


import org.springframework.http.HttpStatus
import java.util.*

class BaseResponse<T> {
    var body: T? = null
    var messages: MutableList<Message> = ArrayList()

    fun addMessage(code: HttpStatus, text: String?, messageType: MessageType): BaseResponse<T> {
        var message = Message()
        message.code = code.toString()
        message.text = text
        message.messageType = messageType
        this.messages.add(message)
        return this
    }

    fun addMessage(message: Message): BaseResponse<T> {
        this.messages.add(message)
        return this
    }

    fun addErrorMessage(code: HttpStatus, text: String?): BaseResponse<T> {
        addMessage(code, text, MessageType.Error)
        return this
    }
}
