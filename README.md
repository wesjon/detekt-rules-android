![Pre Merge Checks](https://github.com/wesjon/android-linters/workflows/Pre-merge%20checks/badge.svg)

# Detekt rules for Android

This repository contains an opinionated set of Detekt Rules for Android projects


## Getting started

#### 1. Add this library declaration into your detekt dependencies

```groovy
detekt {
    // ...
}

dependencies {
    detektPlugins "br.com.wesjon:detekt-android-rules:0.1" // Add it here
    detektPlugins "io.gitlab.arturbosch.detekt:detekt-formatting:$detekt_version"
}
```

#### 2. Include a new block in your `detekt.yml` file

You may want to activate or deactivate rules according to your project needs.

```yml
android-rules:
  TestNameShouldFollowNamingConvention:
    active: true
    namingConvention: 'backtick' // backtick, snake_case or camelCase
  ViewModelExposesState:
    active: true
```

## Rules

### TestNameShouldFollowNamingConvention

Checks whether test names follow the name convention. This rule only applies if method is annotated with `@Test`

#### NON-compliant code

CONFIG: backtick
```kotlin
@Test
fun additionIsCorrect(){
    // ...
}
```
#### Compliant code

**CONFIG: backtick**
```kotlin
@Test
fun `addition is correct`(){
    // ...
}
```

**CONFIG: snake_case**
```kotlin
@Test
fun addition_is_correct(){
    // ...
}
```

**CONFIG: camelCase**
```kotlin
@Test
fun additionIsCorrect(){
    // ...
}
```

### ViewModelExposesState

MVVM has popularized the use of ViewModels in Android apps. Exposing a MutableLiveData to the View layer is an antipattern and should be avoided when using ViewModels.

#### Non-compliant code

```kotlin
class LoginViewModel: ViewModel() {
     val loginViewState = MutableLiveData<LoginViewState>()
}
```

#### Compliant code
```kotlin
class LoginViewModel: ViewModel() {
     private val loginViewState = MutableLiveData<LoginViewState>()
     val loginViewState: LiveData<LoginViewState> = _loginViewState
}
```