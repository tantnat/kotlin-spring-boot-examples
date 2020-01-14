package com.github.tantnat.batch.processor.simple

import org.springframework.batch.item.ItemProcessor

open class SimpleProcessor : ItemProcessor<String, String> {
    override fun process(item: String): String? {
        return item.toUpperCase()
    }
}