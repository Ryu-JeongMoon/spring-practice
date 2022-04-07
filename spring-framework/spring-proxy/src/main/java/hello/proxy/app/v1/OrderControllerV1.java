package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping
public interface OrderControllerV1 {

  @GetMapping("/v1/request")
  String request(@RequestParam("itemId") String itemId);

  @GetMapping("/v1/no-log")
  String noLog();
}

/*
@Controller 붙이지 않아도 클래스에 @RequestMapping 있다면 스프링이 컨트롤러로 인식

컨트롤러 클래스에서는 아래와 같은 형태로 사용할 필요가 없으나
인터페이스에는 버전 별로 제대로 동작하지 않을 때가 있어 @RequestParam, @ModelAttribute 등 명시적으로 붙여주는게 좋다
@RequestParam("itemId") String itemId
 */