package com.github.tantnat.multipledatasources.first.entity

import com.github.tantnat.multipledatasources.second.entity.SecondUser
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USER", schema = "FIRST")
class FirstUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val firstName: String,
    val secondName: String
) {
    fun convertToSecondUser(): SecondUser {
        return SecondUser(
            id = 0,
            firstName = this.firstName,
            secondName = this.secondName,
            date = LocalDate.now()
        )
    }
}