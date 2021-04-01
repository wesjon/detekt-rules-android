package br.com.wesjon.detekt.util

import br.com.wesjon.detekt.Tokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.referenceExpression

val KtProperty?.typeName: String?
    get() {
        val referencedName = (this?.typeReference?.typeElement as? KtUserType)?.referencedName
        return if (referencedName != null) {
            referencedName
        } else {
            (this?.initializer as KtCallExpression).referenceExpression()?.text
        }
    }

fun KtNamedFunction.isTestFunction() = annotationEntries.any { it.text == Tokens.TEST_ANNOTATION }

fun KtClass?.isViewModel(vararg viewModelAlternativeNames: String): Boolean {
    val instance = this

    return if (instance == null) {
        false
    } else {
        val inheritsViewModel = instance.superTypeListEntries.any { superClass ->
            val superClassName = superClass.typeReference?.text
            superClassName == Tokens.VIEW_MODEL_NAME || superClassName in viewModelAlternativeNames
        }
        inheritsViewModel
    }
}