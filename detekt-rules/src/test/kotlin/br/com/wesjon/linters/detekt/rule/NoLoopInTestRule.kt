package br.com.wesjon.linters.detekt.rule

class NoLoopInTestRuleTest {
    private val testWithLoop = """
        class Test {
            @Test
            fun allOddsTest() {
                val list = listOf(2, 4)
                for(num in list){
                    assertTrue(num % 2 == 0)
                }
            }
        }
    """.trimIndent()

    private val testWithNoLoop = """
        class Test {
            @Test
            fun allOddsTest() {
                val list = listOf(2, 4)
                assertTrue(list[0] % 2 == 0)
                assertTrue(list[1] % 2 == 0)
            }
        }
    """.trimIndent()
}