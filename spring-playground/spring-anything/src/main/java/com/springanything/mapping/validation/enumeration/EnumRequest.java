package com.springanything.mapping.validation.enumeration;

import com.springanything.mapping.validation.NotNullEnum;

public record EnumRequest(@NotNullEnum Bears bears) {

}
