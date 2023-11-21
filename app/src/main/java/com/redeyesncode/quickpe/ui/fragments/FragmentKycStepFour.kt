package com.redeyesncode.quickpe.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.pay2kart.base.BaseFragment
import com.redeyesncode.quickpe.R
import com.redeyesncode.quickpe.data.BodyUpdateKyc
import com.redeyesncode.quickpe.databinding.FragmentKycStepFourBinding
import com.redeyesncode.quickpe.repository.DefaultMainRepo
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import com.redeyesncode.quickpe.retrofit.RetrofitInstance
import com.redeyesncode.quickpe.ui.viewmodels.MainViewModel
import com.redeyesncode.redbet.session.AppSession
import com.redeyesncode.redbet.session.Constant
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentKycStepFour.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKycStepFour : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var currentPhotoPath: String? = null

    private var eSign: File?=null
    val REQUEST_IMAGE_CAPTURE = 100
    val REQUEST_PICK_IMAGE = 200
    lateinit var viewModel: MainViewModel

    lateinit var binding:FragmentKycStepFourBinding

    var isSign = false
    var isStatement = false
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
        // Inflate the layout for this fragment

        binding = FragmentKycStepFourBinding.inflate(layoutInflater)

        initClicks()
        setupViewModel()
        attachObservers()

        return binding.root
    }
    private fun attachObservers() {
        viewModel.uploadImageResponse.observe(viewLifecycleOwner, Event.EventObserver(
            onLoading = {
                showLoadingDialog()
            },
            onSuccess = {
                dismissLoadingDialog()
                val bodyUpdateKyc = AppSession(fragmentContext).getObject(
                    Constant.BODY_UPDATE_KYC,
                    BodyUpdateKyc::class.java) as BodyUpdateKyc

                if(isSign){
                    bodyUpdateKyc.kycRequest?.signatureImage = it.message
                    viewModel.submitKycRequest(bodyUpdateKyc)
                    AppSession(fragmentContext).putObject(Constant.BODY_UPDATE_KYC,bodyUpdateKyc)

                }else{
                    bodyUpdateKyc.kycRequest?.bankStatementFile = it.message
                    AppSession(fragmentContext).putObject(Constant.BODY_UPDATE_KYC,bodyUpdateKyc)

                    viewModel.submitKycRequest(bodyUpdateKyc)
                }


                showToast("Updating Record")

            },
            onError = {
                dismissLoadingDialog()
                showToast(it)

            }
        ))
        viewModel.submitKycRequestResponse.observe(viewLifecycleOwner, Event.EventObserver(

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

    private fun initClicks() {
        binding.btnESign.setOnClickListener {
            isSign = true
            isStatement = false
            requestImageOrFile()

        }
        binding.btnBnkState.setOnClickListener {
            isSign = false
            isStatement = true
            requestImageOrFile()
        }

    }
    private fun setupViewModel() {
        val mainRepos = DefaultMainRepo() as MainRepo
        val fintechApi: FintechApi = RetrofitInstance.api
        viewModel = MainViewModel(requireActivity().application,mainRepos,fintechApi)



    }
    fun requestImageOrFile() {
        val permissionCamera = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        )
        val permissionStorage = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permissionCamera != PackageManager.PERMISSION_GRANTED ||
            permissionStorage != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_IMAGE_CAPTURE
            )
        } else {
            // Launch the dialog to choose between taking a photo or picking an image
            val items = arrayOf("Take Photo (Not Working)", "Choose from Library", "Cancel")
            val builder = android.app.AlertDialog.Builder(requireContext())
            builder.setTitle("Add Photo")
            builder.setItems(items) { _, which ->
                when (which) {
                    0 -> dispatchTakePictureIntent()
                    1 -> dispatchPickImageIntent()
                    else -> {
                        // Cancel: do nothing or handle accordingly
                    }
                }
            }
            builder.show()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile(requireActivity())
        } catch (ex: IOException) {
            // Error occurred while creating the File
            null
        }
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.redeyesncode.quickpe.fileprovider",
                it
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun dispatchPickImageIntent() {
        val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageIntent.type = "image/*"
        startActivityForResult(pickImageIntent, REQUEST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                REQUEST_IMAGE_CAPTURE -> {
                    // Photo taken and saved to currentPhotoPath
                    val selectedImageUri: Uri? = data?.data

                    eSign = createFileFromUri(selectedImageUri!!, requireActivity())
                }
                REQUEST_PICK_IMAGE -> {
                    val selectedImageUri: Uri? = data?.data

                    // Image picked from gallery
                    if(isSign){
                        eSign = createFileFromUri(selectedImageUri!!, requireActivity())
                        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), eSign!!)
                        val filePart = MultipartBody.Part.createFormData("image_file",
                            eSign!!.name, requestFile)
                        viewModel.uploadFile(filePart)
                        showToast("Uploading File")
                    }else{
                        eSign = createFileFromUri(selectedImageUri!!, requireActivity())

                        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), eSign!!)
                        val filePart = MultipartBody.Part.createFormData("image_file", eSign!!.name, requestFile)
                        viewModel.uploadFile(filePart)
                        showToast("Uploading File")
                    }


                }
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentKycStepFour.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentKycStepFour().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    @Throws(IOException::class)
    private fun createFileFromUri(uri: Uri, activity: Activity): File? {
        val contentResolver = activity.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val file = createImageFile(activity)
        inputStream?.use { input ->
            val outputStream = FileOutputStream(file)
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    @Throws(IOException::class)
    private fun createImageFile(activity: Activity): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        currentPhotoPath = imageFile.absolutePath
        return imageFile
    }
}