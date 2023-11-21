package com.redeyesncode.quickpe.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.redeyesncode.dateme.base.Event
import com.redeyesncode.quickpe.R
import com.redeyesncode.quickpe.data.ResponseLoginUser
import com.redeyesncode.quickpe.databinding.ActivityDashboardBinding
import com.redeyesncode.quickpe.repository.DefaultMainRepo
import com.redeyesncode.quickpe.repository.MainRepo
import com.redeyesncode.quickpe.retrofit.FintechApi
import com.redeyesncode.quickpe.retrofit.RetrofitInstance
import com.redeyesncode.quickpe.ui.adapters.ImagePagerAdapter
import com.redeyesncode.quickpe.ui.viewmodels.AuthViewModel
import com.redeyesncode.quickpe.ui.viewmodels.MainViewModel
import com.redeyesncode.redbet.base.BaseActivity
import com.redeyesncode.redbet.session.AppSession
import com.redeyesncode.redbet.session.Constant

class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val images = listOf(
        R.drawable.ic_banner_one,
        R.drawable.ic_banner_two,
        R.drawable.ic_banner_three,
        R.drawable.ic_banner_four,
        // Add more drawable resources here
    )

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.home) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }else if(item.itemId == R.id.profile){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,ProfileActivity::class.java))
            return true
        }else if(item.itemId == R.id.kyc){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this@DashboardActivity,SubmitKycActivity::class.java))
            return true
        }else if(item.itemId == R.id.logout) {
            val intentDashboard = Intent(this@DashboardActivity,LoginActivity::class.java)
            intentDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            AppSession(this@DashboardActivity).clear()
            startActivity(intentDashboard)


            return true
        }else{
            return false
        }

    }

    lateinit var viewmodel:MainViewModel
    private var currentPage = 0
    private val scrollDelay: Long = 1500 // Delay in milliseconds (4 seconds)
    private val runnable = Runnable {
        if (currentPage == images.size) {
            currentPage = 0
        }
        binding.viewPager.setCurrentItem(currentPage++, true)
        startAutoScroll()
    }

    private val handler = Handler()
    lateinit var binding:ActivityDashboardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)

        initClicks()
        setupViewModel()
        attachObservers()
        initialApiCall()

        setContentView(binding.root)
    }

    private fun initialApiCall() {
        val user = AppSession(this@DashboardActivity).getObject(Constant.USER_LOGIN,ResponseLoginUser::class.java) as ResponseLoginUser

        val map = hashMapOf<String,String>()
        map.put("user_id",user.data?.userId.toString())
        viewmodel.getKycDetails(map)

    }

    private fun attachObservers() {
        viewmodel.getKycDetailsResponse.observe(this,Event.EventObserver(
            onLoading = {
                showLoadingDialog()
            }, onSuccess = {
                binding.swipeRefresh.isRefreshing = false

                hideLoadingDialog()
                showSnackbar("KYC STATUS ${it.data?.kycStatus}")
                binding.tvKycStatus.text = "Status : ${it.data?.kycStatus}"
                if(it.data?.kycStatus.equals("PENDING")){
                    binding.tvKycStatusApproved.text = "PENDING"
                    binding.cardKycStatus.setCardBackgroundColor(ContextCompat.getColor(this@DashboardActivity,R.color.yellow))
                    binding.cardKycApply.visibility = View.VISIBLE
                }else if (it.data?.kycStatus.equals("APPROVED")){
                    binding.tvKycStatusApproved.text = "APPROVED"
                    binding.cardKycStatus.setCardBackgroundColor(ContextCompat.getColor(this@DashboardActivity,R.color.green_btn))
                    binding.cardKycApply.visibility = View.GONE
                }else if (it.data?.kycStatus.equals("DENIED_REASON")){
                    binding.tvKycStatusApproved.text = "DENIED_REASON ${it.data?.deniedReason.toString()}"
                    binding.cardKycStatus.setCardBackgroundColor(ContextCompat.getColor(this@DashboardActivity,R.color.red))
                    binding.cardKycApply.visibility = View.VISIBLE

                }else if (it.data?.kycStatus.equals("DENIED_NO_REASON")){
                    binding.tvKycStatusApproved.text = "DENIED_NO_REASON"
                    binding.cardKycStatus.setCardBackgroundColor(ContextCompat.getColor(this@DashboardActivity,R.color.red))
                    binding.cardKycApply.visibility = View.GONE

                }
                //DENIED_REASON
                //DENIED_NO_REASON


            }, onError = {
                hideLoadingDialog()
                showToast(it)

            }


        ))


    }

    private fun setupViewModel() {

        val mainRepos = DefaultMainRepo() as MainRepo
        val fintechApi: FintechApi = RetrofitInstance.api


        viewmodel = MainViewModel(application,mainRepos,fintechApi)




    }

    private fun initClicks() {

        binding.navView.setNavigationItemSelectedListener(this)

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)

        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            initialApiCall()

        }

        val adapter = ImagePagerAdapter(this, images)
        binding.viewPager.adapter = adapter
        startAutoScroll()

        binding.cardKycApply.setOnClickListener {
            val intentKyc = Intent(this@DashboardActivity,SubmitKycActivity::class.java)
            startActivity(intentKyc)

        }
        binding.recvLoanPackages.adapter = com.redeyesncode.pay2kart.base.BaseAdapter<String>(this,R.layout.item_loan_package,10)
        binding.recvLoanPackages.layoutManager = LinearLayoutManager(this@DashboardActivity,LinearLayoutManager.VERTICAL,false)

    }
    private fun startAutoScroll() {
        handler.postDelayed(runnable, scrollDelay)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks to prevent memory leaks
        handler.removeCallbacks(runnable)
    }
}