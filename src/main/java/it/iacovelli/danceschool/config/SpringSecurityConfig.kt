package it.iacovelli.danceschool.config

import it.iacovelli.danceschool.helper.AuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
open class SpringSecurityConfig(private val authEntryPoint: AuthenticationEntryPoint)  {

    @Throws(Exception::class)
    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain  {
        http.csrf().disable().authorizeHttpRequests()
            .requestMatchers("/student/**").authenticated()
            .requestMatchers("/course/**").authenticated()
            .requestMatchers("/report/**").authenticated()
            .requestMatchers("/security/**").authenticated()
            .requestMatchers("/roles/**").hasRole("ADMIN")
            .anyRequest().permitAll()
            .and().httpBasic()
            .authenticationEntryPoint(authEntryPoint)
        return http.build()
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication().withUser("prova").password("$2a$10\$udFjoQSbL3fI8AR9px4hgONWUbDBbs.W81.h6lPaCv9gEeS0DKRby").roles("USER")
        auth.inMemoryAuthentication().withUser("admin").password("$2a$10\$gW1TU6s4woc1RRn6k3TKleOJGT3NdwczhgizZtuKsdQvRzx9TPERy").roles("ADMIN")
    }


    @Bean
    open fun encoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }


}
