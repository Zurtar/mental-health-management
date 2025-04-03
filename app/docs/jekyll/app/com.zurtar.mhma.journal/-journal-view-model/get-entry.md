---
title: getEntry
---
//[app](../../../index.html)/[com.zurtar.mhma.journal](../index.html)/[JournalViewModel](index.html)/[getEntry](get-entry.html)



# getEntry



[androidJvm]\
fun [getEntry](get-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html)?



Retrieves a journal entry by its ID.



This method searches the list of journal entries in the UI state and returns the entry that matches the provided ID.



#### Return



The [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html) object if found, or null if no matching entry is found.



#### Parameters


androidJvm

| | |
|---|---|
| id | The ID of the journal entry to be retrieved. |



