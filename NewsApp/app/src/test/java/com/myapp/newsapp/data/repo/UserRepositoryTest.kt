package com.myapp.newsapp.data.repo

import com.myapp.newsapp.data.network.firebase.favourite.FavouriteDataSource
import com.myapp.newsapp.data.network.firebase.user.UserDataSource
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.presentation.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @MockK
    lateinit var userDataSource: UserDataSource

    @MockK
    lateinit var favouriteDataSource: FavouriteDataSource

    @InjectMockKs
    lateinit var userRepository : UserRepositoryImp

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userDataSource = mockk<UserDataSource>()
        favouriteDataSource = mockk()
        userRepository = UserRepositoryImp(userDataSource, favouriteDataSource)

    }

    @Test
    fun `signUp with empty email should return failed status`() = runTest {
        val user = User("", "","password")

        coEvery { userDataSource.signUp(any()) } returns true
        val result = userRepository.signUp(user)

        assertEquals(UserResource.Failed("Email can't be empty"), result)
    }

    @Test
    fun `signUp with empty password should return failed status`() = runTest {
        val user = User("","email@example.com", "")

        coEvery { userDataSource.signUp(any()) } returns true
        val result = userRepository.signUp(user)

        assertEquals(UserResource.Failed("Password can't be empty"), result)
    }

    @Test
    fun `signUp with signUp failure should return failed status`() = runTest {
        val user = User("","email@example.com", "password")

        coEvery { userDataSource.signUp(any()) } returns false
        val result = userRepository.signUp(user)

        assertEquals(UserResource.Failed("Sign up failed"), result)
    }

    @Test
    fun `signUp with signUp success should return success status`() = runTest {
        val user = User("","email@example.com", "password")

        coEvery { userDataSource.signUp(any()) } returns true
        val result = userRepository.signUp(user)

        assertEquals(UserResource.Success("Sign up successfully"), result)
    }

    @Test
    fun `signUp with exception should return failed status with exception message`() = runTest {
        val user = User("","email@example.com", "password")

        coEvery { userDataSource.signUp(any()) } throws Exception("An error occurred")
        val result = userRepository.signUp(user)

        assertEquals(UserResource.Failed("An error occurred"), result)
    }
}