package br.com.wesjon.detekt.rule

import br.com.wesjon.detekt.util.isTestFunction
import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtNamedFunction

enum class NamingConventions(
    val identifier: String,
    val regex: Regex,
    val errorDescription: String
) {
    BACKTICK(
        identifier = "backtick",
        regex = "`[\\w\\d\\s]+`".toRegex(),
        errorDescription = "The method %s should be using backtick (ex.: @Test fun `addition is correct`(){})"
    ),

    SNAKE_CASE(
        identifier = "snake_case",
        regex = "^[a-z._]+\$".toRegex(),
        errorDescription = "The method %s should be in snake_case (ex.:@Test fun addition_is_correct(){} )"
    ),

    CAMEL_CASE(
        identifier = "camelCase",
        regex = "^(([a-z]+[A-Z]*)+([a-zA-Z])*)\$".toRegex(),
        errorDescription = "The method %s should be in cammelCase (ex.:@Test fun additionIsCorrect(){} )"
    );

    companion object {
        val options = values().map { it.identifier }

        fun getNamingConventionByIdentifier(identifier: String) =
            values().find { it.identifier == identifier }
    }
}

class TestNameShouldFollowNamingConvention(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName, Severity.CodeSmell,
        "Checks whether test names follow the name convention ($optionsText)",
        Debt.FIVE_MINS
    )

    private val selectedNamingConvention = valueOrDefault(CONVENTION_KEY, "")

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        val selectedConvention =
            NamingConventions.getNamingConventionByIdentifier(selectedNamingConvention)
        requireNotNull(selectedConvention) {
            "namingConvention was $selectedNamingConvention and should be set with one of the $optionsText"
        }

        val functionName = function.nameIdentifier?.text
        if (function.isTestFunction() && functionName != null) {
            val isFollowingConvention = functionName.matches(selectedConvention.regex)
            if (!isFollowingConvention) {
                report(
                    CodeSmell(
                        issue,
                        Entity.from(function),
                        selectedConvention.errorDescription.format(functionName)
                    )
                )
            }
        }
    }


    companion object {
        const val CONVENTION_KEY = "namingConvention"
        private val optionsText = "options: ${NamingConventions.options.joinToString()}"
    }
}
