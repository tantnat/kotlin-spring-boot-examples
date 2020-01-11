package com.github.tantnat.multipledatasources.controller

import com.github.tantnat.multipledatasources.second.dto.SecondUserDto
import com.github.tantnat.multipledatasources.service.SecondUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/second")
class SecondUserController(private val secondUserService: SecondUserService) {

    @PostMapping("/load")
    fun loadUsersFromFirstToSecond(): ResponseEntity<List<SecondUserDto>> {
        return ResponseEntity.ok(secondUserService.loadUsers())
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<SecondUserDto>> {
        return ResponseEntity.ok(secondUserService.findAll())
    }

    @GetMapping("/count")
    fun count(): ResponseEntity<Int> {
        return ResponseEntity.ok(secondUserService.count())
    }
}