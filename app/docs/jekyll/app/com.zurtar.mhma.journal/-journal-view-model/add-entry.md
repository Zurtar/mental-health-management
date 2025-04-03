---
title: addEntry
---
//[app](../../../index.html)/[com.zurtar.mhma.journal](../index.html)/[JournalViewModel](index.html)/[addEntry](add-entry.html)



# addEntry



[androidJvm]\
fun [addEntry](add-entry.html)(title: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), content: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))



Adds a new journal entry with the provided title and content.



This method creates a new [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html) object with the given title and content, and assigns a new UUID as the entry ID. The entry is then added to the repository.



#### Parameters


androidJvm

| | |
|---|---|
| title | The title of the journal entry. |
| content | The content of the journal entry. |





[androidJvm]\
fun [addEntry](add-entry.html)(entry: [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html))



Adds or updates an existing journal entry.



If the provided [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html) has an ID of &quot;-1&quot; (indicating it is a new entry), a new UUID is assigned to the entry. It also ensures the creation time is set. This entry is then passed to the repository to be added.



#### Parameters


androidJvm

| | |
|---|---|
| entry | The [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html) object to be added or updated. |



