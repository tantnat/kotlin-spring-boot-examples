package com.github.tantnat.batch.repository

import com.github.tantnat.batch.entity.SimpleJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SimpleJpaRepository : JpaRepository<SimpleJpaEntity, Long>