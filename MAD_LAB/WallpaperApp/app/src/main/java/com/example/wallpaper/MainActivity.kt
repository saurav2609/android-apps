package com.example.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView.OnItemSelectedListener
import com.example.wallpaper.R
import android.os.Bundle
import android.content.Intent
import com.example.wallpaper.MainActivity
import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, OnItemSelectedListener,
    CompoundButton.OnCheckedChangeListener {
    private val dropDownOptions = arrayOf("App", "Device")
    private val appWallpapers = intArrayOf(R.drawable.wallpaper1,
        R.drawable.wallpaper2,
        R.drawable.wallpaper3,
        R.drawable.wallpaper4)
    private var selectedImagesTextBox: TextView? = null
    private var selectedWallpaperText: TextView? = null
    private var addImageButton: Button? = null
    private var timeIntervalText: EditText? = null
    private var addedImages // to store selected images
            : MutableList<Uri?>? = null
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedImagesTextBox = findViewById(R.id.imagesListTextView)
        val changeWallpaperButton = findViewById<ToggleButton>(R.id.changeButton)
        addImageButton = findViewById(R.id.addImage)
        timeIntervalText = findViewById(R.id.editTextTime)
        selectedWallpaperText = findViewById(R.id.selectedWallpaperText)
        addedImages = ArrayList()
        handler = Handler()
        val spinner = findViewById<Spinner>(R.id.planets_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dropDownOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        addImageButton.setOnClickListener(this)
        changeWallpaperButton.setOnCheckedChangeListener(this)
    }

    override fun onClick(view: View) {
        selectImage()
    }

    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GET)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            val imageUri = data!!.data
            addedImages!!.add(imageUri)
            val file = File(imageUri!!.path)
            val text = """
                ${file.name}
                
                """.trimIndent()
            selectedImagesTextBox!!.append(text)
            makeToast("$text is selected")
        }
    }

    fun scheduleWallpaperChange(timeDelay: Int) {
        runnable = object : Runnable {
            override fun run() {
                val random = Random()
                val bound =
                    if (addImageButton!!.isEnabled) addedImages!!.size else appWallpapers.size
                val randomInt = random.nextInt(bound)
                changeWallpaperFun(randomInt)
                handler!!.postDelayed(this, (1000 * timeDelay).toLong())
            }
        }
        handler!!.post(runnable as Runnable)
    }

    private fun changeWallpaperFun(imageIndex: Int) {
        val wallpaperManager = WallpaperManager.getInstance(this)
        try {
            val bitmap: Bitmap
            bitmap =
                if (addImageButton!!.isEnabled) // addImageButton is used to indicate type of option selected from the drop down
                {
                    val uri = addedImages!![imageIndex]
                    BitmapFactory.decodeStream(contentResolver.openInputStream(uri!!))
                } else BitmapFactory.decodeResource(resources, appWallpapers[imageIndex])
            wallpaperManager.setBitmap(bitmap)
            selectedWallpaperText!!.visibility = View.VISIBLE
            selectedWallpaperText!!.text = "Randomly selected wallpaper no : " + (imageIndex + 1)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun makeToast(toastMessage: String) {
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        selectedImagesTextBox!!.text = ""
        if (i == 0) { // if App is selected
            addImageButton!!.isEnabled = false
            for (id in appWallpapers) selectedImagesTextBox!!.append("""
    ${getString(id).split("/".toRegex()).toTypedArray()[2]}
    
    """.trimIndent())
        } else {
            addImageButton!!.isEnabled = true
            selectedImagesTextBox!!.text = ""
            addedImages!!.clear()
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    override fun onCheckedChanged(button: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            if (addImageButton!!.isEnabled) {
                if (addedImages!!.size == 0) {
                    makeToast("Select at least one image for wallpaper")
                    button.isChecked = false // to avoid change of button state
                    return
                }
            }
            val time = timeIntervalText!!.text.toString()
            if (time.isEmpty()) {
                makeToast("Please enter the time interval...")
                button.isChecked = false // to avoid change of button state
                return
            }
            scheduleWallpaperChange(time.toInt())
        } else {
            makeToast("Stopped")
            handler!!.removeCallbacks(runnable!!)
        }
    }

    companion object {
        private const val REQUEST_IMAGE_GET = 1
    }
}