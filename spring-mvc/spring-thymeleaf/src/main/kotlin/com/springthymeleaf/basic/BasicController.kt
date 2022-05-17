package com.springthymeleaf.basic

import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/basic")
class BasicController {

  @GetMapping("/text-basic")
  fun textBasic(model: Model): String {
    model.addAttribute("data", "hello kotlin")
    return "basic/text-basic";
  }

  @GetMapping("/text-unescaped")
  fun textUnescaped(model: Model): String {
    model.addAttribute("data", "<b>hello unescaped kotlin</b>")
    return "basic/text-unescaped";
  }

  @GetMapping("/variable")
  fun variable(model: Model): String {
    val panda = User("panda", 15)
    val bear = User("bear", 55)

    val users = listOf(panda, bear)
    val userMap = mapOf("panda" to panda, "bear" to bear)

    model.addAttribute("user", panda)
    model.addAttribute("users", users)
    model.addAttribute("userMap", userMap)

    return "basic/variable"
  }

  @GetMapping("/basic-objects")
  fun basicObjects(session: HttpSession): String {
    session.setAttribute("sessionData", "hello session")
    return "basic/basic-objects"
  }

  @GetMapping("/date")
  fun date(model: Model): String {
    model.addAttribute("localDateTime", LocalDateTime.now())
    return "basic/date"
  }

  @GetMapping("/link")
  fun link(model: Model): String {
    model.addAttribute("param1", "data1")
    model.addAttribute("param2", "data2")
    return "basic/link"
  }

  @GetMapping("/literal")
  fun literal(model: Model): String {
    model.addAttribute("data", "kotlin")
    return "basic/literal"
  }

  @GetMapping("/operation")
  fun operation(model: Model): String {
    model.addAttribute("nullData", null)
    model.addAttribute("data", "kotlin")
    return "basic/operation"
  }

  @GetMapping("/attribute")
  fun attribute(): String {
    return "basic/attribute"
  }

}

@Component
class HelloBean {

  fun hello(data: String): String {
    return "hello $data"
  }
}

data class User(val username: String, val age: Int) {

}

/*
[[${data}]]
문자열에서 값으로 인식되게 하나벼

사용자의 악의적이거나 잘못된 태그 사용으로 인해 html이 깨질 수 있어
thymeleaf는 기본적으로 escape 처리를 지원한다
의도적으로 unescaped 하고 싶다면 th:text 대신 th:utext, [[${data}]] 대신 [(${data})]
쓸 일 거의 없듬
 */