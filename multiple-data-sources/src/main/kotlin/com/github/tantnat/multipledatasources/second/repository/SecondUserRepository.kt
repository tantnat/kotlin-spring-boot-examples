package com.github.tantnat.multipledatasources.second.repository

import com.github.tantnat.multipledatasources.second.entity.SecondUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SecondUserRepository : JpaRepository<SecondUser, Long>