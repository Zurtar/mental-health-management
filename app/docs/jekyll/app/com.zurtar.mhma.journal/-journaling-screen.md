---
title: JournalingScreen
---
//[app](../../index.html)/[com.zurtar.mhma.journal](index.html)/[JournalingScreen](-journaling-screen.html)



# JournalingScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [JournalingScreen](-journaling-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [JournalViewModel](-journal-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToEntryCreation: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToEntryView: ([String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Displays the home page for the Journaling section, consisting of a top app bar, a floating action button to add a new journal entry, and a list of existing journal entries.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the composable. |
| viewModel | The ViewModel providing the UI state. |
| openDrawer | A lambda to open the navigation drawer. |
| onNavigateToEntryCreation | A lambda to navigate to the entry creation screen. |
| onNavigateToEntryView | A lambda to navigate to a detailed view of a journal entry. |



