package com.moas.unrest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import android.view.View
import android.widget.EditText
import io.ktor.request.receiveText
import io.ktor.request.uri


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<View>(R.id.button)
        button.setOnClickListener {
            val portText = (findViewById<View>(R.id.editText)) as EditText
            val port = portText.text.toString().toInt()
            embeddedServer(Netty, port) {
                routing {
                    var url = "/*"
                    val depthText = (findViewById<View>(R.id.editText2)) as EditText
                    val depth = depthText.text.toString().toInt()
                    val resultText = (findViewById<View>(R.id.editText3)) as EditText
                    get("/"){
                        runOnUiThread {
                            resultText.setText(resultText.text.toString()+"\nGET "+ call.request.uri)
                        }
                        call.respondText("OK")
                    }
                    for (i in 0..depth){
                        get(url) {
                            runOnUiThread {
                                resultText.setText(resultText.text.toString()+"\nGET "+ call.request.uri)
                            }
                            call.respondText("OK")
                        }
                        post(url) {
                            val text = call.receiveText()
                            runOnUiThread {
                                resultText.setText(resultText.text.toString()+"\nPOST "+ call.request.uri + " " + text)
                            }
                            call.respondText("OK")
                        }
                        url += "/*"
                    }
                }
            }.start(wait = false)
        }

    }
}
