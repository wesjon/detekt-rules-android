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
    );

    companion object {
        val options = values().map { it.identifier }
    }
}

class TestNameShouldFollowNamingConvention(config: Config) : Rule(config) {

    private val optionsText = "options: ${NamingConventions.options.joinToString()}"
    override val issue = Issue(
        javaClass.simpleName, Severity.CodeSmell,
        "Checks whether test names follow the name convention ($optionsText)",
        Debt.FIVE_MINS
    )

    private val selectedNamingConvention = valueOrDefault(CONVENTION_KEY, "")

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        val functionName = function.nameIdentifier?.text

        if (function.isTestFunction() && functionName != null) {
            if (selectedNamingConvention !in NamingConventions.options) {
                throw IllegalArgumentException(
                    "namingConvention was $selectedNamingConvention and should be set with one of the $optionsText"
                )
            }

            val error = NamingConventions.values().find {
                !functionName.contains(it.regex)
            }

            if (error != null) {
                report(
                    CodeSmell(
                        issue,
                        Entity.from(function),
                        error.errorDescription.format(functionName)
                    )
                )
            }
        }
    }


    companion object {
        const val CONVENTION_KEY = "namingConvention"
    }
}
