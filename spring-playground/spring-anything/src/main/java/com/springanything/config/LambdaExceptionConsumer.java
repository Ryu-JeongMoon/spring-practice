package com.springanything.config;

import java.util.function.Consumer;

@FunctionalInterface
public interface LambdaExceptionConsumer<T, E extends Exception> {

	static <T, E extends Exception> Consumer<T> toUnchecked(LambdaExceptionConsumer<T, E> f) {
		return toUnchecked(f, e -> {
			throw new RuntimeException(e);
		});
	}

	static <T, E extends Exception> Consumer<T> toUnchecked(LambdaExceptionConsumer<T, E> f, Consumer<Exception> c) {
		return t -> {
			try {
				f.accept(t);
			} catch (Exception e) {
				c.accept(e);
			}
		};
	}

	void accept(T t) throws E;
}

