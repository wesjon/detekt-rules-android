package br.com.wesjon.linters.detekt.rule

class FailOnEveryMethodRuleTest {
    private val codeWithAMethod = """
        fun aMethod() { }
    """.trimIndent()

    private val codeWithoutMethods = """
        class User()
    """.trimIndent()
}