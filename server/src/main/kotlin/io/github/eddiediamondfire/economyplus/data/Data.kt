package io.github.eddiediamondfire.economyplus.data

import java.sql.Connection

interface Data {

    fun initaliseDatabase()

    fun saveDatabase()

    fun tableExist(tableName: String): Boolean

    val connection: Connection


}