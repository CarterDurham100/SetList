package com.example.setlist

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.ListenableWorker.Result
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import androidx.work.testing.TestListenableWorkerBuilder
import com.example.setlist.Data.ArtistRepository
import com.example.setlist.Data.BackupWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException
import java.time.Instant

class BackupWorkerTest {

    private lateinit var context: Context
    private lateinit var backupsDir: File

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        backupsDir = File(context.filesDir, "backups")
        backupsDir.deleteRecursively()
    }

    @After
    fun tearDown() {
        backupsDir.deleteRecursively()
    }

    private class FakeArtistRepository(
        artists: List<Artist> = emptyList(),
        private val throwOnRead: Boolean = false
    ) : ArtistRepository {

        private val state = MutableStateFlow(artists)

        override val artists: Flow<List<Artist>> =
            if (throwOnRead) flow { throw IOException("disk error") } else state

        override fun getArtistById(id: Int): Flow<Artist?> = flowOf(null)
        override fun search(term: String): Flow<List<Artist>> = flowOf(emptyList())
        override suspend fun addToArtistList(artist: Artist) {}
        override suspend fun removeFromArtistList(artist: Artist) {}
    }

    private class FakeWorkerFactory(
        private val repository: ArtistRepository
    ) : WorkerFactory() {
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? =
            if (workerClassName == BackupWorker::class.java.name) {
                BackupWorker(appContext, workerParameters, repository)
            } else {
                null
            }
    }

    private fun fakeArtist(id: Int, name: String) = Artist(
        id = id,
        name = name,
        genre = "mhmmm",
        yearFormed = "1000",
        isFav = false,
        dateAdded = Instant.now()
    )

    private fun buildWorker(
        repository: ArtistRepository,
        attempt: Int = 0
    ): BackupWorker =
        TestListenableWorkerBuilder<BackupWorker>(context)
            .setWorkerFactory(FakeWorkerFactory(repository))
            .setRunAttemptCount(attempt)
            .build()

    // ---- tests ----

    @Test
    fun repositoryWithArtists_returnsSuccessAndOutputCountMatches() = runBlocking {
        val artists = listOf(fakeArtist(1, "Nirvana"), fakeArtist(2, "Radiohead"), fakeArtist(3, "Tool"))
        val worker = buildWorker(FakeArtistRepository(artists))

        val result = worker.doWork()

        assertTrue(result is Result.Success)
        val output = (result as Result.Success).outputData
        assertEquals(3, output.getInt(BackupWorker.KEY_ARTIST_COUNT, -1))

        val fileName = output.getString(BackupWorker.KEY_FILE_NAME)
        assertTrue(fileName != null && File(backupsDir, fileName).exists())
    }

    @Test
    fun emptyRepository_returnsSuccessAndWritesNoFile() = runBlocking {
        val worker = buildWorker(FakeArtistRepository(emptyList()))

        val result = worker.doWork()

        assertTrue(result is Result.Success)
        assertNull((result as Result.Success).outputData.getString(BackupWorker.KEY_FILE_NAME))
        assertTrue(backupsDir.list().isNullOrEmpty())
    }

    @Test
    fun ioException_onFirstAttempt_returnsRetry() = runBlocking {
        val worker = buildWorker(FakeArtistRepository(throwOnRead = true), attempt = 0)

        val result = worker.doWork()

        assertEquals(Result.retry(), result)
    }

    @Test
    fun ioException_whenAttemptCountReachesThree_returnsFailure() = runBlocking {
        val worker = buildWorker(FakeArtistRepository(throwOnRead = true), attempt = 3)

        val result = worker.doWork()

        assertEquals(Result.failure(), result)
    }
}