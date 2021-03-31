package br.com.wesjon.detekt

import br.com.wesjon.detekt.rule.TestNameShouldFollowNamingConvention
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class AndroidDetektRulesProvider : RuleSetProvider {
    override val ruleSetId: String = "android-rules"

    override fun instance(config: Config) = RuleSet(
        ruleSetId, listOf(TestNameShouldFollowNamingConvention(config))
    )
}