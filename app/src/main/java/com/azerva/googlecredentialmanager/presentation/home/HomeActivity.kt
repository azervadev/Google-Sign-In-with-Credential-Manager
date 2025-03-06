package com.azerva.googlecredentialmanager.presentation.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.azerva.googlecredentialmanager.R
import com.azerva.googlecredentialmanager.core.launchActivity
import com.azerva.googlecredentialmanager.data.GoogleAuthImp
import com.azerva.googlecredentialmanager.databinding.ActivityHomeBinding
import com.azerva.googlecredentialmanager.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var googleAuthImp : GoogleAuthImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        googleAuthImp = GoogleAuthImp(this@HomeActivity, CredentialManager.create(this))

        binding.btnReturnLogin.setOnClickListener {
            lifecycleScope.launch {
                launchActivity<LoginActivity> { }
                googleAuthImp.googleSignOut()
            }
        }

    }
}