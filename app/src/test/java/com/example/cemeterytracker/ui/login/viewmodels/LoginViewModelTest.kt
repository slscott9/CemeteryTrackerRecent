package com.example.cemeterytracker.ui.login.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cemeterytracker.CoroutineTestRule
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.getOrAwaitValue2
import com.example.cemeterytracker.other.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {


    private lateinit var viewModel : LoginViewModel

    //collaborators
    private lateinit var repository: Repository


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        repository = mock()

        viewModel = LoginViewModel(repository)
    }


    //login method posts resource.error when username or password is empty
   @Test
   fun login() = runBlockingTest {

        viewModel.login("", "password")


        val value = viewModel.loginStatus.getOrAwaitValue2()

        assertEquals(Resource.error("Please fill out all fields", null), value)

   }

    @Test
    fun login_posts_resource_success() = runBlockingTest {

        whenever(repository.login(any())).thenReturn(Resource.success(null))

        viewModel.login("newUser", "Password")

        val value = viewModel.loginStatus.getOrAwaitValue2()

        assertEquals(Resource.success(null), value)
    }
}