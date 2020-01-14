package com.github.tantnat.batch.processor.simple

import org.springframework.batch.item.ItemReader
import java.util.*

open class SimpleReader : ItemReader<String> {

    private val items = ArrayDeque<String>()

    init {
        for (i in 1..10) {
            items.add("test data #$i")
        }
    }

    override fun read(): String? {
        return if (items.isNotEmpty()) {
            items.poll()
        } else {
            null
        }
    }
}