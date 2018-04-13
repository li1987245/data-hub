package cn.credit

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ComponentScan
@SpringBootApplication
class Application

object Application extends App {
  SpringApplication.run(classOf[Application])
}