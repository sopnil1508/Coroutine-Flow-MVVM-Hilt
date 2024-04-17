package com.sopnilshinde.kotlinflowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var channel = Channel<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        producer()
//        consumer()

        GlobalScope.launch (Dispatchers.Main){

            flowProducer()
                .onStart {
                    Log.d("Test" , "Flow started")
                }
                .onCompletion {
                    Log.d("Test" , "FLow completed")
                }
                .onEach {
                    Log.d("Test" , "on every item from flow")
                }
                .collect{
                Log.d("Test" , it.toString())
            }

        }
    }

    fun producer(){
        CoroutineScope(Dispatchers.Main).launch{
            channel.send(1)
            channel.send(2)
        }
    }
    fun consumer(){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("Test" , channel.receive().toString())
            Log.d("Test" , channel.receive().toString())
        }
    }

    fun flowProducer() : Flow<Int>{
        return  flow {
            val list = listOf<Int>(1 , 2, 3 ,4 ,5)
            list.forEach {
                delay(2000)
                emit(it)
            }
        }
    }
}