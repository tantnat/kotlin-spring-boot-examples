package com.github.tantnat.batch.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api/job")
class JobController(private val jobLauncher: JobLauncher) {

    @Qualifier("simpleJob")
    @Autowired
    lateinit var simpleJob: Job

    @Qualifier("jpaSimpleJob")
    @Autowired
    lateinit var jpaSimpleJob: Job

    @PostMapping("/simple")
    fun startSimpleJob() {
        val jobParams = JobParametersBuilder()
                .addDate("currentDate", Date.from(Instant.now()))
                .toJobParameters()
        jobLauncher.run(simpleJob, jobParams)
    }


    @PostMapping("/jpaSimple")
    fun startJpaSimpleJob() {
        val jobParams = JobParametersBuilder()
            .addDate("currentDate", Date.from(Instant.now()))
            .toJobParameters()
        jobLauncher.run(jpaSimpleJob, jobParams)
    }


}