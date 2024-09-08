package com.springanything.security.method;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoleChecker {

  private final MethodUserRepository methodUserRepository;

  public boolean isSelf(Object userIdObject) {
    String userId = extractUserId(userIdObject);
    String currentUserId = SecurityUtils.currentUserId();

    return StringUtils.isNoneBlank(userId, currentUserId)
      && StringUtils.equals(userId, currentUserId)
      && methodUserRepository.existsById(userId);
  }

  private String extractUserId(Object userIdObject) {
    if (userIdObject instanceof String userId) {
      return userId;
    } else if (userIdObject != null) {
      return extractUserIdFromObject(userIdObject);
    } else {
      return null;
    }
  }

  private String extractUserIdFromObject(Object obj) {
    for (Field field : obj.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(UserIdParameter.class)) {
        field.setAccessible(true);
        try {
          Object value = field.get(obj);
          return value != null ? value.toString() : null;
        } catch (IllegalAccessException e) {
          log.error("error : ", e);
        }
      }
    }
    return null;
  }
}
