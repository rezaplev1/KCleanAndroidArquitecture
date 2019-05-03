package com.cristianmg.sqldelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cristianmg.data.repository.CharacterRepository
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val characterRepository: CharacterRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        characterRepository.characters()
            .subscribeOn(Schedulers.io())
            .subscribe({
            Log.d("", "" + it.toString())
        }, {})
    }
}
