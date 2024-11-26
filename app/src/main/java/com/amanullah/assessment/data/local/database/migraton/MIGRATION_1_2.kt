package com.amanullah.assessment.data.local.database.migraton

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add the new column with a default value
        db.execSQL("ALTER TABLE posts ADD COLUMN author TEXT DEFAULT NULL")
    }
}
