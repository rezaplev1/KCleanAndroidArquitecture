package com.cristianmg.data

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.squareup.sqldelight.android.AndroidSqliteDriver
import junit.framework.Assert.assertEquals
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
        val appContext = InstrumentationRegistry.getTargetContext()
        val database = Database(AndroidSqliteDriver(Database.Schema, appContext, "test.db"))
        //val characters: List<com.cristianmg.data.Character> = database.characterQueries.readAll().executeAsList()
        assertEquals("com.cristianmg.sqldelight", appContext.packageName)
    }


}
