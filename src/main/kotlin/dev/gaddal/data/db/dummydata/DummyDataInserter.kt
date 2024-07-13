package dev.gaddal.data.db.dummydata

interface DummyDataInserter {
    val config: DummyDataConfig
    suspend fun insert(deleteAll: Boolean = false)
}