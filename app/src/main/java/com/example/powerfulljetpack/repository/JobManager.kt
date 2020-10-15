package com.example.powerfulljetpack.repository

import kotlinx.coroutines.Job

open class JobManager(
    private val className: String
) {

    private val jobs: HashMap<String, Job> = HashMap()

    fun addjob(methodName: String, job: Job) {

        //cancel the previuse one first
        cancellJob(methodName)
        jobs[methodName] = job
    }

    fun cancellJob(methodName: String) {
        getJob(methodName)?.cancel()
    }

    fun getJob(methodName: String): Job? {

        if (jobs.containsKey(methodName)) {
            jobs[methodName]?.let {

                return it
            }
        }
        return null
    }


    //cancell everyjob when navigate backA
    fun cancellActiveJobs() {
        for ((methodname, job) in jobs) {
            if (job.isActive) {
                job.cancel()
            }
        }
    }
}