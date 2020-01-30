package com.github.tantnat.batch.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table
class SimpleJpaProcessedEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
        @Column val name: String,
        @Column val surname: String,
        @Column val date: LocalDate
)