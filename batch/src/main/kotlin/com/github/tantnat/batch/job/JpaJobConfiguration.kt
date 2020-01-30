package com.github.tantnat.batch.job

import com.github.tantnat.batch.entity.SimpleJpaEntity
import com.github.tantnat.batch.entity.SimpleJpaProcessedEntity
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate
import javax.persistence.EntityManagerFactory

@Configuration
@EnableBatchProcessing
class JpaJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val entityManagerFactory: EntityManagerFactory
) {

    @Bean
   @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun jpaSimpleJob(): Job = jobBuilderFactory.get("JpaSimpleJob")
        .start(jpaSimpleJobStep())
        .build()

    @Bean
    @JobScope
    fun jpaSimpleJobStep(): Step = stepBuilderFactory.get("JpaSimpleStep")
        .chunk<SimpleJpaEntity, SimpleJpaProcessedEntity>(5)
        .reader(jpaSimpleReader())
        .processor(jpaSimpleProcessor())
        .writer(jpaSimpleWriter())
        .build()

    @Bean
    @StepScope
    fun jpaSimpleReader(): JpaPagingItemReader<out SimpleJpaEntity> {
        return JpaPagingItemReaderBuilder<SimpleJpaEntity>()
            .name("simple jpa")
            .entityManagerFactory(entityManagerFactory)
            .queryString("from SimpleJpaEntity c")
            .pageSize(2)
            .build()
    }

    @Bean
    @StepScope
    fun jpaSimpleProcessor(): ItemProcessor<SimpleJpaEntity, SimpleJpaProcessedEntity> {
        return ItemProcessor { it.convertToProcessedEntity() }
    }

    @Bean
    @StepScope
    fun jpaSimpleWriter(): JpaItemWriter<SimpleJpaProcessedEntity> {
        return JpaItemWriterBuilder<SimpleJpaProcessedEntity>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}

private fun SimpleJpaEntity.convertToProcessedEntity(): SimpleJpaProcessedEntity? {
    return SimpleJpaProcessedEntity(0, this.name.toUpperCase(), this.surname.toUpperCase(), LocalDate.now())
}
