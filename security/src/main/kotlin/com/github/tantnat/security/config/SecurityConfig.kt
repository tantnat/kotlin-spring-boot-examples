package com.github.tantnat.security.config

import com.github.tantnat.security.entity.MyUser
import com.github.tantnat.security.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import java.time.Duration
import java.time.Instant
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val userRepository: UserRepository
) : WebSecurityConfigurerAdapter() {

    private val authCookieFilter = AuthCookieFilter(userRepository)

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity?) {
        requireNotNull(http)


        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .formLogin()
            .loginPage("/api/auth/login")
            .successHandler(formLoginSuccessHandler())
            .failureHandler { httpServletRequest, httpServletResponse, authenticationException -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED") }
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/api/auth/logout")
            .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler())
            .deleteCookies(MY_COOKIE_NAME)
            .permitAll()
            .and()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .addFilterBefore(authCookieFilter, UsernamePasswordAuthenticationFilter::class.java)

    }

    private fun formLoginSuccessHandler(): AuthenticationSuccessHandler {
        return AuthenticationSuccessHandler { httpServletRequest, httpServletResponse, authentication ->
            run {
                val userDetails = authentication.principal as MyUser
                val headerValues = mutableListOf<String>()

                val time = Instant.now().plus(Duration.ofHours(9)).epochSecond
                val cookieValue = "${userDetails.id}:${userDetails.authorities.first()}:$time"
                headerValues.add("$MY_COOKIE_NAME=$cookieValue")
                headerValues.add("SameSite=Strict")
                headerValues.add("Path=/")
                headerValues.add("HttpOnly")
                httpServletResponse.addHeader("Set-Cookie", headerValues.joinToString(separator = ";"))
                httpServletResponse.writer.print(
                    SecurityContextHolder.getContext().authentication.authorities.iterator().next().authority
                )
            }
        }
    }
}