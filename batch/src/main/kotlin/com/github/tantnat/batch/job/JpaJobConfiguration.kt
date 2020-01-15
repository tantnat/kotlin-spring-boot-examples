package com.github.tantnat.batch.job

import com.github.tantnat.batch.entity.SimpleJpaEntity
import com.github.tantnat.batch.entity.SimpleJpaProcessedEntity
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.WebApplicationContext

@Configuration
@EnableBatchProcessing
class JpaJobConfiguration(
        private val jobBuilderFactory: JobBuilderFactory,
        private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
//    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun jpaSimpleJob(): Job = jobBuilderFactory.get("JpaSimpleJob")
            .start(jpaSimpleJobStep())
            .build()

    @Bean
    fun jpaSimpleJobStep(): Step = stepBuilderFactory.get("JpaSimpleStep")
            .chunk<SimpleJpaEntity, SimpleJpaProcessedEntity>(5)
            .reader(jpaSimpleReader())
            .processor(jpaSimpleProcessor())
            .writer(jpaSimpleWriter())
            .build()

    @Bean
    fun jpaSimpleReader(): ItemReader<out SimpleJpaEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Bean
    fun jpaSimpleProcessor(): ItemProcessor<SimpleJpaEntity, SimpleJpaProcessedEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Bean
    fun jpaSimpleWriter(): ItemWriter<SimpleJpaProcessedEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}