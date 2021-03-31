package br.com.wesjon.rule

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtLoopExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.anyDescendantOfType

class NoLoopInTestRule : Rule() {
    override val issue = Issue(
        "NoLoopInTestRule",
        Severity.CodeSmell,
        "O teste não deve conter loops, use testes parametrizados ou acesse posições da lista",
        Debt.TWENTY_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        val isTestMethod = function.annotationEntries.any {
            it.text == "@Test"
        }

        if (isTestMethod) {
            val containsLoop =
                function.bodyExpression
                    ?.anyDescendantOfType<KtLoopExpression>() ?: false
            if (containsLoop) {
                report(
                    CodeSmell(
                        issue,
                        Entity.from(function),
                        "O teste ${function.name} contem loop"
                    )
                )
            }
        }
    }
}
