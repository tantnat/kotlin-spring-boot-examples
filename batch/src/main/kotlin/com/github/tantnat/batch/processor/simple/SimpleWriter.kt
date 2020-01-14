package com.github.tantnat.batch.processor.simple

import org.springframework.batch.item.ItemWriter

open class SimpleWriter : ItemWriter<String> {
    override fun write(items: MutableList<out String>) {
        println(items)
    }
}