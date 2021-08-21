package csmp.part_a.p6.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import csmp.part_a.p6.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var parseXmlButton: Button? = null
    var parseJsonButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        parseJsonButton = findViewById<View>(R.id.parseJsonButton) as Button
        parseXmlButton = findViewById<View>(R.id.parseXmlButton) as Button
        parseJsonButton!!.setOnClickListener(this)
        parseXmlButton!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.parseXmlButton -> {
                val xmlIntent = Intent(this@MainActivity, ParsedXml::class.java)
                startActivity(xmlIntent)
            }
            R.id.parseJsonButton -> {
                val jsonIntent = Intent(this@MainActivity, ParsedJson::class.java)
                startActivity(jsonIntent)
            }
        }
    }
}