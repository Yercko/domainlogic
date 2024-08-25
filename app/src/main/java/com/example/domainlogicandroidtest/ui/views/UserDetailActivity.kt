package com.example.domainlogicandroidtest.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.domainlogicandroidtest.R
import com.example.domainlogicandroidtest.databinding.ActivityUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val username = intent.getStringExtra(EXTRA_USER_NAME)
        val userId = intent.getIntExtra(EXTRA_USER_ID, -1)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val htmlUrl = intent.getStringExtra(EXTRA_HTML_URL)

        binding.textViewUsername.text = username
        binding.textViewUserId.text = "ID: $userId"
        binding.textViewHtmlUrl.text = htmlUrl

        Glide.with(this)
            .load(avatarUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageViewAvatar)
    }

    companion object {
        const val EXTRA_USER_NAME = "extra_user_name"
        const val EXTRA_USER_ID = "extra_user_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        const val EXTRA_HTML_URL = "extra_html_url"

        fun startActivity(
            context: AppCompatActivity,
            username: String?,
            userId: Int?,
            avatarUrl: String?,
            htmlUrl: String?
        ) {
            val intent = Intent(context, UserDetailActivity::class.java).apply {
                putExtra(EXTRA_USER_NAME, username)
                putExtra(EXTRA_USER_ID, userId)
                putExtra(EXTRA_AVATAR_URL, avatarUrl)
                putExtra(EXTRA_HTML_URL, htmlUrl)
            }
            context.startActivity(intent)
        }
    }
}