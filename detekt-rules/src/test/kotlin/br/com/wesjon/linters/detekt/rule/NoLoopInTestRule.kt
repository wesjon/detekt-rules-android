package br.com.wesjon.linters.detekt.rule

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class NoLoopInTestRuleTest {
    private val testWithLoop = """
        class Test {
            @Test
            fun `addition isCorrect`() {
                for (i in 0..3) {
                    assertEquals(4, 2 + 2)
                }
            }
        }
    """.trimIndent()

    private val testWithNoLoop = """
        class Test {
            @Test
            fun allOddsTest() {
                val list = listOf(2, 4)
                assertTrue(list[0] % 2 == 0)
                assertTrue(list[1] % 2 == 0)
            }
        }
    """.trimIndent()

    private val noLoopInTestRule = NoLoopInTestRule()

    @Test
    fun `test contains loop should report issue`() {
        val findings = noLoopInTestRule.lint(testWithLoop)

        assertThat(findings).hasSize(1)
        assertThat(findings[0].message).isEqualTo("")
    }

    @Test
    fun `test contains no loop should report nothing`(){
        val findings = noLoopInTestRule.lint(testWithNoLoop)

        assertThat(findings).isEmpty()
    }
}