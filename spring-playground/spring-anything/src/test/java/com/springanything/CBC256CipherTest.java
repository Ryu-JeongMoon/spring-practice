package com.springanything;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.springanything.crypto.CBC256Cipher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class CBC256CipherTest {

	String id = "panda";
	String number = "1234";
	String name = "bear";

	@Test
	public void encDesTest() {
		String encryptedId = CBC256Cipher.encrypt(id);
		String encryptedNumber = CBC256Cipher.encrypt(number);
		String encryptedName = CBC256Cipher.encrypt(name);

		log.info("{} / {} / {}", encryptedId, encryptedNumber, encryptedName);

		String decryptedId = CBC256Cipher.decrypt(encryptedId);
		String decryptedNumber = CBC256Cipher.decrypt(encryptedNumber);
		String decryptedName = CBC256Cipher.decrypt(encryptedName);

		log.info("{} / {} / {}", decryptedId, decryptedNumber, decryptedName);

		assertThat(id).isEqualTo(decryptedId);
		assertThat(number).isEqualTo(decryptedNumber);
		assertThat(name).isEqualTo(decryptedName);
	}
}