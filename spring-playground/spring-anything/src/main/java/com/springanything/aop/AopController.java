package com.springanything.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@WithParameter
@RestController
public class AopController {

    @BeforePrint
    @GetMapping("/test/yahoo")
    public String yahoo() {
        return "yahoo~!";
    }

    @GetMapping("/test/aop-model-attribute")
    public AopRequest mappingModelAttribute(AopRequest aopRequest) {
        log.info("aopRequest = {}", aopRequest);
        return aopRequest;
    }

    @GetMapping("/test/aop-request-body")
    public AopRequest mappingRequestBody(@RequestBody AopRequest aopRequest) {
        log.info("aopRequest = {}", aopRequest);
        return aopRequest;
    }
}
