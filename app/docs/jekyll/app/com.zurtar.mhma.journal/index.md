---
title: com.zurtar.mhma.journal
---
//[app](../../index.html)/[com.zurtar.mhma.journal](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [JournalUIState](-journal-u-i-state/index.html) | [androidJvm]<br>data class [JournalUIState](-journal-u-i-state/index.html)(var entryList: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[JournalEntry](../com.zurtar.mhma.data/-journal-entry/index.html)&gt; = listOf())<br>UI State for managing the journal entries. |
| [JournalViewModel](-journal-view-model/index.html) | [androidJvm]<br>class [JournalViewModel](-journal-view-model/index.html)@Injectconstructor(journalRepository: [JournalRepository](../com.zurtar.mhma.data/-journal-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)<br>ViewModel responsible for handling journal entry operations and providing them to the UI. |


## Functions


| Name | Summary |
|---|---|
| [EntryItem](-entry-item.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [EntryItem](-entry-item.html)(item: [JournalEntry](../com.zurtar.mhma.data/-journal-entry/index.html), onDelete: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onView: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)) |
| [EntryModificationScreen](-entry-modification-screen.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [EntryModificationScreen](-entry-modification-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [JournalViewModel](-journal-view-model/index.html) = hiltViewModel(), id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateBack: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable for the EntryModification page. It can be used for either creating a new entry or editing an existing one. If no entry ID is provided, the fields will be empty for a new entry. If an ID is provided, the fields will be populated with the data from the existing entry. |
| [EntryViewScreen](-entry-view-screen.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [EntryViewScreen](-entry-view-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [JournalViewModel](-journal-view-model/index.html) = hiltViewModel(), id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateBack: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToEntryEdit: ([String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable, Displays the entry view screen, where users can see the details of a selected journal entry. It has options to navigate back to the journaling screen or edit the current entry. |
| [JournalingScreen](-journaling-screen.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [JournalingScreen](-journaling-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [JournalViewModel](-journal-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToEntryCreation: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToEntryView: ([String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Displays the home page for the Journaling section, consisting of a top app bar, a floating action button to add a new journal entry, and a list of existing journal entries. |
