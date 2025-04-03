---
title: JournalRemoteDataSource
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[JournalRemoteDataSource](index.html)



# JournalRemoteDataSource



[androidJvm]\
@Singleton



class [JournalRemoteDataSource](index.html)@Injectconstructor(fireStoreDatasource: FirebaseFirestore)

Data source class for managing journal entries in Firestore. This class is responsible for adding, deleting, updating, and fetching journal entries from the Firestore database.



## Constructors


| | |
|---|---|
| [JournalRemoteDataSource](-journal-remote-data-source.html) | [androidJvm]<br>@Inject<br>constructor(fireStoreDatasource: FirebaseFirestore) |


## Functions


| Name | Summary |
|---|---|
| [addJournalEntry](add-journal-entry.html) | [androidJvm]<br>suspend fun [addJournalEntry](add-journal-entry.html)(journalEntry: [JournalEntry](../-journal-entry/index.html))<br>Adds a new journal entry to Firestore. |
| [deleteJournalEntry](delete-journal-entry.html) | [androidJvm]<br>suspend fun [deleteJournalEntry](delete-journal-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Deletes a journal entry from Firestore by its ID. |
| [fetchJournalEntries](fetch-journal-entries.html) | [androidJvm]<br>suspend fun [fetchJournalEntries](fetch-journal-entries.html)(): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[JournalEntry](../-journal-entry/index.html)&gt;<br>Fetches all journal entries from Firestore and returns them as a list of [JournalEntry](../-journal-entry/index.html) objects. |
| [getJournalEntries](get-journal-entries.html) | [androidJvm]<br>fun [getJournalEntries](get-journal-entries.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[JournalEntry](../-journal-entry/index.html)&gt;&gt;<br>Returns a Flow of journal entries, listening for real-time updates from Firestore. |
| [updateMoodEntry](update-mood-entry.html) | [androidJvm]<br>suspend fun [updateMoodEntry](update-mood-entry.html)(journalEntry: [JournalEntry](../-journal-entry/index.html))<br>Updates an existing journal entry in Firestore. If the journal entry does not exist, it will be added. |
