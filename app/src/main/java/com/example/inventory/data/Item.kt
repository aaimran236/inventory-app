/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Entity data class represents a single row in the database.
 */

/*
To ensure consistency and meaningful behavior of the generated code, data classes must fulfill the
following requirements:

The primary constructor must have at least one parameter.
All primary constructor parameters must be val or var.
Data classes cannot be abstract, open, or sealed.
 */

/*
By default (no arguments to @Entity), the table name is the same as the class name.
Use the tableName argument to customize the table name.
 */
@Entity(tableName = "items")
data class Item(
    ///After the app assigns a primary key, it cannot be modified
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)
