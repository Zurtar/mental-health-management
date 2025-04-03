---
title: ChatbotViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.chatbot](../index.html)/[ChatbotViewModel](index.html)



# ChatbotViewModel



[androidJvm]\
class [ChatbotViewModel](index.html)@Injectconstructor(chatRepository: [ChatRepository](../../com.zurtar.mhma.data/-chat-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsible for managing chatbot interactions. Handles message processing, chat logs, and user interactions.



## Constructors


| | |
|---|---|
| [ChatbotViewModel](-chatbot-view-model.html) | [androidJvm]<br>@Inject<br>constructor(chatRepository: [ChatRepository](../../com.zurtar.mhma.data/-chat-repository/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [allMessages](all-messages.html) | [androidJvm]<br>val [allMessages](all-messages.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt;&gt; |
| [logList](log-list.html) | [androidJvm]<br>val [logList](log-list.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatLog](../../com.zurtar.mhma.data/-chat-log/index.html)&gt;&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [addCurrentBranchLog](add-current-branch-log.html) | [androidJvm]<br>fun [addCurrentBranchLog](add-current-branch-log.html)(log: [ChatLog](../../com.zurtar.mhma.data/-chat-log/index.html))<br>Adds a chat log to the repository and clears the current branch messages.<br>[androidJvm]<br>fun [addCurrentBranchLog](add-current-branch-log.html)(messageList: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt;, logType: [ChatBranch](../-chat-branch/index.html), toBeCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)<br>Creates and adds a new chat log based on a list of messages. It makes a copy of the provided message list before storing it. |
| [deleteLog](delete-log.html) | [androidJvm]<br>fun [deleteLog](delete-log.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Deletes a chat log based on its ID. |
| [getBranchList](get-branch-list.html) | [androidJvm]<br>fun [getBranchList](get-branch-list.html)(): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatBranch](../-chat-branch/index.html)&gt;<br>Retrieves a list of all available chat branches. |
| [getBranchStep](get-branch-step.html) | [androidJvm]<br>fun [getBranchStep](get-branch-step.html)(): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>Retrieves the current step in the chatbot's branching logic. |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
| [getCurrentBranch](get-current-branch.html) | [androidJvm]<br>fun [getCurrentBranch](get-current-branch.html)(): [ChatBranch](../-chat-branch/index.html)<br>Retrieves the currently active chat branch or dialogue. |
| [getLog](get-log.html) | [androidJvm]<br>fun [getLog](get-log.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [ChatLog](../../com.zurtar.mhma.data/-chat-log/index.html)?<br>Retrieves a specific chat log based on its ID. |
| [sendCompletionDate](send-completion-date.html) | [androidJvm]<br>fun [sendCompletionDate](send-completion-date.html)(selectedDate: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?)<br>Sends a completion date for a chat log if applicable. |
| [sendMessage](send-message.html) | [androidJvm]<br>fun [sendMessage](send-message.html)(userMessage: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Sends a message from the user to the chatbot. Also triggers the chatbot's response simulation. |
