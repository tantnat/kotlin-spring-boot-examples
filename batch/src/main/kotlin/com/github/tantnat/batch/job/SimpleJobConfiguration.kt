package com.github.tantnat.batch.job

import com.github.tantnat.batch.processor.simple.SimpleProcessor
import com.github.tantnat.batch.processor.simple.SimpleReader
import com.github.tantnat.batch.processor.simple.SimpleWriter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.WebApplicationContext

@Configuration
@EnableBatchProcessing
class SimpleJobConfiguration(
        private val jobBuilderFactory: JobBuilderFactory,
        private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun simpleJob(): Job = jobBuilderFactory.get("SimpleJob")
            .start(simpleJobStep())
            .build()

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun simpleJobStep(): Step = stepBuilderFactory.get("SimpleStep")
            .chunk<String,String>(5)
            .reader(simpleReader())
            .processor(simpleProcessor())
            .writer(simpleWriter())
            .build()

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun simpleReader(): ItemReader<String> {
        return SimpleReader()
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun simpleProcessor(): SimpleProcessor {
        return SimpleProcessor()
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun simpleWriter(): SimpleWriter {
        return SimpleWriter()
    }

}