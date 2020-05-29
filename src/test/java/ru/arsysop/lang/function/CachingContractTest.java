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

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

interface CachingContractTest {

	@Test
	default void retrievalHappens() {
		Long millis = System.currentTimeMillis();
		Supplier<String> supplier = hex(millis);
		assertNotNull(supplier.get());
		assertEquals(Long.toHexString(millis), supplier.get());
	}

	@Test
	default void retrievalHappensLazily() {
		AtomicInteger counter = new AtomicInteger(0);
		counting(counter);
		assertEquals(0, counter.get());
	}

	@Test
	default void retrievalHappensOnes() {
		AtomicInteger counter = new AtomicInteger(0);
		Supplier<String> supplier = counting(counter);
		for (int i = 0; i < 5; i++) {
			supplier.get();
			assertEquals(1, counter.get());
		}
	}

	@Test
	default void prohibitsNullRetriever() {
		assertThrows(NullPointerException.class, this::createWithNullRetriever);
	}

	/**
	 * Supply hex string of the given {@code value}
	 */
	Supplier<String> hex(Long value);

	/**
	 * Supplier provided must increment {@code counter} on each <i>retrieve</i> request
	 */
	Supplier<String> counting(AtomicInteger counter);

	/**
	 * Construct the supplier under test with {@code null} retriever
	 */
	void createWithNullRetriever();

}
