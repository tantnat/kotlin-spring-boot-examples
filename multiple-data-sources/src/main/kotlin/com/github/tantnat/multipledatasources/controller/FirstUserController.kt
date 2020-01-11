package com.github.tantnat.multipledatasources.controller

import com.github.tantnat.multipledatasources.first.dto.FirstUserDto
import com.github.tantnat.multipledatasources.first.entity.FirstUser
import com.github.tantnat.multipledatasources.first.repository.FirstUserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/first")
class FirstUserController(private val firstUserRepository: FirstUserRepository) {
    @PostMapping("/generate")
    fun generate() {
        val users = arrayListOf<FirstUser>().apply {
            for (i in 0..10) {
                this.add(
                    FirstUser(
                        id = 0,
                        firstName = "Generated_FirstName_$i",
                        secondName = "Generated_SecondName_$i"
                    )
                )
            }
        }
        firstUserRepository.saveAll(users)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<FirstUserDto>> {
        return ResponseEntity.ok(firstUserRepository.findAll().map { it.convertToFirstUserDto() })
    }
}

private fun FirstUser.convertToFirstUserDto(): FirstUserDto {
    return FirstUserDto(
        firstName = this.firstName,
        secondName = this.secondName
    )
}
