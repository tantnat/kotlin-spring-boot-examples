package com.github.tantnat.batch.controller

import com.github.tantnat.batch.entity.SimpleJpaEntity
import com.github.tantnat.batch.repository.SimpleJpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/generate")
class GenerateController(private val simpleJpaRepository: SimpleJpaRepository) {

    @GetMapping("/jpa")
    fun generateJpa() {
           for (i in 0..10) {
               val entity = SimpleJpaEntity(id = 0, name = "jpa_name_$i", surname = "jpa_surname_$i")
               simpleJpaRepository.save(entity)
           }
    }

}