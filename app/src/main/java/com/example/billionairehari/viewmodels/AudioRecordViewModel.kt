package com.example.billionairehari.viewmodels

import android.app.Application
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.Buffer
import javax.inject.Inject
import kotlin.time.TimeSource

enum class State {
    PLAYING,
    PAUSE,
    RESUME,
    STOP
}

class AudioRecordViewModel constructor(
    private val audio_source:Int = MediaRecorder.AudioSource.MIC,
    private val sample_rate:Int = 44100,
    private val channel:Int = AudioFormat.CHANNEL_IN_MONO,
    private val format:Int = AudioFormat.ENCODING_PCM_16BIT
) : ViewModel() {

    val buffer_size = AudioRecord.getMinBufferSize(sample_rate,channel,format)

    private var audioRecord:AudioRecord? = null
    @Volatile private var isRecording = false
    @Volatile private var isPaused = false

    private val _state = MutableStateFlow<State>(State.STOP)
    val state = _state.asStateFlow()

    val recordedData = mutableListOf<Short>()

    fun startRecording(){
        try {
            audioRecord = AudioRecord(
                audio_source,
                sample_rate,
                channel,
                format,
                buffer_size
            )
            audioRecord?.startRecording()
            isRecording = true
            isPaused = false
            _state.value = State.PLAYING
            Thread {
                val buffer = ShortArray(buffer_size)
                if(isRecording){
                    if(!isPaused){
                        val read = audioRecord?.read(buffer,0,buffer.size) ?: 0
                        Log.d("AudioRecordViewModel",read.toString())
                        if(read > 0){
                            recordedData.addAll(buffer.take(read))
                        }
                    }else {
//                    Thread.sleep(24)
                    }
                }
            }.start()
        }catch(error: Exception){
            Log.e("Billionaire",error.toString())
        }
    }

    fun pause(){
        if(!isPaused && (_state.value == State.PLAYING || _state.value == State.RESUME)){
            isPaused = true
            _state.value = State.PAUSE
        }
    }
    fun resume(){
        if(isPaused && _state.value == State.PAUSE){
            isPaused = false
            _state.value = State.RESUME
        }
    }

    fun stop(){
        try {
            if(_state.value == State.RESUME || _state.value == State.PLAYING){
                isRecording = false
                audioRecord?.stop()
                audioRecord?.release()
                audioRecord = null
                _state.value = State.STOP
            }else{
                throw Exception("The current state is ${_state.value} so stop will not work")
            }
        }catch(error:Exception){
            Log.e("Audio View Model Error",error.toString())
        }
    }
}