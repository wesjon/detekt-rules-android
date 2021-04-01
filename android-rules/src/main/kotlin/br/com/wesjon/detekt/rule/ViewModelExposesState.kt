package br.com.wesjon.detekt.rule

import br.com.wesjon.detekt.util.isViewModel
import br.com.wesjon.detekt.util.typeName
import io.gitlab.arturbosch.detekt.api.*
import io.gitlab.arturbosch.detekt.api.internal.valueOrDefaultCommaSeparated
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.isPublic

class ViewModelExposesState(config: Config) : Rule(config) {
    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Maintainability,
        description = "The ViewModel is exposing a mutable state that might be changed by other layers",
        Debt.TWENTY_MINS
    )

    private val customBaseViewModels =
        ruleSetConfig.valueOrDefaultCommaSeparated(KEY_CUSTOM_VIEWMODEL_CLASSES_NAME, emptyList())

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        if (klass.isViewModel(*customBaseViewModels.toTypedArray())) {
            val mutableStateProperties = klass.collectDescendantsOfType<KtProperty>()
                .asSequence()
                .filter { property -> property.isPublic }
                .filter { property -> property.typeName in mutableStateTypes }
                .toList()

            val viewModelName = klass.name
            mutableStateProperties.forEach { property ->
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
        val KEY_CUSTOM_VIEWMODEL_CLASSES_NAME = "customViewModels"
        private val mutableStateTypes = listOf("MutableLiveData", "MutableStateFlow")
    }
}