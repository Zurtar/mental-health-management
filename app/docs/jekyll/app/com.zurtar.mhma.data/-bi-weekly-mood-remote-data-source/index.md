---
title: BiWeeklyMoodRemoteDataSource
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[BiWeeklyMoodRemoteDataSource](index.html)



# BiWeeklyMoodRemoteDataSource



[androidJvm]\
@Singleton



class [BiWeeklyMoodRemoteDataSource](index.html)@Injectconstructor(fireStoreDatasource: FirebaseFirestore)

Data source class for interacting with the Firestore database to manage Bi-Weekly mood entries. This class handles adding and fetching mood entries from the Firestore database.



## Constructors


| | |
|---|---|
| [BiWeeklyMoodRemoteDataSource](-bi-weekly-mood-remote-data-source.html) | [androidJvm]<br>@Inject<br>constructor(fireStoreDatasource: FirebaseFirestore) |


## Functions


| Name | Summary |
|---|---|
| [addMoodEntry](add-mood-entry.html) | [androidJvm]<br>suspend fun [addMoodEntry](add-mood-entry.html)(moodEntry: [BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html))<br>Adds a new BiWeekly mood entry to the Firestore database. |
| [fetchMoodEntries](fetch-mood-entries.html) | [androidJvm]<br>suspend fun [fetchMoodEntries](fetch-mood-entries.html)(): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)&gt;<br>Fetches all the BiWeekly mood entries from the Firestore database. |
