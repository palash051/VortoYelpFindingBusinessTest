package com.jbhuiyan.projects.vortoyelptest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        var appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jbhuiyan.projects.vortoyelptest", appContext.packageName)
    }
}