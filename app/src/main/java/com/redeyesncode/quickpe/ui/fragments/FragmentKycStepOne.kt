package com.redeyesncode.quickpe.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.pay2kart.base.BaseFragment
import com.redeyesncode.quickpe.data.BodyUpdateKyc
import com.redeyesncode.quickpe.data.ResponseKycDetails
import com.redeyesncode.quickpe.data.ResponseLoginUser
import com.redeyesncode.quickpe.databinding.FragmentKycStepOneBinding
import com.redeyesncode.quickpe.repository.DefaultMainRepo
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import com.redeyesncode.quickpe.retrofit.RetrofitInstance
import com.redeyesncode.quickpe.ui.viewmodels.MainViewModel
import com.redeyesncode.redbet.session.AppSession
import com.redeyesncode.redbet.session.Constant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentKycStepOne.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKycStepOne : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: MainViewModel
    lateinit var binding:FragmentKycStepOneBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKycStepOneBinding.inflate(layoutInflater,container,false)

        initClicks()
        setupViewModel()
        attachObservers()
        initialApiCall()

        return binding.root
    }

    private fun initialApiCall() {

        val user = AppSession(fragmentContext).getObject(Constant.USER_LOGIN,ResponseLoginUser::class.java) as ResponseLoginUser

        val map = hashMapOf<String,String>()
        map.put("user_id",user.data?.userId.toString())
        viewModel.getKycDetails(map)



    }

    private fun attachObservers() {

        viewModel.getKycDetailsResponse.observe(viewLifecycleOwner,Event.EventObserver(

            onLoading = {
                showLoadingDialog()
            }, onError = {
                dismissLoadingDialog()
                showToast(it)
            }, onSuccess = {
                dismissLoadingDialog()

                AppSession(fragmentContext).putObject(Constant.BODY_UPDATE_KYC,BodyUpdateKyc())
                showTopSnackBar("Session Initialized",binding.root)
                updateUi(it.data!!)

            }

        ))
        viewModel.submitKycRequestResponse.observe(viewLifecycleOwner,Event.EventObserver(

            onLoading = {
                showLoadingDialog()
            }, onError = {
                dismissLoadingDialog()
                showToast(it)
            }, onSuccess = {
                dismissLoadingDialog()
                showToast(it.message.toString())
            }

        ))
    }

    private fun updateUi(data: ResponseKycDetails.Data) {
        val user = AppSession(fragmentContext).getObject(
            Constant.USER_LOGIN,
            ResponseLoginUser::class.java) as ResponseLoginUser

        binding.edtFirstName.setText(user.data?.name.toString())
        binding.edtMidName.setText(user.data?.name.toString())
        binding.edtDateOfBirth.setText(data.dateOfBirth.toString())
        binding.edtEmail.setText(user.data?.email.toString())
        binding.edtGender.setText(user.data?.gender.toString())

        binding.edtSalary.setText(data?.monthlySalary.toString())
        binding.edtRelativeName.setText(data?.relativeName.toString())
        binding.edRelativeNumber.setText(data?.relativeNumber.toString())
        binding.edtCurrentAddress.setText(data?.permanentAddress.toString())
        binding.edtPermanentAddress.setText(data?.currentAddress.toString())

        binding.edtOccupation.setText("None")
        binding.edtPincode.setText("None")

        val bodyUpdateKyc = BodyUpdateKyc()

        bodyUpdateKyc.userId = user.data?.userId?.toInt()
        bodyUpdateKyc.kycRequest?.currentAddress = binding.edtCurrentAddress.text.toString()
        bodyUpdateKyc.kycRequest?.permanentAddress = binding.edtPermanentAddress.text.toString()
        bodyUpdateKyc.kycRequest?.monthlySalary = binding.edtSalary.text.toString()
        bodyUpdateKyc.kycRequest?.relativeName = binding.edtRelativeName.text.toString()
        bodyUpdateKyc.kycRequest?.relativeNumber = binding.edRelativeNumber.text.toString()
        bodyUpdateKyc.kycRequest?.dateOfBirth = binding.edtDateOfBirth.text.toString()
        bodyUpdateKyc.kycRequest?.panCard = data.panCard.toString()
        bodyUpdateKyc.kycRequest?.panCardImage = data.panCardImage.toString()


        AppSession(fragmentContext).putObject(Constant.BODY_UPDATE_KYC,bodyUpdateKyc)

    }

    private fun setupViewModel() {
        val mainRepos = DefaultMainRepo() as MainRepo
        val fintechApi: FintechApi = RetrofitInstance.api
        viewModel = MainViewModel(requireActivity().application,mainRepos,fintechApi)



    }

    private fun initClicks() {
        binding.btnNext.setOnClickListener {
            if(isValidated()){
                val bodyUpdateKyc = AppSession(fragmentContext).getObject(Constant.BODY_UPDATE_KYC,BodyUpdateKyc::class.java) as BodyUpdateKyc
                val user = AppSession(fragmentContext).getObject(Constant.USER_LOGIN,ResponseLoginUser::class.java) as ResponseLoginUser

                bodyUpdateKyc.userId = user.data?.userId?.toInt()
                bodyUpdateKyc.kycRequest?.currentAddress = binding.edtCurrentAddress.text.toString()
                bodyUpdateKyc.kycRequest?.permanentAddress = binding.edtPermanentAddress.text.toString()
                bodyUpdateKyc.kycRequest?.monthlySalary = binding.edtSalary.text.toString()
                bodyUpdateKyc.kycRequest?.relativeName = binding.edtRelativeName.text.toString()
                bodyUpdateKyc.kycRequest?.relativeNumber = binding.edRelativeNumber.text.toString()
                bodyUpdateKyc.kycRequest?.dateOfBirth = binding.edtDateOfBirth.text.toString()

                AppSession(fragmentContext).putObject(Constant.BODY_UPDATE_KYC,bodyUpdateKyc)


                viewModel.submitKycRequest(bodyUpdateKyc)
            }

        }
        binding.edtGender.setOnClickListener {
            val options = arrayOf("Male","Female","Other")
            showOptionsDialog(fragmentContext,options,binding.edtGender)

        }
        binding.edtOccupation.setOnClickListener {
            val options = arrayOf("Salaried","Self-Employed","Business","Student")
            showOptionsDialog(fragmentContext,options,binding.edtOccupation)

        }
        binding.edtDateOfBirth.setOnClickListener {
            showDatePickerDialog(fragmentContext,binding.edtDateOfBirth)
        }


    }

    private fun isValidated():Boolean{
        if(binding.edtFirstName.text.toString().isEmpty()){

            binding.edtFirstName.error = "Please enter name"
            return false

        }else if(binding.edtMidName.text.toString().isEmpty()){
            binding.edtMidName.error ="Please enter midname"

            return false
        }else if(binding.edtDateOfBirth.text.toString().isEmpty()){
            showToast("Select date of birth")

            return false
        }else if(binding.edtEmail.text.toString().isEmpty()){
            binding.edtEmail.error = "Please enter email"
            return false
        }else if(binding.edtGender.text.toString().isEmpty()){
            showToast("Please select gender")
            return false
        }else if(binding.edtOccupation.text.toString().isEmpty()){
            showToast("Please select occupation")
            return false
        }else if(binding.edtPincode.text.toString().isEmpty()){
            binding.edtPincode.error = "Enter Pincode"

            return false
        }else if(binding.edtSalary.text.toString().isEmpty()){

            binding.edtSalary.error = "Enter Salary"

            return false
        }else if(binding.edRelativeNumber.text.toString().isEmpty()){
            binding.edRelativeNumber.error = "Enter Relative Number"
            return false
        }else if (binding.edtRelativeName.text.toString().isEmpty()){
            binding.edtRelativeName.error = "Enter Relative Name"
            return false
        }else if(binding.edtCurrentAddress.text.toString().isEmpty()){

            binding.edtCurrentAddress.error = "Please enter current address"
            return false
        }else if(binding.edtPermanentAddress.text.toString().isEmpty()){
            binding.edtPermanentAddress.error = "Please enter office address"
            return false
        }else{
            return true
        }


    }
    fun showOptionsDialog(context: Context, options: Array<String>, edtGender: EditText) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select an Option")
            .setItems(options) { _, which ->
                // Handle the selected option here
                val selectedOption = options[which]
                // Do something with the selected option
                // For example, you could pass it to another function or display it
                // Or perform any action based on the selected option
                edtGender.setText(selectedOption)

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
    fun showDatePickerDialog(context: Context, editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                editText.setText(formattedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentKycStepOne.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentKycStepOne().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}