package cn.credit.service

import cn.credit.dao.UserRepository
import cn.credit.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService extends BaseService[User] {

  @Autowired val userRepository: UserRepository = null

}