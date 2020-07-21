package br.com.wesjon.linters.detekt.rule

import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class FailOnEveryMethodRuleTest {
    private val codeExample = """
        class Car (
            private val engine: Engine
        ) {
            val speed = 0
        
            fun start() {
                engine.start()
            }
            
            fun stop() {
                engine.stop()
            }
        }
    """.trimIndent()

    private val failOnEveryMethodRule = FailOnEveryMethodRule()

    @Test
    fun howDetektSees(){
        val findings = failOnEveryMethodRule.lint(codeExample)


    }
}