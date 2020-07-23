package br.com.wesjon.linters.detekt.rule

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class TestNameShouldBeEnclosedInBacktickTest {
    private val testThatdoesntContainBacktick = """
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
    """.trimIndent()

    private val testThatContainBacktick = """
class ExampleUnitTest {
    @Test
    fun `addition is correct`() {
        assertEquals(4, 2 + 2)
    }
}
    """.trimIndent()

    private val testShouldContainBacktickRule = TestNameShouldBeEnclosedInBacktick()

    @Test
    fun `test doesnt contains backtick should report issue`() {
        val findings = testShouldContainBacktickRule.lint(testThatdoesntContainBacktick)

        assertThat(findings).hasSize(1)
        assertThat(findings[0].message).isEqualTo("O nome do teste precisa ter backtick")
    }

    @Test
    fun `test contains backtick find no issues`() {
        val findings = testShouldContainBacktickRule.lint(testThatContainBacktick)

        assertThat(findings).isEmpty()
    }
}