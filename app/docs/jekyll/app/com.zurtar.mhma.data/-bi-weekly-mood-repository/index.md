---
title: BiWeeklyMoodRepository
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[BiWeeklyMoodRepository](index.html)



# BiWeeklyMoodRepository



[androidJvm]\
@Singleton



class [BiWeeklyMoodRepository](index.html)@Injectconstructor(biWeeklyMoodRemoteDataSource: [BiWeeklyMoodRemoteDataSource](../-bi-weekly-mood-remote-data-source/index.html))

Repository class for managing Bi-Weekly mood data. This class interacts with the remote data source to fetch and add mood entries.



## Constructors


| | |
|---|---|
| [BiWeeklyMoodRepository](-bi-weekly-mood-repository.html) | [androidJvm]<br>@Inject<br>constructor(biWeeklyMoodRemoteDataSource: [BiWeeklyMoodRemoteDataSource](../-bi-weekly-mood-remote-data-source/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [addMoodEntry](add-mood-entry.html) | [androidJvm]<br>suspend fun [addMoodEntry](add-mood-entry.html)(moodEntry: [BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html))<br>Adds a BiWeekly mood entry to the remote data source. |
| [fetchLatestMoodEntries](fetch-latest-mood-entries.html) | [androidJvm]<br>suspend fun [fetchLatestMoodEntries](fetch-latest-mood-entries.html)(): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)&gt;<br>Fetches the latest BiWeekly mood entries from the remote data source. |
