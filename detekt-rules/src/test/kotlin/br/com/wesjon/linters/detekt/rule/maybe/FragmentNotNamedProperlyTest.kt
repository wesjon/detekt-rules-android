package br.com.wesjon.linters.detekt.rule.maybe

class FragmentNotNamedProperlyTest {
    private val fragmentNotNamedProperly = """
        XptoView: Fragment()
    """.trimIndent()

    private val fragmentNamedCorrectly = """
        XptoFragment: Fragment()
    """.trimIndent()
}