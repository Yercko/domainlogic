package com.example.domainlogicandroidtest.utils

import android.content.Context
import com.example.domainlogicandroidtest.domain.usecase.ShowUserGreeting
import com.example.domainlogicandroidtest.ui.model.UserView

class ShowToastImpl(var context: Context) : ShowUserGreeting {
    override fun show(userView: UserView?) {
    }
}