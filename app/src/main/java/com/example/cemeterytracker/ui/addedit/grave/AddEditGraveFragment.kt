package com.example.cemeterytracker.ui.addedit.grave

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cemeterytracker.R
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.databinding.FragmentAddEditGraveBinding
import com.example.cemeterytracker.other.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddEditGraveFragment : Fragment() {

    private lateinit var binding : FragmentAddEditGraveBinding
    private val viewModel: AddEditGraveViewModel by viewModels()
    private val navArgs : AddEditGraveFragmentArgs by navArgs()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var bornDateListener: DatePickerDialog.OnDateSetListener
    private lateinit var deathDateListener: DatePickerDialog.OnDateSetListener
    private lateinit var marriedDateListener: DatePickerDialog.OnDateSetListener

    private var cal = Calendar.getInstance()
    var year = cal.get(Calendar.YEAR)
    var month = cal.get(Calendar.MONTH)
    var day = cal.get(Calendar.DAY_OF_MONTH)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_grave, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        if(navArgs.editGraveFlag){
            viewModel.setGraveId(navArgs.graveId)
            binding.viewModel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.graveInsertSuccess.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(AddEditGraveFragmentDirections.actionAddEditGraveFragmentToCemDetailFragment(navArgs.cemId))
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        bornDateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val date: String = "$month / $dayOfMonth / $year"
            binding.bornEt.setText(date) //use this instead of .text since edit text expects exitable instead of string
        }

        deathDateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val date: String = "$month / $dayOfMonth / $year"
            binding.deathYearEt.setText(date) //use this instead of .text since edit text expects exitable instead of string
        }

        marriedDateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val date: String = "$month / $dayOfMonth / $year"
            binding.marriageYearEt.setText(date) //use this instead of .text since edit text expects exitable instead of string
        }

        binding.marriageYearInputLayout.setStartIconOnClickListener {
            showDatePicker(marriedDateListener)
        }
        binding.deathYearInputLayout.setStartIconOnClickListener {
            showDatePicker(deathDateListener)
        }
        binding.birthYearInputLayout.setStartIconOnClickListener {
            showDatePicker(bornDateListener)
        }

        binding.createGraveFAB.setOnClickListener {
            if(
                    binding.firstNameEt.text.isNullOrEmpty() ||
                    binding.lastNameet.text.isNullOrEmpty() ||
                    binding.bornEt.text.isNullOrEmpty() ||
                    binding.deathYearEt.text.isNullOrEmpty() ||
                    binding.marriageYearEt.text.isNullOrEmpty() ||
                    binding.commentEt.text.isNullOrEmpty() ||
                    binding.graveNumEt.text.isNullOrEmpty()){
                Toast.makeText(requireActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show()
            }else{
                val grave =
                        Grave(
                                graveId =  if(navArgs.graveId != -1L) navArgs.graveId else 0, //if grave id is null then user is creating a new grave, generate uuid else user is editing existing grave
                                firstName = binding.firstNameEt.text.toString(),
                                lastName = binding.lastNameet.text.toString(),
                                birthDate = binding.bornEt.text.toString(),
                                deathDate = binding.bornEt.text.toString(),
                                marriageYear = binding.marriageYearEt.text.toString(),
                                comment = binding.commentEt.text.toString(),
                                graveNumber = binding.graveNumEt.text.toString(),
                                cemeteryId = navArgs.cemId,
                                addedBy = sharedPreferences.getString(Constants.NO_USERNAME, Constants.NO_USERNAME).toString()
                        )
                viewModel.insertGrave(grave)
            }
        }
    }


    private fun showDatePicker(dateListener : DatePickerDialog.OnDateSetListener) {
        val dialog = DatePickerDialog(requireActivity(), android.R.style.Theme_Holo_Dialog, dateListener, year, month, day)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }









}