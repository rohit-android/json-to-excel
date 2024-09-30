package com.rohit.machinetask.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohit.machinetask.R
import com.rohit.machinetask.adapters.HarvestAdapter
import com.rohit.machinetask.model.HarvestData
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.OutputStream

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var harvestAdapter: HarvestAdapter
    private lateinit var harvestList: MutableList<HarvestData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Parse JSON data
        harvestList = parseJson()

        // Set adapter
        harvestAdapter = HarvestAdapter(harvestList)
        recyclerView.adapter = harvestAdapter

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Set the title
        supportActionBar?.title = "Dashboard"
    }

    private fun parseJson(): MutableList<HarvestData> {
        val jsonString = loadJsonFromAsset("listing_response.json")
        val harvestList = mutableListOf<HarvestData>()

        try {
            val jsonObject = JSONObject(jsonString)
            val dataObject = jsonObject.getJSONObject("data")
            val harvestArray = dataObject.getJSONArray("all_harvest_data")

            for (i in 0 until harvestArray.length()) {
                val harvestObject = harvestArray.getJSONObject(i)
                val harvestData = HarvestData(
                    id = harvestObject.getInt("id"),
                    harvest_type = harvestObject.getString("harvest_type"),
                    field_number = harvestObject.getInt("field_number"),
                    location = harvestObject.getString("location"),
                    acres = harvestObject.getDouble("Acres"),
                    farm_name = harvestObject.getString("farm_name"),
                    harvest_started = harvestObject.getString("harvest_started"),
                    harvest_complete = harvestObject.getString("harvest_complete"),
                    google_link = harvestObject.getString("google_link"),
                    pattern = harvestObject.getString("pattern"),
                    estimated_loads_count = harvestObject.getString("estimated_loads_count"),
                    harvested_loads = harvestObject.getString("harvested_loads"),
                    harvesters_used = harvestObject.getString("harvesters_used"),
                )
                harvestList.add(harvestData)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return harvestList
    }

    private fun loadJsonFromAsset(fileName: String): String {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_export -> {
                // Call the export function
                exportToExcel()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun exportToExcel() {
        // Choose the path to save
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_TITLE, "harvest_data.xlsx")
        }
        createDocumentLauncher.launch(intent)
    }

    private val createDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.also { uri ->
                try {
                    val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                    val harvestList = parseJson() // Parse the JSON to get the data

                    // Create an Excel workbook and sheet
                    val workbook: Workbook = XSSFWorkbook()
                    val sheet = workbook.createSheet("Harvest Data")

                    // Create the header row
                    val headerRow = sheet.createRow(0)
                    headerRow.createCell(0).setCellValue("ID")
                    headerRow.createCell(1).setCellValue("Harvest Type")
                    headerRow.createCell(2).setCellValue("Location")
                    headerRow.createCell(3).setCellValue("Acres")
                    headerRow.createCell(4).setCellValue("Farm Name")
                    headerRow.createCell(5).setCellValue("Harvest Started")
                    headerRow.createCell(6).setCellValue("Harvest Completed")
                    headerRow.createCell(7).setCellValue("Google Link")

                    // Populate the data rows
                    for ((index, harvest) in harvestList.withIndex()) {
                        val row = sheet.createRow(index + 1)
                        row.createCell(0).setCellValue(harvest.id.toString())
                        row.createCell(1).setCellValue(harvest.field_number.toString())
                        row.createCell(2).setCellValue(harvest.location)
                        row.createCell(3).setCellValue(harvest.acres.toString())
                        row.createCell(4).setCellValue(harvest.farm_name)
                        row.createCell(5).setCellValue(harvest.harvest_started)
                        row.createCell(6).setCellValue(harvest.harvest_complete)
                        row.createCell(7).setCellValue(harvest.google_link)
                    }
                    // Write workbook to the OutputStream
                    outputStream?.use {
                        workbook.write(it)
                        workbook.close()  // Close the workbook
                        Toast.makeText(this, "Excel file saved successfully!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to save Excel file.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}
