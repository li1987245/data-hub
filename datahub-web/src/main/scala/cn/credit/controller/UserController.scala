package cn.credit.controller

import cn.credit.entity.User
import cn.credit.service.UserService
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.domain.Page
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

@ComponentScan
@Controller
@ResponseBody
class UserController @Autowired()(private val userService : UserService){

  @RequestMapping(value = Array("/list"), method = Array(RequestMethod.GET))
  def list() : List[User] = {
    userService.findAll
  }

  @RequestMapping(value = Array("save"), method = Array(RequestMethod.POST))
  def save(@Valid user : User) : User = {
    userService.save(user)
  }

  @RequestMapping(value = Array("/find/{id}"), method = Array(RequestMethod.GET))
  def find(@PathVariable(value = "id") id: Long) : User = {
    userService.find(id)
  }

  @RequestMapping(value = Array("delete/{id}"), method = Array(RequestMethod.POST))
  def delete(@PathVariable(value = "id") id: Long) : Unit = {
    userService.delete(id)
  }

  @RequestMapping(value = Array("update"), method = Array(RequestMethod.POST))
  def update(@Valid user : User, bindingResult : BindingResult) : User = {
    userService.update(user)
  }

  @RequestMapping(value = Array("page"), method = Array(RequestMethod.GET))
  def page(@RequestParam("page") page : Int, @RequestParam("pageSize") pageSize : Int) : Page[User] = {
    userService.page(page, pageSize)
  }

}
