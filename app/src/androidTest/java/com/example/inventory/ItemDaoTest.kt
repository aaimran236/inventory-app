package com.example.inventory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.InventoryDatabase
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase

    /*
     we use an in-memory database and do not persist it on the disk. To do so, we use
     the inMemoryDatabaseBuilder() function.

     we do this because the information need not be persisted, but rather, needs to be
     deleted when the process is killed.

     we are running the DAO queries in the main thread with .allowMainThreadQueries(),
     just for testing.
     */

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        itemDao = inventoryDatabase.itemDao()
    }

    private var item1 = Item(1, "Apples", 10.0, 20)
    private var item2 = Item(2, "Bananas", 15.0, 97)

    ///utility functions
    private suspend fun addOneItemToDb() {
        itemDao.insert(item1)
    }

    private suspend fun addTwoItemsToDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }

    /*
    In this test, we use the utility function addOneItemToDb()to add one item to the database.

    Then, we read the first item in the database. With assertEquals(), you compare the
    expected value with the actual value. You run the test in a new coroutine with
    runBlocking{}. This setup is the reason you mark the utility functions as suspend.
     */
    @Test
    fun daoInsert_insertsItemIntoDB()= runBlocking{
        addOneItemToDb()
        val allItems=itemDao.getAllItems().first()
        assertEquals(item1,allItems[0])
    }

    @Test
    fun daoGetAllItems_returnsAllItemsFromDB()=runBlocking {
        addTwoItemsToDb()
        val allItems=itemDao.getAllItems().first()
        assertEquals(item1,allItems[0])
        assertEquals(item2,allItems[1])
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        inventoryDatabase.close()
    }

}