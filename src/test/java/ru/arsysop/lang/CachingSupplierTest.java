/********************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   ArSysOp - initial API and implementation
 ********************************************************************************/
package ru.arsysop.lang;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CachingSupplierTest implements CachingContractTest {

	@Override
	public Supplier<String> hexNow() {
		return new CachingSupplier<>(() -> Long.toHexString(System.currentTimeMillis()));
	}

	@Override
	public Supplier<String> counting(AtomicInteger counter) {
		return new CachingSupplier<>(() -> {
			counter.incrementAndGet();
			return "";
		});
	}

	@Override
	public void createWithNullRetriever() {
		new CachingSupplier<>(null);
	}

}
