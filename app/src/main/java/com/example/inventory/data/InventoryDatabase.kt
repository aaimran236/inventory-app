package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        /*
        The Instance variable keeps a reference to the database, when one has been created.
        This helps maintain a single instance of the database opened at a given time, which is
        an expensive resource to create and maintain.
         */

        /*
        The value of a volatile variable is never cached, and all reads and writes are to and
        from the main memory. These features help ensure the value of "Instance" is always up to
        date and is the same for all execution threads. It means that changes made by one thread
        to "Instance" are immediately visible to all other threads.
         */
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase{
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}