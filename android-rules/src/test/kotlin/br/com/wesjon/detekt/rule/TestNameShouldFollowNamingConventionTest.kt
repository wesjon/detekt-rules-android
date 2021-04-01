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
    private val testConventionSnakeCase = TestNameShouldFollowNamingConvention(
        TestConfig(mapOf(CONVENTION_KEY to NamingConventions.SNAKE_CASE.identifier))
    )
    private val testConventionCammelCase = TestNameShouldFollowNamingConvention(
        TestConfig(mapOf(CONVENTION_KEY to NamingConventions.CAMEL_CASE.identifier))
    )

    @Test
    fun `backtick config test doesnt contains backtick should report issue`() {
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
    fun `backtick test contains backtick find no issues`() {
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

    @Test
    fun `snake_case test doesnt contains snake_case report issue`() {
        val findings = testConventionSnakeCase.lint(
            """
            @Test
            fun additionIsCorrect() {
                assertEquals(4, 2 + 2)
            }
            """.trimIndent()
        )

        assertThat(findings).hasSize(1)
        assertThat(findings[0].message)
            .isEqualTo("The method additionIsCorrect should be in snake_case (ex.:@Test fun addition_is_correct(){} )")
    }

    @Test
    fun `snake_case test contains snake_case no issues`() {
        val findings = testConventionSnakeCase.lint(
            """
            @Test
            fun addition_is_correct() {
                assertEquals(4, 2 + 2)
            }
            """.trimIndent()
        )

        assertThat(findings).isEmpty()
    }

    @Test
    fun `cammelCase test doesnt contains cammelCase report issue`() {
        val findings = testConventionCammelCase.lint(
            """
            @Test
            fun addition_is_correct() {
                assertEquals(4, 2 + 2)
            }
            """.trimIndent()
        )

        assertThat(findings).hasSize(1)
        assertThat(findings[0].message)
            .isEqualTo("The method addition_is_correct should be in cammelCase (ex.:@Test fun additionIsCorrect(){} )")
    }

    @Test
    fun `cammelCase test contains cammelCase no issues`() {
        val findings = testConventionCammelCase.lint(
            """
            @Test
            fun additionIsCorrect() {
                assertEquals(4, 2 + 2)
            }
            """.trimIndent()
        )

        assertThat(findings).isEmpty()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `rule config contains invalid namingConvention throw exception`() {
        TestNameShouldFollowNamingConvention(
            TestConfig(mapOf(CONVENTION_KEY to "invalid"))
        ).lint("@Test fun test(){}")
    }
}