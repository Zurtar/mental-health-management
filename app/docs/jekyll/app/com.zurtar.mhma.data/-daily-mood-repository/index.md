---
title: DailyMoodRepository
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[DailyMoodRepository](index.html)



# DailyMoodRepository



[androidJvm]\
@Singleton



class [DailyMoodRepository](index.html)@Injectconstructor(dailyMoodRemoteDataSource: [DailyMoodRemoteDataSource](../-daily-mood-remote-data-source/index.html))

Repository for managing daily mood evaluations. This repository serves as a middle layer between the data source and the application, handling operations related to daily mood entries.



## Constructors


| | |
|---|---|
| [DailyMoodRepository](-daily-mood-repository.html) | [androidJvm]<br>@Inject<br>constructor(dailyMoodRemoteDataSource: [DailyMoodRemoteDataSource](../-daily-mood-remote-data-source/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [addMoodEntry](add-mood-entry.html) | [androidJvm]<br>suspend fun [addMoodEntry](add-mood-entry.html)(moodEntry: [DailyEvaluationEntry](../-daily-evaluation-entry/index.html))<br>Adds a new mood entry to the remote data source. |
| [fetchMoodEntries](fetch-mood-entries.html) | [androidJvm]<br>suspend fun [fetchMoodEntries](fetch-mood-entries.html)(): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../-daily-evaluation-entry/index.html)&gt;<br>Fetches all mood entries from the remote data source. |
| [getMoodEntries](get-mood-entries.html) | [androidJvm]<br>fun [getMoodEntries](get-mood-entries.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../-daily-evaluation-entry/index.html)&gt;&gt;<br>Returns a Flow of mood entries from the remote data source. This flow emits real-time updates when data changes. |
