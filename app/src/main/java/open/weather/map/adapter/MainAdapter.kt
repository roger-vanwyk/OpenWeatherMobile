package open.weather.map.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import open.weather.map.R
import open.weather.map.model.ModelMain
import java.util.*

val View.iconTemp: LottieAnimationView
    get() {
        TODO("Not yet implemented")
    }
private val View.tvTempMax: TextView
    get() {
        TODO("Not yet implemented")
    }
private val View.tvTempMin: TextView
    get() {
        TODO("Not yet implemented")
    }
private val View.tvTemp: TextView
    get() {
        TODO("Not yet implemented")
    }
val View.tvNameDay: TextView
    get() {
        TODO("Not yet implemented")
    }
private val View.cvListWeather: CardView
    get() {
        TODO("Not yet implemented")
    }

class MainAdapter(private val items: List<ModelMain>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        val generator = ColorGenerator.MATERIAL

        // generate random color
        val color = generator.randomColor
        holder.cvListWeather.setCardBackgroundColor(color)

        holder.tvNameDay.text = data.timeNow
        holder.tvTemp.text = String.format(Locale.getDefault(), "%.0f°C", data.currentTemp)
        holder.tvTempMin.text = String.format(Locale.getDefault(), "%.0f°C", data.tempMin)
        holder.tvTempMax.text = String.format(Locale.getDefault(), "%.0f°C", data.tempMax)

        if (data.descWeather == "broken clouds"){
            holder.iconTemp.setAnimation(R.raw.cloudy_forest)
        } else if(data.descWeather == "light rain") {
            holder.iconTemp.setAnimation(R.raw.rainy_forest)
        } else if(data.descWeather == "overcast clouds"){
            holder.iconTemp.setAnimation(R.raw.cloudy_forest)
        } else if (data.descWeather == "moderate rain"){
            holder.iconTemp.setAnimation(R.raw.rainy_forest)
        } else if(data.descWeather == "few clouds"){
            holder.iconTemp.setAnimation(R.raw.sunny_forest)
        } else if(data.descWeather == "heavy intensity rain"){
            holder.iconTemp.setAnimation(R.raw.rainy_forest)
        } else if(data.descWeather == "sky clear"){
            holder.iconTemp.setAnimation(R.raw.sunny_forest)
        } else {
            holder.iconTemp.setAnimation(R.raw.unknown)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvListWeather: CardView
        var tvNameDay: TextView
        var tvTemp: TextView
        var tvTempMin: TextView
        var tvTempMax: TextView
        var iconTemp: LottieAnimationView

        init {
            cvListWeather = itemView.cvListWeather
            tvNameDay = itemView.tvNameDay
            tvTemp = itemView.tvTemp
            tvTempMin = itemView.tvTempMin
            tvTempMax = itemView.tvTempMax
            iconTemp = itemView.iconTemp
        }
    }
}

private fun CardView.setCardBackgroundColor(color: Any) {
    TODO("Not yet implemented")
}

class ColorGenerator {
    object MATERIAL {

        val randomColor: Any
            get() {
                TODO()
            }
    }

}
