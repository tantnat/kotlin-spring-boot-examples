package com.github.tantnat.multipledatasources.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "first-database")
class FirstDatabaseProperties {
    lateinit var hibernateDialect: String
}