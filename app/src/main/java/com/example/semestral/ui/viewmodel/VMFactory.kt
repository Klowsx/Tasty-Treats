package com.example.semestral.ui.viewmodel

import Repo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class VMFactory(private  val repo: Repo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
        return  modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }

}