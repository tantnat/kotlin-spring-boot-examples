package com.github.tantnat.security.repository

import com.github.tantnat.security.entity.MyUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface UserRepository : JpaRepository<MyUser, Long>, QuerydslPredicateExecutor<MyUser>