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

plugins {
    java
    jacoco
    `maven-publish`
}

group = "ru.arsysop"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

java {
    withJavadocJar()
    withSourcesJar()
}

jacoco {
    toolVersion = "0.8.5"
}

tasks.test {
    useJUnitPlatform {
        systemProperties["junit.jupiter.testinstance.lifecycle.default"] = "per_class"
    }
}

tasks.jacocoTestReport {
    reports {
        csv.isEnabled = false
        html.isEnabled = false
        xml.isEnabled = true
        xml.destination = file("$buildDir/test-coverage.xml")

    }
}

tasks.withType(Jar::class.java) {
    manifest {
        extendManifest(Manifest@ this)
    }
}

tasks.getByName("sourcesJar") {
    (this as Jar).apply {
        from(
            sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).allSource,
            sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME).allSource,
            file("README.md")
        )
    }
}

fun extendManifest(mf: Manifest): Unit {
    mf.attributes(
        "Bundle-ManifestVersion" to "2",
        "Bundle-SymbolicName" to project.name,
        "Bundle-Name" to project.name,
        "Bundle-Version" to project.version,
        "Bundle-Vendor" to "ArSysOp",
        "Bundle-RequiredExecutionEnvironment" to "JavaSE-1.8"
    )
}
