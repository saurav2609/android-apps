package csmp.part_a.p6.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import csmp.part_a.p6.R
import csmp.part_a.p6.classes.City
import csmp.part_a.p6.helper.XmlPullParserHandler
import java.io.IOException

class ParsedXml : AppCompatActivity() {
    var xmlListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parsed_xml)
        xmlListView = findViewById<View>(R.id.xmlListView) as ListView
        var cityList: List<City>? = null
        try {
            val xmlPullParserHandler = XmlPullParserHandler() //a custom class to parse XML
            val inputStream = assets.open("city.xml")
            cityList = xmlPullParserHandler.parseXMLData(inputStream)
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cityList)
            xmlListView!!.adapter = arrayAdapter
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}