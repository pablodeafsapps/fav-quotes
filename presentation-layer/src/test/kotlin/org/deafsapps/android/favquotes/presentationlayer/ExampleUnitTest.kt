package org.deafsapps.android.favquotes.presentationlayer

import org.junit.Assert.assertEquals
import org.junit.Test

private const val AMOUNT1 = 2
private const val AMOUNT2 = 2
private const val RESULT = 4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    /**
     *
     */
    @Test
    fun `check that addition is correct`() {
        val expectedResult = RESULT
        val num1 = AMOUNT1
        val num2 = AMOUNT2
        assertEquals(expectedResult, num1 + num2)
    }

}
