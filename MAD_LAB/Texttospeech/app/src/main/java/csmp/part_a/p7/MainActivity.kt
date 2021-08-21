package csmp.part_a.p7

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var editText: EditText? = null
    private var textToSpeech: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        val button = findViewById<Button>(R.id.convertButton)
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) textToSpeech!!.language = Locale.US
        }
        button.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val text = editText!!.text.toString()
        if (text == "") makeToast("EMPTY") else textToSpeech!!.speak(text,
            TextToSpeech.QUEUE_FLUSH,
            null)
    }

    private fun makeToast(toastMessage: String) {
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
    }
}