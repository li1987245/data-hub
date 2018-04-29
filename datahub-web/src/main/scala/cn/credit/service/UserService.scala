package cn.credit.service

import java.util.concurrent.{CompletableFuture, Future}

import cn.credit.dao.UserRepository
import cn.credit.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class UserService extends BaseService[User] {

  @Autowired val userRepository: UserRepository = null

  /**
    * 异步方法不能和调用方法处于同一类中  否则不生效
    * @param name
    * @return
    */
  @Async("customizedAsyn")
  def getName(name:String):Future[String] = {
    println("2")
    Thread.sleep(3000)
    println("3")
    CompletableFuture.completedFuture(name)
  }

}