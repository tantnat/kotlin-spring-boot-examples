package com.github.tantnat.security.config

import com.github.tantnat.security.entity.MyUser
import com.github.tantnat.security.entity.QMyUser
import com.github.tantnat.security.repository.UserRepository
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsChecker
import org.springframework.web.filter.GenericFilterBean
import java.time.Instant
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

class AuthCookieFilter(
    private val userRepository: UserRepository
) : GenericFilterBean() {

    private val userDetailsChecker: UserDetailsChecker = AccountStatusUserDetailsChecker()

    override fun doFilter(
        servletRequest: ServletRequest?,
        servletResponse: ServletResponse?,
        filterChain: FilterChain?
    ) {
        val httpServletRequest = servletRequest as HttpServletRequest

        val cookies: Array<Cookie>? = httpServletRequest.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name.equals(MY_COOKIE_NAME)) {
                    val cookieValue: String = cookie.value
                    val arguments = cookieValue.split(":")
                    val expiresAtEpochSeconds = arguments.last().toLong()
                    if (arguments.isNotEmpty()) {
                        if (Instant.now().epochSecond < expiresAtEpochSeconds) {
                            val entity: MyUser? =
                                userRepository.findOne(QMyUser.myUser.id.eq(arguments.first().toLong()))
                                    .orElseGet(null)
                            if (entity != null) {
                                userDetailsChecker.check(entity)
                                SecurityContextHolder.getContext().authentication =
                                    UsernamePasswordAuthenticationToken(
                                        entity, null,
                                        entity.authorities
                                    )
                            }
                        }
                    }
                }
            }
        }
        filterChain?.doFilter(servletRequest, servletResponse)
    }
}