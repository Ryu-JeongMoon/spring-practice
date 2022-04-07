package com.example.ioc.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class Proto {
}
