package com.github.tantnat.multipledatasources.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "second-database")
class SecondDatabaseProperties {
    lateinit var hibernateDialect: String
}