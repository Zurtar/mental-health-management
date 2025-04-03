---
title: JournalRepository
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[JournalRepository](index.html)



# JournalRepository



[androidJvm]\
@Singleton



class [JournalRepository](index.html)@Injectconstructor(journalRemoteDataSource: [JournalRemoteDataSource](../-journal-remote-data-source/index.html))

Repository class responsible for handling operations related to journal entries. It acts as a bridge between the data source and the rest of the application, providing methods to add, update, delete, and fetch journal entries.



## Constructors


| | |
|---|---|
| [JournalRepository](-journal-repository.html) | [androidJvm]<br>@Inject<br>constructor(journalRemoteDataSource: [JournalRemoteDataSource](../-journal-remote-data-source/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [addJournalEntry](add-journal-entry.html) | [androidJvm]<br>suspend fun [addJournalEntry](add-journal-entry.html)(journalEntry: [JournalEntry](../-journal-entry/index.html))<br>Adds a new journal entry to the Firestore database. |
| [deleteJournalEntry](delete-journal-entry.html) | [androidJvm]<br>suspend fun [deleteJournalEntry](delete-journal-entry.html)(journalEntry: [JournalEntry](../-journal-entry/index.html))<br>Deletes a journal entry using the provided [JournalEntry](../-journal-entry/index.html) object.<br>[androidJvm]<br>suspend fun [deleteJournalEntry](delete-journal-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Deletes a journal entry by its ID. |
| [getJournalEntries](get-journal-entries.html) | [androidJvm]<br>fun [getJournalEntries](get-journal-entries.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[JournalEntry](../-journal-entry/index.html)&gt;&gt;<br>Returns a Flow that emits a list of journal entries in real-time, listening for updates from Firestore. |
| [updateMoodEntry](update-mood-entry.html) | [androidJvm]<br>suspend fun [updateMoodEntry](update-mood-entry.html)(journalEntry: [JournalEntry](../-journal-entry/index.html))<br>Updates an existing journal entry in the Firestore database. If the entry does not exist, it will be added. |
