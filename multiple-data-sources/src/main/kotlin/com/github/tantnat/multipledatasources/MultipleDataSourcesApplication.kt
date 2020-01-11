package com.github.tantnat.multipledatasources

import com.github.tantnat.multipledatasources.configuration.FirstDatabaseProperties
import com.github.tantnat.multipledatasources.configuration.SecondDatabaseProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(
    FirstDatabaseProperties::class,
    SecondDatabaseProperties::class
)
@SpringBootApplication
class MultipleDataSourcesApplication

fun main(args: Array<String>) {
    runApplication<MultipleDataSourcesApplication>(*args)
}
