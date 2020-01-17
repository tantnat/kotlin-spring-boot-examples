package com.github.tantnat.batch.entity

import javax.persistence.*

@Entity
@Table
class SimpleJpaEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
        @Column val name: String,
        @Column val surname: String
)