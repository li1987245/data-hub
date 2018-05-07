package cn.credit

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@ComponentScan
@SpringBootApplication
@EnableAsync
@EnableFeignClients
@EnableDiscoveryClient
class Application

object Application extends App {
  SpringApplication.run(classOf[Application])
}