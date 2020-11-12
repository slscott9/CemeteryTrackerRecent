package com.example.cemeterytracker.data.repository

import android.app.Application
import com.example.cemeterytracker.CoroutineTestRule
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import com.example.cemeterytracker.data.local.LocalDataSource
import com.example.cemeterytracker.data.remote.RemoteDataSource
import com.example.cemeterytracker.other.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepositoryImplTest {

    private lateinit var repository: Repository

    //collaborators
    lateinit var localDataSource : LocalDataSource
    private lateinit var remoteDataSource : RemoteDataSource
    private lateinit var context: Application
    private lateinit var userRequest: UserRequest
    private lateinit var resourceSuccess : Resource<String>
    private lateinit var resourceError : Resource<String>
    private lateinit var serverResponse: ServerResponse

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {

        userRequest = UserRequest(
            userName = "newUser",
            email = "newUser@gmail.com",
            password = "password",
            graveAdded = 0,
            cemeteriesAdded = 0
        )

        serverResponse = ServerResponse(
            message = "Successful",
            successful = true
        )

        resourceError = Resource.error("Check network connection", null)
        resourceSuccess = Resource.success("Successful")

        localDataSource = mock()
        remoteDataSource = mock()

        context = Application()

        repository = RepositoryImpl(localDataSource, remoteDataSource, context)



    }

    @Test
    fun register_returns_resource_error() = runBlocking<Unit> {
        val exception = Exception()

        doAnswer { throw exception }.`when`(remoteDataSource).register(any())

        val resource = repository.register(userRequest)

        assertEquals(resourceError, resource)


    }

    @Test
    fun register_returns_resource_success() = runBlocking<Unit> {

        whenever(remoteDataSource.register(any())).thenReturn(serverResponse)

        val response = repository.register(userRequest)

        assertEquals(resourceSuccess, response)
    }

    @Test
    fun login_returns_resource_error() = runBlocking<Unit> {

        val exception = Exception()

        doAnswer { throw exception }.`when`(remoteDataSource).login(any())

        val resource = repository.login(userRequest)

        assertEquals(resourceError, resource)
    }



}