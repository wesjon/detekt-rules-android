package br.com.wesjon.linters.detekt.rule

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtNamedFunction

class FailOnEveryMethodRule : Rule() {
    override val issue = Issue(
        "FailOnEveryMethodRule",
        Severity.CodeSmell,
        "Falha em todos os métodos",
        Debt.FIVE_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        report(
            CodeSmell(
                issue, Entity.from(function),
                ""
            )
        )
    }
}