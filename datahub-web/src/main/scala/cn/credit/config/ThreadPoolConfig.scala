package cn.credit.config

import java.util.concurrent.{Executor, ThreadPoolExecutor}

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class ThreadPoolConfig {

  @Bean
  def customizedAsyn:Executor={
    val executor = new ThreadPoolTaskExecutor()
    executor.setCorePoolSize(5)
    executor.setMaxPoolSize(10)
    executor.setQueueCapacity(1000)
    executor.setThreadNamePrefix("DefineExecutor-")

    // rejection-policy：当pool已经达到max size的时候，如何处理新任务  
    // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行  
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
    executor.initialize()
    executor
  }
}
