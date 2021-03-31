package br.com.wesjon.detekt.util

import br.com.wesjon.detekt.Tokens
import org.jetbrains.kotlin.psi.KtNamedFunction

fun KtNamedFunction.isTestFunction() = annotationEntries.any { it.text == Tokens.TEST_ANNOTATION }