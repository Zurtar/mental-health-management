---
title: getJournalEntries
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[JournalRepository](index.html)/[getJournalEntries](get-journal-entries.html)



# getJournalEntries



[androidJvm]\
fun [getJournalEntries](get-journal-entries.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[JournalEntry](../-journal-entry/index.html)&gt;&gt;



Returns a Flow that emits a list of journal entries in real-time, listening for updates from Firestore.



#### Return



A Flow of [JournalEntry](../-journal-entry/index.html) objects.



