package com.zurtar.mhma

/*The following contains examples of Journal entries In updated version of application, Journal entries should
/be taken from a database. This modification will be done by modifying the functions found in JournalEntryManager
and JournalViewModel
 */

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

data class JournalEntry(
    var id: Int,
    var title : String,
    var content : String,
    var createdAt: Date
)

@RequiresApi(Build.VERSION_CODES.O)
fun getExampleEntry() : List<JournalEntry>{
    return listOf<JournalEntry>(
        JournalEntry(123, "First Entry", "Insert Example Content", Date.from(Instant.now())),
        JournalEntry(124, "Second Entry", "Insert Example Content", Date.from(Instant.now())),
        JournalEntry(125, "Third Entry", "Insert Example Content", Date.from(Instant.now())),
        JournalEntry(126, "Fourth Entry", "Insert Example Content", Date.from(Instant.now())),
    )
}