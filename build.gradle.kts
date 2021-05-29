plugins {
    groovy
    jacoco
    id("org.unbroken-dome.test-sets") version "4.0.0"
}

repositories {
    mavenCentral()
}

testSets {
    "integrationTest"()
}

dependencies {
    testImplementation("org.codehaus.groovy:groovy-all:2.5.12")
    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
    testImplementation("junit:junit:4.13.2")

    "integrationTestImplementation"("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1")
}

tasks {
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
                    minimum = BigDecimal(1.000)
                }
            }

            rule {
                element = "CLASS"

                limit {
                    counter = "METHOD"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal(1.000)
                }

                excludes = listOf(
                    // Class only delegates so won"t benefit from test coverage
                    "io.paradiddle.ms.util.SetDelegate"
                )
            }

            rule {
                element = "METHOD"

                limit {
                    counter = "INSTRUCTION"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal(1.000)
                }

                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal(1.000)
                }

                limit {
                    counter = "COMPLEXITY"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal(1.000)
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
                    "io.paradiddle.ms.util.SetDelegate.spliterator()"
                )
            }
        }
    }

    register<JacocoReport>("codeCoverageReport") {
        group = "reporting"
        dependsOn("test", "integrationTest")
        executionData(files(withType<Test>()).filter { it.name.endsWith(".exec") })
        sourceSets(sourceSets.main.get())

        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.isEnabled = true
        }
    }
}


