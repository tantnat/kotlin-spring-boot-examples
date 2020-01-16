package com.github.tantnat.batch.job

import com.github.tantnat.batch.entity.SimpleJpaEntity
import com.github.tantnat.batch.entity.SimpleJpaProcessedEntity
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    fun jpaSimpleReader(): JpaPagingItemReader<out SimpleJpaEntity> {
        return JpaPagingItemReaderBuilder<SimpleJpaEntity>()
            .entityManagerFactory(entityManagerFactory)
            .queryString("from SimpleJpaEntity c")
            .pageSize(2)
            .build()
    }

    @Bean
    fun jpaSimpleProcessor(): ItemProcessor<SimpleJpaEntity, SimpleJpaProcessedEntity> {
        return ItemProcessor { it.convertToProcessedEntity() }
    }

    @Bean
    fun jpaSimpleWriter(): JpaItemWriter<SimpleJpaProcessedEntity> {
        return JpaItemWriterBuilder<SimpleJpaProcessedEntity>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}

private fun SimpleJpaEntity.convertToProcessedEntity(): SimpleJpaProcessedEntity? {
    return SimpleJpaProcessedEntity(0, this.name, this.surname, LocalDate.now())
}
