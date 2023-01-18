package it.iacovelli.danceschool.helper

import org.springframework.http.HttpStatus

class Message {

    var code: String? = null

    var text: String? = null

    var messageType: MessageType? = null

    constructor() {

    }

    constructor(text: String) {
        this.text = text
    }



    override fun toString(): String {
        return "Message{" +
                "code='" + code + '\''.toString() +
                ", text='" + text + '\''.toString() +
                ", messageType=" + messageType +
                '}'.toString()
    }

    companion object {


        fun getBadRequestMessage(text: String): Message {
            val message = Message()
            message.code = HttpStatus.BAD_REQUEST.toString()
            message.text = text
            message.messageType = MessageType.Error
            return message
        }

        fun getNotFoundMessage(text: String): Message {
            val message = Message()
            message.code = HttpStatus.NOT_FOUND.toString()
            message.text = text
            message.messageType = MessageType.Error
            return message
        }

        fun getSystemErrorMessage(text: String): Message {
            val message = Message()
            message.code = HttpStatus.INTERNAL_SERVER_ERROR.toString()
            message.text = text
            message.messageType = MessageType.Error
            return message
        }
    }
}
