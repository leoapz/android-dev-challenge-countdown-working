package com.example.androiddevchallenge.model

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private val _inputTime = MutableLiveData(3)
    val inputTime: LiveData<Int> = _inputTime

    private val _timerIsRunning = MutableLiveData(false)
    val timerIsRunning: LiveData<Boolean> get() = _timerIsRunning

    private val _timerIsFinished = MutableLiveData(false)
    val timerIsFinished: LiveData<Boolean> = _timerIsFinished

    private val timer = object : CountDownTimer(3000, 1) {
        override fun onTick(millisUntilFinished: Long) {
            onTimeRemainingChanged((millisUntilFinished.toInt() / 1000) + 1)
        }

        override fun onFinish() {
            stopTimer()
        }
    }

    fun onInputTimeChange(inputTime: Int) {
        _inputTime.value = inputTime
    }

    fun onTimeRemainingChanged(newTime: Int) {
        _inputTime.value = newTime
    }

    fun startTimer() {
        timer.start()
        _timerIsRunning.value = true
        _timerIsFinished.value = false
    }

    private fun stopTimer() {
        _timerIsRunning.value = false
        _timerIsFinished.value = true
    }

}