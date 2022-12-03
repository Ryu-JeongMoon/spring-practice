package com.springanything.conditional;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.*;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.StandardEnvironment;

public class DisabledOnMidnightCondition implements ExecutionCondition {

	private static final ConditionEvaluationResult ENABLED_BY_DEFAULT =
		enabled("@DisabledOnMidnight is not present");

	private static final ConditionEvaluationResult ENABLED_DURING_DAYTIME =
		enabled("Test is enabled during daytime");

	private static final ConditionEvaluationResult DISABLED_ON_MIDNIGHT =
		disabled("Disabled as it is around midnight");

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		StandardEnvironment environment = new StandardEnvironment();
		String property = environment.getProperty("transfer.database.enabled");
		System.out.println("property = " + property);

		// Optional<DisabledOnMidnight> optional = findAnnotation(context.getElement(), DisabledOnMidnight.class);
		// if (optional.isPresent()) {
		//   LocalDateTime now = LocalDateTime.now();
		//   if (now.getHour() == 23 || now.getHour() <= 1) {
		//     return DISABLED_ON_MIDNIGHT;
		//   } else {
		//     return ENABLED_DURING_DAYTIME;
		//   }
		// }
		return ENABLED_BY_DEFAULT;
	}

	@Documented
	@Target({ ElementType.TYPE, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	@ExtendWith(DisabledOnMidnightCondition.class)
	public @interface DisabledOnMidnight {

	}
}
