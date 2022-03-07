/*
 * Paradiddle MS - A lightweight microservices library with a comprehensible codebase.
 * Copyright (c) Michael Juliano 2020-2021.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, write to:
 *
 * Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330
 * Boston, MA 02111-1307 USA
 */

plugins {
    groovy
    jacoco
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("org.nosphere.apache.rat") version "0.7.0"
}

repositories {
    mavenCentral()
}

testSets {
    "integrationTest"()
}

dependencies {
    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")

    "integrationTestImplementation"("org.codehaus.groovy:groovy-all:3.0.10")
    "integrationTestImplementation"("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks {
    named<Test>("integrationTest") {
        useJUnitPlatform()
    }

    named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
        dependsOn("test", "integrationTest")
        executionData(
            files(
                listOf(
                    "$buildDir/jacoco/test.exec",
                    "$buildDir/jacoco/integrationTest.exec"
                )
            )
        )
        violationRules {
            rule {
                element = "BUNDLE"

                limit {
                    counter = "CLASS"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal("0.833")
                }
            }

            rule {
                element = "CLASS"

                limit {
                    counter = "METHOD"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal("1.000")
                }

                excludes = listOf(
                    // Class only delegates so won"t benefit from test coverage
                    "io.paradiddle.ms.util.SetDelegate",
                    // Class is a container for static keys for use with HttpExchange#getAttribute
                    "io.paradiddle.ms.httpserver.HttpExchangeAttributes"
                )
            }

            rule {
                element = "METHOD"

                limit {
                    counter = "INSTRUCTION"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal("1.000")
                }

                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal("1.000")
                }

                limit {
                    counter = "COMPLEXITY"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal("1.000")
                }

                excludes = listOf(
                    // Methods of class that only delegates
                    "io.paradiddle.ms.util.SetDelegate.SetDelegate(java.util.Set)",
                    "io.paradiddle.ms.util.SetDelegate.size()",
                    "io.paradiddle.ms.util.SetDelegate.isEmpty()",
                    "io.paradiddle.ms.util.SetDelegate.contains(java.lang.Object)",
                    "io.paradiddle.ms.util.SetDelegate.iterator()",
                    "io.paradiddle.ms.util.SetDelegate.toArray()",
                    "io.paradiddle.ms.util.SetDelegate.toArray(java.lang.Object[])",
                    "io.paradiddle.ms.util.SetDelegate.add(java.lang.Object)",
                    "io.paradiddle.ms.util.SetDelegate.remove(java.lang.Object)",
                    "io.paradiddle.ms.util.SetDelegate.containsAll(java.util.Collection)",
                    "io.paradiddle.ms.util.SetDelegate.addAll(java.util.Collection)",
                    "io.paradiddle.ms.util.SetDelegate.retainAll(java.util.Collection)",
                    "io.paradiddle.ms.util.SetDelegate.removeAll(java.util.Collection)",
                    "io.paradiddle.ms.util.SetDelegate.clear()",
                    "io.paradiddle.ms.util.SetDelegate.equals(java.lang.Object)",
                    "io.paradiddle.ms.util.SetDelegate.hashCode()",
                    "io.paradiddle.ms.util.SetDelegate.spliterator()",
                    // Only returns a string that has no impact on execution elsewhere
                    "io.paradiddle.ms.httpserver.RequestRuleFilter.description()",
                    // No class implementation
                    "io.paradiddle.ms.httpserver.HttpExchangeAttributes.HttpExchangeAttributes()"
                )
            }
        }
    }

    named<org.nosphere.apache.rat.RatTask>("rat") {
        substringMatcher(
            "GPL2",
            "GPL2",
            "GNU General Public License version 2"
        )
        approvedLicense("GPL2")
        excludes.add(".github/**")
        excludes.add(".gitignore")
        excludes.add(".idea/**")
        excludes.add("build/**")
        excludes.add("gradle/**")
        excludes.add("gradlew")
        excludes.add("gradlew.bat")
        excludes.add("README.md")
    }

    named<Test>("test") {
        useJUnitPlatform()
    }

    register<JacocoReport>("codeCoverageReport") {
        group = "reporting"
        dependsOn("test", "integrationTest")
        executionData(files(withType<Test>()).filter { it.name.endsWith(".exec") })
        sourceSets(sourceSets.main.get())

        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(true)
        }
    }
}
