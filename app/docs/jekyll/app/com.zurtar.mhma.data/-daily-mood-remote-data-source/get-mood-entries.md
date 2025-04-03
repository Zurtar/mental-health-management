---
title: getMoodEntries
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[DailyMoodRemoteDataSource](index.html)/[getMoodEntries](get-mood-entries.html)



# getMoodEntries



[androidJvm]\
fun [getMoodEntries](get-mood-entries.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../-daily-evaluation-entry/index.html)&gt;&gt;



Listens for real-time updates to the mood entries in Firestore and emits changes as a Flow.



#### Return



A Flow of lists of [DailyEvaluationEntry](../-daily-evaluation-entry/index.html).



