---
title: DailyMoodRemoteDataSource
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[DailyMoodRemoteDataSource](index.html)



# DailyMoodRemoteDataSource



[androidJvm]\
@Singleton



class [DailyMoodRemoteDataSource](index.html)@Injectconstructor(fireStoreDatasource: FirebaseFirestore)

Data source for managing daily mood evaluations in Firestore. This class provides methods for adding, fetching, and listening to mood entries.



## Constructors


| | |
|---|---|
| [DailyMoodRemoteDataSource](-daily-mood-remote-data-source.html) | [androidJvm]<br>@Inject<br>constructor(fireStoreDatasource: FirebaseFirestore) |


## Functions


| Name | Summary |
|---|---|
| [addMoodEntry](add-mood-entry.html) | [androidJvm]<br>suspend fun [addMoodEntry](add-mood-entry.html)(moodEntry: [DailyEvaluationEntry](../-daily-evaluation-entry/index.html))<br>Adds a new mood entry to the Firestore database. |
| [fetchMoodEntries](fetch-mood-entries.html) | [androidJvm]<br>suspend fun [fetchMoodEntries](fetch-mood-entries.html)(): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../-daily-evaluation-entry/index.html)&gt;<br>Fetches all mood entries from the Firestore database. |
| [getMoodEntries](get-mood-entries.html) | [androidJvm]<br>fun [getMoodEntries](get-mood-entries.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../-daily-evaluation-entry/index.html)&gt;&gt;<br>Listens for real-time updates to the mood entries in Firestore and emits changes as a Flow. |
