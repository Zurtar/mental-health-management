---
title: getMoodEntries
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[DailyMoodRepository](index.html)/[getMoodEntries](get-mood-entries.html)



# getMoodEntries



[androidJvm]\
fun [getMoodEntries](get-mood-entries.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../-daily-evaluation-entry/index.html)&gt;&gt;



Returns a Flow of mood entries from the remote data source. This flow emits real-time updates when data changes.



#### Return



A Flow of lists of [DailyEvaluationEntry](../-daily-evaluation-entry/index.html).



