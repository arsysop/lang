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

interface CachingContractTest {

	@Test
	default void creationHappens() {
		Supplier<String> supplier = hexNow();
		assertNotNull(supplier.get());
		assertTrue(supplier.get().startsWith(Long.toHexString(System.currentTimeMillis()).substring(0, 7)));
	}


	@Test
	default void creationHappensLazily() {
		AtomicInteger counter = new AtomicInteger(0);
		counting(counter);
		assertEquals(0, counter.get());
	}

	@Test
	default void creationHappensOnes() {
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
	 * Supply hex string of current millis
	 */
	Supplier<String> hexNow();

	/**
	 * Supplier provided must increment {@code counter} on each <i>retrieve</i> request
	 */
	Supplier<String> counting(AtomicInteger counter);

	/**
	 * Construct the supplier under test with {@code null} retriever
	 */
	void createWithNullRetriever();

}
