package br.com.wesjon.detekt.rule

import br.com.wesjon.detekt.util.isTestFunction
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

        if (function.isTestFunction()) {
            val containsLoop =
                function.bodyExpression?.anyDescendantOfType<KtLoopExpression>() ?: false
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
