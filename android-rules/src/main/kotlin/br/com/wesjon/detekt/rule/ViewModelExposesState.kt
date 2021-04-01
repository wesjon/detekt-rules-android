package br.com.wesjon.detekt.rule

import br.com.wesjon.detekt.Tokens
import br.com.wesjon.detekt.util.isViewModel
import br.com.wesjon.detekt.util.typeName
import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.containingClass
import org.jetbrains.kotlin.psi.psiUtil.isPublic

class ViewModelExposesState(config: Config) : Rule(config) {
    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Maintainability,
        description = "The ViewModel is exposing a mutable state that might be changed by other layers",
        Debt.TWENTY_MINS
    )

    override fun visitProperty(property: KtProperty) {
        super.visitProperty(property)

        if (property.isPublic && property.containingClass().isViewModel()) {
            val isMutableStateProperty = property.typeName in mutableStateTypes
            if (isMutableStateProperty) {
                val viewModelName = property.containingClass()?.name ?: Tokens.VIEW_MODEL_NAME
                report(
                    CodeSmell(
                        issue,
                        Entity.from(property),
                        "Property ${property.name} exposes the $viewModelName state"
                    )
                )
            }
        }
    }

    companion object {
        private val mutableStateTypes = listOf("MutableLiveData", "MutableStateFlow")
    }
}