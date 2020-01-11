package com.github.tantnat.multipledatasources.second.entity

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USER", schema = "SECOND")
class SecondUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val firstName: String,
    val secondName: String,
    val date: LocalDate
)