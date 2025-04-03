---
title: JournalViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.journal](../index.html)/[JournalViewModel](index.html)



# JournalViewModel



[androidJvm]\
class [JournalViewModel](index.html)@Injectconstructor(journalRepository: [JournalRepository](../../com.zurtar.mhma.data/-journal-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsible for handling journal entry operations and providing them to the UI.



This ViewModel interacts with the [JournalRepository](../../com.zurtar.mhma.data/-journal-repository/index.html) to manage the creation, updating, deletion, and retrieval of journal entries. It exposes a StateFlow of [JournalUIState](../-journal-u-i-state/index.html) that contains the list of journal entries to be displayed in the UI.



## Constructors


| | |
|---|---|
| [JournalViewModel](-journal-view-model.html) | [androidJvm]<br>@Inject<br>constructor(journalRepository: [JournalRepository](../../com.zurtar.mhma.data/-journal-repository/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[JournalUIState](../-journal-u-i-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [addEntry](add-entry.html) | [androidJvm]<br>fun [addEntry](add-entry.html)(entry: [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html))<br>Adds or updates an existing journal entry.<br>[androidJvm]<br>fun [addEntry](add-entry.html)(title: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), content: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Adds a new journal entry with the provided title and content. |
| [deleteEntry](delete-entry.html) | [androidJvm]<br>fun [deleteEntry](delete-entry.html)(entry: [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html))<br>Deletes a journal entry by the entry object itself.<br>[androidJvm]<br>fun [deleteEntry](delete-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Deletes a journal entry by its ID. |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
| [getEntry](get-entry.html) | [androidJvm]<br>fun [getEntry](get-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [JournalEntry](../../com.zurtar.mhma.data/-journal-entry/index.html)?<br>Retrieves a journal entry by its ID. |
| [updateEntry](update-entry.html) | [androidJvm]<br>fun [updateEntry](update-entry.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), newTitle: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), newContent: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Updates the title and content of an existing journal entry. |
