/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT which is available at
 * https://spdx.org/licenses/MIT.html#licenseText
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package ru.arsysop.lang.function;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class CachingSupplierTest implements CachingContractTest {

	@Override
	public Supplier<String> hex(Long value) {
		return new CachingSupplier<>(() -> Long.toHexString(value));
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
