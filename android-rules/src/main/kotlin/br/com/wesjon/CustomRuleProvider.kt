package br.com.wesjon

import br.com.wesjon.rule.TestNameShouldBeEnclosedInBacktick
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class CustomRuleProvider : RuleSetProvider {
    override val ruleSetId: String = "custom-rules"

    override fun instance(config: Config) = RuleSet(
        ruleSetId, listOf(TestNameShouldBeEnclosedInBacktick())
    )
}