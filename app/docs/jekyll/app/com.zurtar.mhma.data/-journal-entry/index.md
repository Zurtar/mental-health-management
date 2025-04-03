---
title: JournalEntry
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[JournalEntry](index.html)



# JournalEntry



[androidJvm]\
data class [JournalEntry](index.html)(var id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var title: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var content: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var createdAt: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)

Represents a journal entry that contains a title, content, and the date the entry was created.



## Constructors


| | |
|---|---|
| [JournalEntry](-journal-entry.html) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, title: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, content: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, createdAt: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [content](content.html) | [androidJvm]<br>var [content](content.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The content of the journal entry. |
| [createdAt](created-at.html) | [androidJvm]<br>@Serializable(with = [DateSerializer::class](../../com.zurtar.mhma.data.models/-date-serializer/index.html))<br>var [createdAt](created-at.html): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?<br>The date when the journal entry was created. |
| [id](id.html) | [androidJvm]<br>var [id](id.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The unique identifier for the journal entry. |
| [title](title.html) | [androidJvm]<br>var [title](title.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The title of the journal entry. |
