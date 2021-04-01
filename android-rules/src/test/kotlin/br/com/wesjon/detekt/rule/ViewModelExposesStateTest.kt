package br.com.wesjon.detekt.rule

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class ViewModelExposesStateTest {
    @Test
    fun `ViewModel exposes mutablestate report issue`() {
        val findings = ViewModelExposesState(Config.empty).lint(
            """
                class LoginViewModel: ViewModel() {
                     val loginViewStateLiveData = MutableLiveData<LoginViewState>()
                     val loginViewStateStateFlow = MutableStateFlow<LoginViewState>()
                }
                """.trimIndent()
        )

        assertThat(findings).hasSize(2)
        assertThat(findings[0].message)
            .isEqualTo("Property loginViewStateLiveData exposes the LoginViewModel state")
        assertThat(findings[1].message)
            .isEqualTo("Property loginViewStateStateFlow exposes the LoginViewModel state")
    }

    @Test
    fun `ViewModel doesnt expose mutable state report no issue`() {
        val findings = ViewModelExposesState(Config.empty).lint(
            """
                class LoginViewModel: ViewModel() {
                     private val loginViewState = MutableLiveData<LoginViewState>()
                     val loginViewState: LiveData<LoginViewState> = _loginViewState
                }
                """.trimIndent()
        )

        assertThat(findings).isEmpty()
    }
}
