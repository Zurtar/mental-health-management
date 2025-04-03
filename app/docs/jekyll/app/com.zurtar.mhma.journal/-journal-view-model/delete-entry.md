---
title: deleteEntry
---
//[app](../../../index.html)/[com.zurtar.mhma.journal](../index.html)/[JournalViewModel](index.html)/[deleteEntry](delete-entry.html)



# deleteEntry



[androidJvm]\
fun [deleteEntry](delete-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))



Deletes a journal entry by its ID.



This method removes the journal entry with the specified ID from the repository.



#### Parameters


androidJvm

| | |
|---|---|
| id | The ID of the journal entry to be deleted. |





[androidJvm]\
fun [deleteEntry](delete-entry.html)(entry: [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html))



Deletes a journal entry by the entry object itself.



This method calls [deleteEntry](delete-entry.html) with the ID of the provided [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html).



#### Parameters


androidJvm

| | |
|---|---|
| entry | The [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html) object to be deleted. |



