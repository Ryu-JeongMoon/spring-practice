package com.springanything.jasypt;

import static org.assertj.core.api.Assertions.*;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.springanything.AbstractIntegrationTest;

class JasyptEncryptorTest extends AbstractIntegrationTest {

	@Autowired
	private Environment environment;

	@Autowired
	private StringEncryptor stringEncryptor;

	@Test
	void encrypt() {
		// given
		String username = "test-username";
		String password = "test-password";

		// when
		String encryptedUsername = stringEncryptor.encrypt(username);
		String encryptedPassword = stringEncryptor.encrypt(password);

		// then
		log.info("Encrypted username: {}", encryptedUsername);
		log.info("Encrypted password: {}", encryptedPassword);
	}

	@Test
	void decrypt() {
		// given
		String username = "test-username";
		String password = "test-password";

		// when
		String usernameProperty = environment.getProperty("app.username");
		String passwordProperty = environment.getProperty("app.password");

		// then
		assertThat(usernameProperty).isEqualTo(username);
		assertThat(passwordProperty).isEqualTo(password);
	}
}

/*
log - c.u.j.encryptor.DefaultLazyEncryptor
String Encryptor custom Bean not found with name 'jasyptStringEncryptor'. Initializing Default String Encryptor

log - c.u.j.c.StringEncryptorBuilder
Encryptor config not found for property jasypt.encryptor.key-obtention-iterations, using default value: 1000
Encryptor config not found for property jasypt.encryptor.pool-size, using default value: 1
Encryptor config not found for property jasypt.encryptor.provider-name, using default value: null
Encryptor config not found for property jasypt.encryptor.provider-class-name, using default value: null
Encryptor config not found for property jasypt.encryptor.salt-generator-classname, using default value: org.jasypt.salt.RandomSaltGenerator
Encryptor config not found for property jasypt.encryptor.string-output-type, using default value: base64
 */