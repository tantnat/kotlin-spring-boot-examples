package com.github.tantnat.multipledatasources.first.repository

import com.github.tantnat.multipledatasources.first.entity.FirstUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FirstUserRepository : JpaRepository<FirstUser, Long>