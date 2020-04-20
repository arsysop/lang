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
package ru.arsysop.lang;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CachingFunctionTest implements CachingContractTest {

	@Override
	public Supplier<String> hex(Long value) {
		return new CachingFunction<>(value, Long::toHexString);
	}

	@Override
	public Supplier<String> counting(AtomicInteger counter) {
		return new CachingFunction<>(new Object(), o -> {
			counter.incrementAndGet();
			return "";
		});
	}

	@Override
	public void createWithNullRetriever() {
		new CachingFunction<>(new Object(), null);
	}

	@Test
	void prohibitsNullSource() {
		assertThrows(NullPointerException.class, () -> new CachingFunction<>(null, o -> new Object()));
	}

}
