# lang

[![Build](https://github.com/arsysop/lang/workflows/Java%20CI/badge.svg)](https://github.com/arsysop/lang/actions?query=workflow%3A%22Java+CI%22)
[![Hits-of-Code](https://hitsofcode.com/github/arsysop/lang)](https://hitsofcode.com/view/github/arsysop/lang)
[![codecov](https://codecov.io/gh/arsysop/lang/branch/master/graph/badge.svg)](https://codecov.io/gh/arsysop/lang)

[![.](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://github.com/arsysop/lang/blob/master/LICENSE)

[![Release](https://img.shields.io/badge/Release-Latest%200.1-pink.svg)](https://github.com/arsysop/lang/releases/latest)

Orthodox OOP constructions of extensive use that we need badly in all our projects.

Currently contains `java.util.function` extensions `CachingFunction` and `CachingSupplier` 
who look like `Function` and `Supplier` correspondingly, but encapsulate caching behaviour.

### how to use
#### dependency
The library is published on `jcenter`. To apply to, say, gradle build, use 
```groovy
repositories {
    jcenter()
}

dependencies {
    implementation group: 'ru.arsysop.lang', name: 'lang', version: '0.1'
}
```
or have [a hint](https://mvnrepository.com/artifact/ru.arsysop.lang/lang/0.1) for your build automation system.

#### code

Here is the sample of [CachingFunction](src/main/java/ru/arsysop/lang/function/CachingFunction.java) usage. It
 - does not perform any calculations in ctor
 - does not contain not-final fields
 - calculates lazily
 - and do it only ones for the first need
```java
public final class UserFromEmail {

	private final CachingFunction<String, String> name;

	/**
	 * Get the first part of an email address (before '@'-symbol) as a user name.
	 *
	 * @param email valid email address
	 */
	public UserFromEmail(String email) {
		name = new CachingFunction<>(email, input -> input.substring(0, input.indexOf('@')));
	}

	public String name() {
		return name.get();
	}

}
```

 

   

