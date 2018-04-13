package cn.credit.entity


import java.util.Date

import javax.persistence.{Entity, GeneratedValue, GenerationType, Id}
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.NotBlank
import org.springframework.format.annotation.DateTimeFormat

import scala.beans.BeanProperty
import scala.language.implicitConversions

@Entity
class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Integer = _

  @BeanProperty
  @NotBlank
  var userName: String = _

  @BeanProperty
  @NotNull
  var password: String = _

  @BeanProperty
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  var birthday: Date = _

  @BeanProperty
  val createTime: Date = new Date()

  override def toString = "id = " + id + ",userName = " + userName + ",birthday = " + birthday + ",createTime = " + createTime
}