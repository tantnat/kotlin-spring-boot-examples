package com.github.tantnat.multipledatasources.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.github.tantnat.multipledatasources.first"],
    entityManagerFactoryRef = "firstEntityManager",
    transactionManagerRef = "firstTransactionManager"
)
@EnableTransactionManagement
class FirstDatabaseConfig(private val firstDatabaseProperties: FirstDatabaseProperties) {

    @Primary
    @Bean(name = ["firstDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource")
    fun firstDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Primary
    @Bean(name = ["firstEntityManager"])
    fun firstEntityManager(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(firstDataSource())
            .packages("com.github.tantnat.multipledatasources.first.entity")
            .persistenceUnit("first")
            .properties(
                mapOf(
                    "hibernate.dialect" to firstDatabaseProperties.hibernateDialect
                )
            )
            .build()
    }

    @Primary
    @Bean
    fun firstTransactionManager(@Qualifier("firstEntityManager") entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory
        return transactionManager
    }

}