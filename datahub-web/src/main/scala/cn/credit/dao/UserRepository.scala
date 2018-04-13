package cn.credit.dao

import cn.credit.entity.User
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import java.lang.Long

trait UserRepository extends JpaRepository[User, Long]{
  @Query(value = "SELECT * FROM user where user_name like '%?1%'", nativeQuery = true)
  def listByName(name: String): List[User]
}
