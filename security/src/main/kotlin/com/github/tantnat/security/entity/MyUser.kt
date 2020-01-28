package com.github.tantnat.security.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class MyUser(
    @Id val id: Long,
    @Column val name: String,
    @Column val surname: String,
    @Column val email: String
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsername(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPassword(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}