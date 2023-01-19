package it.iacovelli.danceschool

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.IOException
import java.net.URL
import javax.annotation.PostConstruct

@SpringBootApplication
open class DanceSchoolApplication {

    @Value("\${firebase.service-account.url}")
    lateinit var serviceAccountUrl: String

    @Value("\${firebase.database.url}")
    lateinit var databaseUrl: String

    @PostConstruct
    @Throws(IOException::class)
    private fun initializeFirebase() {
        val serviceAccount = URL(serviceAccountUrl).openStream()
        val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl)
                .build()
        FirebaseApp.initializeApp(options)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(DanceSchoolApplication::class.java, *args)
        }
    }

}
