package org.springkafka.panda.interactor;

import org.springframework.stereotype.Component;

@Component
public class PandaReceiver implements Runnable {

	protected final static int RECEIVE_TIMEOUT = 1000;

	@Override
	public void run() {

	}
}
