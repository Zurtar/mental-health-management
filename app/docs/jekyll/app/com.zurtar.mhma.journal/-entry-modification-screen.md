---
title: EntryModificationScreen
---
//[app](../../index.html)/[com.zurtar.mhma.journal](index.html)/[EntryModificationScreen](-entry-modification-screen.html)



# EntryModificationScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [EntryModificationScreen](-entry-modification-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [JournalViewModel](-journal-view-model/index.html) = hiltViewModel(), id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateBack: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable for the EntryModification page. It can be used for either creating a new entry or editing an existing one. If no entry ID is provided, the fields will be empty for a new entry. If an ID is provided, the fields will be populated with the data from the existing entry.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the composable. |
| viewModel | The ViewModel providing the UI state. |
| id | The ID of the journal entry to modify, or null for new entry creation. |
| openDrawer | A lambda to open the navigation drawer. |
| onNavigateBack | A lambda to navigate back to the previous screen. |



