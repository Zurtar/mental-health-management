---
title: EntryViewScreen
---
//[app](../../index.html)/[com.zurtar.mhma.journal](index.html)/[EntryViewScreen](-entry-view-screen.html)



# EntryViewScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [EntryViewScreen](-entry-view-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [JournalViewModel](-journal-view-model/index.html) = hiltViewModel(), id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateBack: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToEntryEdit: ([String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable, Displays the entry view screen, where users can see the details of a selected journal entry. It has options to navigate back to the journaling screen or edit the current entry.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the composable. |
| viewModel | The ViewModel providing the UI state. |
| id | The ID of the `JounralEntry` to view. |
| openDrawer | A lambda to open the navigation drawer. |
| onNavigateBack | A lambda to navigate back to the journaling screen. |
| onNavigateToEntryEdit | A lambda to navigate to the entry edit screen. |



