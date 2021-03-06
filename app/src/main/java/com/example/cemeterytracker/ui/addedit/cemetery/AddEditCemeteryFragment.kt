package com.example.cemeterytracker.ui.addedit.cemetery

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.cemeterytracker.R
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.databinding.FragmentAddEditCemeteryBinding
import com.example.cemeterytracker.other.Constants
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_edit_cemetery.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddEditCemeteryFragment : Fragment() {

    private lateinit var binding: FragmentAddEditCemeteryBinding
    private val navArgs : AddEditCemeteryFragmentArgs by navArgs()
    private val viewModel: AddEditCemViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences : SharedPreferences


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var  geocoder: Geocoder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_cemetery, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity()) //Use its methods to get location updates
        locationRequest = LocationRequest()    //can also use this to get finer location
        geocoder = Geocoder(requireActivity(), Locale.getDefault()) //gets lat and long into usable address objects

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if(navArgs.editFlag){

        }

        setupListeners()

        viewModel.cemId.observe(viewLifecycleOwner){
            it?.let {
                findNavController().navigate(AddEditCemeteryFragmentDirections.actionAddEditCemeteryFragmentToCemDetailFragment(it))

            }
        }
    }



    private val locationCallback = object : LocationCallback() { //We say what happens when the fusedLocationClient.requestLocationUpdates returns location
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations){
                // Update UI with location data
                // ...
                val addressList: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1) //converts latitude and longitude to an address
                if (addressList != null && addressList.isNotEmpty()) {
                    val address: Address = addressList?.get(0)
                    val sb = StringBuilder()
                    for (i in 0..address.maxAddressLineIndex) {
                        sb.append(address.getAddressLine(i)).append(",")
                    }
                    sb.deleteCharAt(sb.length - 1) //
                    binding.etLocation.setText(sb) //set the text to the adress
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean { //if false take user to location settings
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(locationRequest, //returns a Task object that longitude and lat can be extracted from
            locationCallback,                                           //send a callback that we define ourselves
            Looper.getMainLooper()) //started on a looper thread
    }

    private fun setupListeners() {
        binding.addEditCemToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.addCemFAB.setOnClickListener {
            if (
                binding.nameEt.text.isNullOrEmpty() ||
                binding.etCounty.text.isNullOrEmpty() ||
                binding.etState.text.isNullOrEmpty() ||
                binding.etFirstYear.text.isNullOrEmpty() ||
                binding.etRange.text.isNullOrEmpty() ||
                binding.etLocation.text.isNullOrEmpty() ||
                binding.etTownship.text.isNullOrEmpty() ||
                binding.etSection.text.isNullOrEmpty() ||
                binding.etSpot.text.isNullOrEmpty()
            ) {

                Toast.makeText(requireActivity(), "Please enter all fields", Toast.LENGTH_SHORT)
                    .show()
            }else{

                val newCem = Cemetery(
                    name = binding.nameEt.text.toString(),
                    county = binding.etCounty.text.toString(),
                    state = binding.etState.text.toString(),
                    firstYear = binding.etFirstYear.text.toString(),
                    range = binding.etRange.text.toString(),
                    location = binding.etLocation.text.toString(),
                    township = binding.etTownship.text.toString(),
                    section = binding.etSection.text.toString(),
                    spot = binding.etSpot.text.toString(),
                    addedBy = sharedPreferences.getString(Constants.KEY_LOGGED_IN_USERNAME, Constants.NO_USERNAME).toString(),
                    graveCount = 0,
                        newCemetery = true

                )
                viewModel.insertCemetery(newCem)
            }
        }

        binding.locationEtLayout.setStartIconOnClickListener {
            if (!isLocationEnabled()) {
                Toast.makeText(
                    requireActivity(),
                    "Your location provider is turned off. Please turn it on.",
                    Toast.LENGTH_SHORT
                ).show()

                // This will redirect you to settings from where you need to turn on the location provider.
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            } else {
                // https://www.androdocs.com/kotlin/getting-current-location-latitude-longitude-in-android-using-kotlin.html
                Dexter.withActivity(requireActivity())
                    .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                            if (report!!.areAllPermissionsGranted()) {
                                startLocationUpdates()
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions() //Tell user why they need permissions
                        }
                    }).onSameThread()
                    .check()
            }
        }
    }

    private fun showRationalDialogForPermissions() { //show to the user if their permission are not set
        AlertDialog.Builder(requireActivity())
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}