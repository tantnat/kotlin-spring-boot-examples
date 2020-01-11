package com.github.tantnat.multipledatasources.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.github.tantnat.multipledatasources.second"],
    entityManagerFactoryRef = "secondEntityManager",
    transactionManagerRef = "secondTransactionManager"
)
@EnableTransactionManagement
class SecondDatabaseConfig(private val secondDatabaseProperties: SecondDatabaseProperties) {

    @Bean(name = ["secondDataSource"])
    @ConfigurationProperties(prefix = "spring.second-datasource")
    fun secondDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean(name = ["secondEntityManager"])
    fun secondEntityManager(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(secondDataSource())
            .packages("com.github.tantnat.multipledatasources.second.entity")
            .persistenceUnit("second")
            .properties(
                mapOf(
                    "hibernate.dialect" to secondDatabaseProperties.hibernateDialect
                )
            )
            .build()
    }

    @Bean
    fun secondTransactionManager(
        @Qualifier("secondEntityManager") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory
        return transactionManager
    }
}