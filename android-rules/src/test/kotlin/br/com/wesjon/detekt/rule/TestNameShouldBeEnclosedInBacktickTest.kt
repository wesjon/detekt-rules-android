package br.com.wesjon.detekt.rule

import br.com.wesjon.detekt.rule.TestNameShouldFollowNamingConvention.Companion.CONVENTION_KEY
import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class TestNameShouldFollowNamingConventionTest {
    private val testConventionBacktick = TestNameShouldFollowNamingConvention(
        TestConfig(mapOf(CONVENTION_KEY to NamingConventions.BACKTICK.identifier))
    )

    @Test
    fun `test doesnt contains backtick should report issue`() {
        val findings = testConventionBacktick.lint(
            """
            @Test
            fun addition_isCorrect() {
                assertEquals(4, 2 + 2)
            }
            """.trimIndent()
        )

        assertThat(findings).hasSize(1)
        assertThat(findings[0].message)
            .isEqualTo("The method addition_isCorrect should be using backtick (ex.: @Test fun `addition is correct`(){})")
    }

    @Test
    fun `test contains backtick find no issues`() {
        val findings = testConventionBacktick.lint(
            """
            @Test
            fun `addition is correct`() {
                assertEquals(4, 2 + 2)
            }
            """.trimIndent()
        )

        assertThat(findings).isEmpty()
    }
}