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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Convenience variant of {@linkplain CachingFunction} for cases when actual value is supplied without particular
 * source.
 *
 * @since 0.1
 */
public final class CachingSupplier<T> implements Supplier<T> {

	private final Supplier<T> supply;
	private final List<T> value = new ArrayList<>(1);

	/**
	 * To create a <i>lateinit</i> value, you should specify a way to {@code get} it when it's requested.
	 *
	 * @param supply a function that builds <i>the lateinit value</i>. It is guaranteed to be called ones and only when
	 *               {@linkplain Supplier#get} method is called.
	 * @since 0.1
	 */
	public CachingSupplier(Supplier<T> supply) {
		Objects.requireNonNull(supply);
		this.supply = supply;
	}

	/**
	 * Returns cached value. The first call begets {@code the value retrieval} and caching.
	 *
	 * @since 0.1
	 */
	public T get() {
		if (value.isEmpty()) {
			value.add(supply.get());
		}
		return value.get(0);
	}
}