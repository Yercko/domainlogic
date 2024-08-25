package com.example.domainlogicandroidtest.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domainlogicandroidtest.databinding.ActivityUsersearchBinding
import com.example.domainlogicandroidtest.ui.adapters.UserAdapter
import com.example.domainlogicandroidtest.ui.model.UserView
import com.example.domainlogicandroidtest.ui.presenter.UserSearchPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserSearchActivity : AppCompatActivity(), UserSearchView {

    @Inject lateinit var presenter: UserSearchPresenter
    private lateinit var binding: ActivityUsersearchBinding
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.attachView(this)
        adapter = UserAdapter { user -> presenter.onUserClicked(user) }
        binding.recyclerViewUsers.adapter = adapter
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)

        binding.buttonSearch.setOnClickListener {
            val username = binding.editTextSearch.text.toString()
            presenter.onSearchButtonClicked(username)
        }
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                validateInput(input)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun validateInput(input: String) {
        binding.buttonSearch.isEnabled = presenter.validSizeTextInput(input)
        binding.textViewError.visibility =
            if (input.isNotEmpty() && !presenter.validSizeTextInput(input))
                VISIBLE else INVISIBLE
    }

    override fun showUsers(users: List<UserView>) {
        adapter.submitList(users)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToUserDetail(user: UserView) {
        UserDetailActivity.startActivity(this, user.login, user.id, user.avatarUrl, user.htmlUrl)
    }
}