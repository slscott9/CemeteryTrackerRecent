package com.example.cemeterytracker.ui.login.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cemeterytracker.CoroutineTestRule
import com.example.cemeterytracker.TestCoroutineRule
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.getOrAwaitValue2
import com.example.cemeterytracker.other.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class RegisterViewModelTest {


    private lateinit var viewModel: RegisterViewModel

    //collborators
    private lateinit var repository: Repository


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()


    /*
        RegisterViewModel's register function uses a suspend function repo.register that is launched in viewmodelscope.launch coroutine
        This relies on the Main looper which is only available in the application.
        Unit Tests do not have access to the main dispatcher (androidTests do)

        CoroutineTestRule gives this unit test class access to the main looper
       */



    @Before
    fun setup() {
        repository = mock()
        viewModel = RegisterViewModel(repository)

    }

    @After
    fun tearDown() {
    }

    //register view model's register function posts resource.loading
    @Test
    fun register()  = runBlockingTest{

        viewModel.register("newUser@gmail.com", "newUser", "password")

        val value = viewModel.registerStatus.getOrAwaitValue2()

        assertEquals( viewModel.registerStatus.value, Resource.loading(null))
    }

    @Test
    fun register_posts_resource_error() = runBlockingTest {

        viewModel.register("", "", "")

        val value = viewModel.registerStatus.getOrAwaitValue2()

        assertEquals(value, Resource.error("Please fill in all fields", null))
    }

    @Test
    fun register_posts_resource_success() = runBlockingTest{


        whenever(repository.register(any())).thenReturn(Resource.success(null))

        viewModel.register("h", "g","g")



        val value = viewModel.registerStatus.value

        assertEquals(Resource.success(null), value)





    }









}