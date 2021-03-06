package open.weather.map.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import open.weather.map.R
import open.weather.map.model.ModelNextDay
import java.util.*

private val View.tvMinTemp: TextView
    get() {
        TODO("Not yet implemented")
    }
private val View.tvMaxTemp: TextView
    get() {
        TODO("Not yet implemented")
    }
private val View.tvDate: TextView
    get() {
        TODO("Not yet implemented")
    }
private val View.cvListNextDays: CardView
    get() {
        TODO("Not yet implemented")
    }

class NextDayAdapter(
    private val mContext: Context,
    private val items: List<ModelNextDay>
) : RecyclerView.Adapter<NextDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_next_days, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]

        holder.tvNameDay.text = data.nameDay
        holder.tvDate.text = data.nameDate
        holder.tvMinTemp.text = String.format(Locale.getDefault(), "%.0f°C", data.tempMin)
        holder.tvMaxTemp.text = String.format(Locale.getDefault(), "%.0f°C", data.tempMax)

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
        var cvListNextDays: CardView
        var tvNameDay: TextView
        var tvDate: TextView
        var tvMaxTemp: TextView
        var tvMinTemp: TextView
        var iconTemp: LottieAnimationView

        init {
            cvListNextDays = itemView.cvListNextDays
            tvNameDay = itemView.tvNameDay
            tvDate = itemView.tvDate
            tvMaxTemp = itemView.tvMaxTemp
            tvMinTemp = itemView.tvMinTemp
            iconTemp = itemView.iconTemp
        }
    }
}