/*
 * Copyright 2011-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springanything.config.transaction;

import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * {@link org.springframework.data.transaction.SynchronizationManager} delegating calls to Spring's {@link TransactionSynchronizationManager}.
 *
 * @author Michael Hunger
 * @author Oliver Gierke
 * @since 1.6
 */
enum SpringTransactionSynchronizationManager implements SynchronizationManager {

  INSTANCE;

  public void initSynchronization() {
    TransactionSynchronizationManager.initSynchronization();
  }

  public boolean isSynchronizationActive() {
    return TransactionSynchronizationManager.isSynchronizationActive();
  }

  public void clearSynchronization() {
    TransactionSynchronizationManager.clear();
  }
}
