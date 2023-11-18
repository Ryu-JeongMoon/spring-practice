package com.springanything.mapping.validation.enumeration;

import com.springanything.mapping.validation.validator.NotNullEnum;

public record EnumRequest(@NotNullEnum Bears bears) {

}
