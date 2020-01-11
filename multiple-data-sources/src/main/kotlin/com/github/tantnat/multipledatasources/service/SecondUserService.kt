package com.github.tantnat.multipledatasources.service

import com.github.tantnat.multipledatasources.first.repository.FirstUserRepository
import com.github.tantnat.multipledatasources.second.dto.SecondUserDto
import com.github.tantnat.multipledatasources.second.entity.SecondUser
import com.github.tantnat.multipledatasources.second.repository.NativeRepository
import com.github.tantnat.multipledatasources.second.repository.SecondUserRepository
import org.springframework.stereotype.Service

@Service
class SecondUserService(
    private val firstUserRepository: FirstUserRepository,
    private val seconUserRepository: SecondUserRepository,
    private val nativeRepository: NativeRepository
) {
    fun loadUsers(): List<SecondUserDto> {
        val firstUsers = firstUserRepository.findAll()
        val secondUsers = firstUsers.map { it.convertToSecondUser() }
        return seconUserRepository.saveAll(secondUsers).map { it.convertToSecondUserDto() }
    }

    fun findAll(): List<SecondUserDto> {
        return seconUserRepository.findAll().map { it.convertToSecondUserDto() }
    }

    fun count(): Int {
        return nativeRepository.count()
    }
}

private fun SecondUser.convertToSecondUserDto(): SecondUserDto {
    return SecondUserDto(
        firstName = this.firstName,
        secondName = this.secondName,
        date = this.date
    )
}
