package csmp.part_a.p6.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import csmp.part_a.p6.R
import csmp.part_a.p6.classes.City
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class ParsedJson : AppCompatActivity() {
    var jsonListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parsed_json)
        jsonListView = findViewById<View>(R.id.jsonListView) as ListView
        val cityList: MutableList<City> = ArrayList()
        try {
            // get JSONObject from JSON file
            val obj = JSONObject(loadJSONFromAsset())
            // fetch JSONObject named cities
            val cityArray = obj.getJSONArray("cities")
            for (i in 0 until cityArray.length()) {
                val cityObject = cityArray.getJSONObject(i)
                val city = City()
                city.setCityName(cityObject.getString("City_name"))
                city.setLatitude(cityObject.getDouble("Latitude"))
                city.setLongitude(cityObject.getDouble("Longitude"))
                city.setTemperature(cityObject.getDouble("Temperature"))
                city.setHumidity(cityObject.getString("Humidity"))
                cityList.add(city)
            }
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cityList)
            jsonListView!!.adapter = arrayAdapter
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun loadJSONFromAsset(): String? {
        val jsonString: String
        jsonString = try {
            val inputStream = assets.open("city.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return jsonString
    }
}