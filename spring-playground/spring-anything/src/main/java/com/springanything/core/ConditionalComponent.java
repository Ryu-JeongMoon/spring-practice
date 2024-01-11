package com.springanything.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "springanything.conditional", havingValue = "true")
public class ConditionalComponent {

}
