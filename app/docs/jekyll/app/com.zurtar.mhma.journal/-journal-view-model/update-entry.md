---
title: updateEntry
---
//[app](../../../index.html)/[com.zurtar.mhma.journal](../index.html)/[JournalViewModel](index.html)/[updateEntry](update-entry.html)



# updateEntry



[androidJvm]\
fun [updateEntry](update-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), newTitle: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), newContent: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))



Updates the title and content of an existing journal entry.



This method creates a new [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html) object with the same ID as the existing one, and the new title and content. It then passes the updated entry to the repository for saving.



#### Parameters


androidJvm

| | |
|---|---|
| id | The ID of the journal entry to be updated. |
| newTitle | The new title for the journal entry. |
| newContent | The new content for the journal entry. |



