package com.springanything.conditional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class ConditionalTest {

	@Test
	public void disabledOnLinux() {
		assertEquals(42, 40 + 2);
	}

	@Test
	@DisabledIfEnvironmentVariable(named = "FLAKY_TESTS", matches = "false")
	public void disableFlakyTest() {
		assertEquals(42, 40 + 2);
	}

	@Test
	@DisabledOnMidnightCondition.DisabledOnMidnight
	public void disabledOnMidNight() {
		assertEquals(42, 40 + 2);
	}
}
