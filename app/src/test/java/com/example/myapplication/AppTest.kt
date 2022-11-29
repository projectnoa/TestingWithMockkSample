package com.example.myapplication

import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AppTest {
    @RelaxedMockK
    lateinit var view: AppInterface.View

    @RelaxedMockK
    lateinit var repository: Repository

    lateinit var app: AppInterface.AppFeatures

    @Before
    fun setUp() {
        // Initializes properties annotated with @MockK
        MockKAnnotations.init(this, relaxUnitFun = true)
        // Initialize app instance to invoke methods for testing
        app = App(view, repository)
    }

    @Test
    fun `Fetches data and returns empty result`() {
        // Set expectation
        every { repository.fetchData() } returns listOf()
        // Execute method
        app.fetchData()
        // Initialize capture data slot for expectation validation
        val captureData = slot<List<UIDataModel>>()
        // Validate a specific outcome
        verify(exactly = 1) { view.onResult(capture(captureData)) }
        // Assert result
        captureData.captured.let { res ->
            assertNotNull(res)
            assert(res.isEmpty())
        }
    }

    @Test
    fun `Fetches data and throws an exception`() {
        // Set expectation
        every { repository.fetchData() } throws IllegalStateException("Error")
        // Execute method
        app.fetchData()
        // Validate a specific outcome
        verify(exactly = 0) { view.onResult(any()) }
        verify(exactly = 1) { view.onError(any()) }
    }

    @Test
    fun `Fetches data with a different behaviour`() {
        // Initialize a fake UUID
        val uuid = "fake-uuid"
        // Builds an Object mock of the utils.
        mockkObject(Utils)
        // Set expectation
        every { Utils.generateUUID() } returns uuid
        every { repository.fetchData() } returns listOf(Model(1, "A"))
        // Execute method
        app.fetchData()
        // Initialize capture data slot for expectation validation
        val captureData = slot<List<UIDataModel>>()
        // Validate a specific outcome
        verify(exactly = 1) { view.onResult(capture(captureData)) }
        // Assert result
        captureData.captured.let { res ->
            assert(res.isNotEmpty())
            assertEquals(uuid, res.first().uuid)
        }
        // Tear down mocked object
        unmockkObject(Utils)
    }

    @Test
    fun `The log works`() {
        // A spy is a special kind of mockk that enables a mix of mocked behaviour and real behaviour.
        // A part of the behaviour may be mocked, but any non-mocked behaviour will call the original method.
        val spiedApp = spyk(app)
        // Execute mocked method
        every { repository.fetchData() } returns listOf()
        // Execute method
        spiedApp.fetchData()
        // Validate a specific outcome
        verify(exactly = 1) { spiedApp.log(any(), any()) }
    }

    @After
    fun tearDown() {
        // Tear down setup
        unmockkAll()
    }
}