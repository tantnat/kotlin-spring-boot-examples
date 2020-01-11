package com.github.tantnat.multipledatasources.second.repository

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class NativeRepositoryImpl(
    @Qualifier("secondEntityManager") private val entityManager: EntityManager
) : NativeRepository {

    override fun count(): Int {
        return entityManager.createNativeQuery("select count(*) from second.user").singleResult.toString().toInt()
    }
}