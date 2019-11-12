package com.abhi.deliverylist.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.abhi.deliverylist.R
import com.abhi.deliverylist.databinding.ActivityDetailBinding
import com.abhi.deliverylist.di.Injectable
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection

class DetailActivity : AppCompatActivity(), OnMapReadyCallback,Injectable {

    private lateinit var mMap: GoogleMap
    private lateinit var deliveryItem: DeliveryItem

    lateinit var detailBinding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val actionbar = supportActionBar

        actionbar!!.setDisplayHomeAsUpEnabled(true)

        deliveryItem= intent.getParcelableExtra("delivery_item")

        detailBinding.detailTvDescription.text= deliveryItem.description
        if(!deliveryItem!!.imageUrl!!.isEmpty())
        Picasso.get().load(deliveryItem.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(detailBinding.detailIvDelivery)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val location = LatLng(deliveryItem.lat, deliveryItem.lng)
        mMap.addMarker(MarkerOptions().position(location).title(deliveryItem.address))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
            12.0f));
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
