package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao{

    /*
    In the Inventory app, we only insert the entity from one place that is the Add
    Item screen so we are not expecting any conflicts and can set the conflict strategy
    to Ignore.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    /*
    Note: The @Delete annotation deletes an item or a list of items. You need to pass
    the entities you want to delete. If you don't have the entity, you might have to
    fetch it before calling the delete() function.
     */
    @Delete
    suspend fun delete(item: Item)

    /*
     the :id uses the colon notation in the query to reference arguments in the function.
     */
    @Query("SELECT * from items WHERE id = :id")
    /*
     With Flow as the return type, you receive notification whenever the data in the database
     changes. The Room keeps this Flow updated for you, which means you only need to explicitly
     get the data once. This setup is helpful to update the inventory list, which you implement
     in the next codelab.

     Because of the Flow return type, Room also runs the query on the background thread. You
     don't need to explicitly make it a suspend function and call it inside a coroutine scope.
     */
    fun getItem(id: Int) : Flow<Item>

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}