package cn.credit

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@ComponentScan
@SpringBootApplication
@EnableAsync
class Application

object Application extends App {
  SpringApplication.run(classOf[Application])
}