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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>
 * Orthodox OOP construction.
 * </p>
 *
 * <p>
 * Seat of mutability caused by prohibition of any calculation in a ctor and necessity to reuse a complicated calculation result.
 * </p>
 *
 * <p>
 * Typical use case:
 * </p>
 * <ul>
 * <li>you have a final field which, of course, must be initialized immediately
 * in ctor</li>
 * <li>you follow OOOP rule for light-weight ctor: no calculations, period</li>
 * </ul>
 *
 * <p>
 * Hence, you initialize a field with
 * </p>
 * <ul>
 * <li>a source of data and</li>
 * <li>a memorized way of retrieving the value from this source.</li>
 * </ul>
 *
 * <p>
 * The actual calculation is performed lazily, at the first {@code get} call.
 * </p>
 *
 * <p>
 * Then the result is cached (forever) and all further {@code get}s do not cause
 * any calculation.
 * </p>
 *
 * <h3>Sample</h3>
 * <pre>
 * public final class User {
 *
 * 	private final CachingFunction&lt;String, String&gt; name;
 *
 *	public User(String validEmail) {
 * 		this.name =  new CachingFunction&lt;&gt;(validEmail, email -&gt; email.substring(0, email.indexOf('@')));
 * 	}
 *
 * 	public String name(){
 * 		return name.get(); // calculation is performed only for the first call
 * 	}
 *
 * }
 * </pre>
 *
 * @param <S> type of data source object
 * @param <T> type of data retrieved and cached
 * @since 0.1
 */
public final class CachingFunction<S, T> implements Supplier<T> {

	private final S source;
	private final Function<S, T> retrieve;
	private final List<T> value = new ArrayList<>(1);

	/**
	 * To create a <i>lateinit</i> value, you should specify a {@code source} for initialization and a {@code way} to
	 * perform it.
	 *
	 * @param source   original data sufficient to build the <i>lateinited value</i> from it
	 * @param retrieve a function that builds <i>the lateinit value</i> from a given {@code source}. It is guaranteed to
	 *                 be called ones and only when {@linkplain Supplier#get} method is called.
	 * @since 0.1
	 */
	public CachingFunction(S source, Function<S, T> retrieve) {
		Objects.requireNonNull(source);
		Objects.requireNonNull(retrieve);
		this.source = source;
		this.retrieve = retrieve;
	}

	/**
	 * Returns cached value. The first call begets {@code the value retrieval} and caching.
	 *
	 * @since 0.1
	 */
	public T get() {
		if (value.isEmpty()) {
			value.add(retrieve.apply(source));
		}
		return value.get(0);
	}

}