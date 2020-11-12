package com.example.cemeterytracker.data.local

import com.example.cemeterytracker.data.database.CemeteryDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: CemeteryDao
) : LocalDataSource {
}