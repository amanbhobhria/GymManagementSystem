package com.example.gymmanagementsystem

import android.app.DatePickerDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddNewMemberActivity : AppCompatActivity() {
    private lateinit var dateEditText: EditText
    private var selectedDate: Calendar = Calendar.getInstance()
    lateinit var addMemberBtn: Button

    lateinit var nameEdtTxt:EditText
    lateinit var phoneEdtTxt:EditText
    lateinit var gymPlanSpinner:Spinner

    lateinit var fstream: FileOutputStream

    private val fileName="GymData.txt"
    private val filePath="MyFileStorage"
    lateinit var myExternalFile:File



    var mPermission= arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_member)
        initialise()
        addMem()
    }

    private fun initialise() {
        dateEditText = findViewById(R.id.edit_text_date)
        nameEdtTxt = findViewById(R.id.edit_name_member)
        phoneEdtTxt = findViewById(R.id.edit_text_phone_no)
        gymPlanSpinner = findViewById(R.id.planSpinner)
        addMemberBtn = findViewById(R.id.button_add_member)
        val datePickerButton = findViewById<ImageButton>(R.id.image_button_date_picker)
        datePickerButton.setOnClickListener { showDatePicker() }



        dateEditText.setOnClickListener { showDatePicker() }
    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->

                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateEditText()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateEditText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateEditText.setText(dateFormat.format(selectedDate.time))
    }

    private fun addMem()
    {
addMemberBtn.setOnClickListener()
{
    val username="""
            ${nameEdtTxt.text}
           """.trimIndent()
    val phoneNo=phoneEdtTxt.text.toString()
    val startDate=dateEditText.text.toString()


    try{

        if(ContextCompat.checkSelfPermission(this,mPermission[0])!=
            PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,mPermission[1] )!=
            PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(this,mPermission,23)
        }
        else
        {
            if(username.isEmpty() || phoneNo.isEmpty()||startDate.isEmpty())
                Toast.makeText(this,"either of field is empty",Toast.LENGTH_SHORT).show()

            else
            {
                myExternalFile= File(getExternalFilesDir(filePath),fileName)
                fstream= FileOutputStream(myExternalFile)
                fstream.write(username.toByteArray())
                fstream.write(phoneNo.toByteArray())
                fstream.write(startDate.toByteArray())
                fstream.close()
                Toast.makeText(this, "Member Added successfully", Toast.LENGTH_SHORT).show()
                finish()

            }

        }

    }
    catch (e:Exception)
    {
        Toast.makeText(this,"Exception in : "+e.toString(),Toast.LENGTH_SHORT).show()
    }




}
    }
}