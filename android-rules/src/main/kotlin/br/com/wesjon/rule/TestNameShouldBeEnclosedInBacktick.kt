package br.com.wesjon.rule

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtNamedFunction

class TestNameShouldBeEnclosedInBacktick : Rule() {
    override val issue = Issue(
        "TestNameShouldBeEnclosedInBacktick",
        Severity.CodeSmell,
        "Verifica se o nome do teste segue a convencao do projeto, e inclui backtick",
        Debt.FIVE_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        val isTestMethod = function.annotationEntries.any {
            it.text == "@Test"
        }

        if (isTestMethod) {
            val methodNameContainsBacktick = function.nameIdentifier?.textContains('`') == true
            if (!methodNameContainsBacktick) {
                report(
                    CodeSmell(
                        issue, Entity.from(function),
                        "O nome do teste precisa conter backtick"
                    )
                )
            }
        }
    }
}
