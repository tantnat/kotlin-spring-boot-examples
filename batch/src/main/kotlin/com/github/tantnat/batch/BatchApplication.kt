package com.github.tantnat.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}
