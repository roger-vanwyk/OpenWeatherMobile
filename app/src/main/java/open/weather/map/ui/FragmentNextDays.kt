package open.weather.map.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import open.weather.map.model.ModelNextDay
import open.weather.map.network.ApiEndpoint
import org.json.JSONException
import org.json.JSONObject

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class FragmentNextDays : BottomSheetDialogFragment(), LocationListener {

    var lat: Double? = null
    var lng: Double? = null
    var nextDayAdapter: NextDayAdapter? = null
    var rvListWeather: ShimmerRecyclerView? = null
    var fabClose: FloatingActionButton? = null
    var modelNextDays: MutableList<ModelNextDay> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_next_days, container, false)

        nextDayAdapter = NextDayAdapter(activity!!, modelNextDays)
        rvListWeather = rootView.rvListWeather
        rvListWeather?.setLayoutManager(LinearLayoutManager(activity))
        rvListWeather?.setHasFixedSize(true)
        rvListWeather?.setAdapter(nextDayAdapter)
        rvListWeather?.showShimmerAdapter()

        fabClose = rootView.findViewById(R.id.fabClose)
        fabClose?.setOnClickListener({
            dismiss()
        })

        //method get LatLong
        getLatLong()

        return rootView
    }

    @SuppressLint("MissingPermission")
    private fun getLatLong () {
        val locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true)
        val location = locationManager.getLastKnownLocation(provider)
        if (location != null) {
            onLocationChanged(location)
        } else {
            locationManager.requestLocationUpdates(provider, 20000, 0f, this)
        }
    }

    override fun onLocationChanged(location: Location) {
        lng = location.longitude
        lat = location.latitude
        Handler().postDelayed({
            //method get Data Weather
            getListWeather()
        }, 3000)
    }

    private fun getListWeather() {
        AndroidNetworking.get(ApiEndpoint.BASEURL + ApiEndpoint.Daily + "lat=" + lat + "&lon=" + lng + ApiEndpoint.UnitsAppidDaily)
            .setPriority(RenderScript.Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        val jsonArray = response.getJSONArray("list")
                        for (i in 0 until jsonArray.length()) {
                            val dataApi = ModelNextDay()
                            val objectList = jsonArray.getJSONObject(i)
                            val jsonObjectOne = objectList.getJSONObject("temp")
                            val jsonArrayOne = objectList.getJSONArray("weather")
                            val jsonObjectTwo = jsonArrayOne.getJSONObject(0)
                            val longDate = objectList.optLong("dt")
                            val formatDate = SimpleDateFormat("d MMM yy")
                            val readableDate = formatDate.format(Date(longDate * 1000))
                            val longDay = objectList.optLong("dt")
                            val format = SimpleDateFormat("EEEE")
                            val readableDay = format.format(Date(longDay * 1000))

                            dataApi.nameDate = readableDate
                            dataApi.nameDay = readableDay
                            dataApi.descWeather = jsonObjectTwo.getString("description")
                            dataApi.tempMin = jsonObjectOne.getDouble("min")
                            dataApi.tempMax = jsonObjectOne.getDouble("max")
                            modelNextDays.add(dataApi)
                        }
                        nextDayAdapter?.notifyDataSetChanged()
                        rvListWeather?.hideShimmerAdapter()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(activity, "Unable to fetch the weather", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(anError: ANError) {
                    Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
    override fun onProviderEnabled(s: String) {}
    override fun onProviderDisabled(s: String) {}

    companion object {
        fun newInstance(string: String?): FragmentNextDays {
            val fragmentNextDays = FragmentNextDays()
            val args = Bundle()
            args.putString("string", string)
            fragmentNextDays.arguments = args
            return fragmentNextDays
        }
    }
}