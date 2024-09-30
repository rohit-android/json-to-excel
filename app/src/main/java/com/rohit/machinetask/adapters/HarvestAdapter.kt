package com.rohit.machinetask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rohit.machinetask.R
import com.rohit.machinetask.model.HarvestData

class HarvestAdapter(private val harvestList: List<HarvestData>) :
    RecyclerView.Adapter<HarvestAdapter.HarvestViewHolder>() {

    class HarvestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val harvestType: TextView = itemView.findViewById(R.id.tvHarvestType)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
        val acres: TextView = itemView.findViewById(R.id.tvAcres)
        val farmName: TextView = itemView.findViewById(R.id.tvFarmName)
        val harvestStarted: TextView = itemView.findViewById(R.id.tvHarvestStarted)
        val tvHarvestCompleted: TextView = itemView.findViewById(R.id.tvHarvestCompleted)
        val googleLink: TextView = itemView.findViewById(R.id.tvGoogleLink)
        val pattern: TextView = itemView.findViewById(R.id.tvPattern)
        val estimated_loads_count: TextView = itemView.findViewById(R.id.estimated_loads_count)
        val harvested_loads: TextView = itemView.findViewById(R.id.harvested_loads)
        val harvesters_used: TextView = itemView.findViewById(R.id.harvesters_used)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HarvestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_harvest, parent, false)
        return HarvestViewHolder(view)
    }

    override fun onBindViewHolder(holder: HarvestViewHolder, position: Int) {
        val currentHarvest = harvestList[position]
        holder.harvestType.text = currentHarvest.field_number.toString()
        holder.farmName.text = currentHarvest.farm_name
        holder.acres.text = "Acres \n " + currentHarvest.acres.toString()
        holder.pattern.text = "Pattern \n " + currentHarvest.pattern
        holder.estimated_loads_count.text = "Est. Loads \n " + currentHarvest.estimated_loads_count
        holder.harvested_loads.text = "Est. Loads \n " + currentHarvest.harvested_loads
        holder.location.text = "Location \n "+currentHarvest.location
        holder.harvesters_used.text = "Harv. Used \n " +currentHarvest.harvesters_used
        holder.harvestStarted.text = "Harv. Started \n " + currentHarvest.harvest_started
        holder.tvHarvestCompleted.text = "Harv. Completed \n " + currentHarvest.harvest_complete
        holder.googleLink.text = "Notes \n " + "Lorem Ipsum is simply dummy text of the printing and typesetting industry"
    }

    override fun getItemCount(): Int {
        return harvestList.size
    }
}
