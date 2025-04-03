---
title: TabbedContent
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[TabbedContent](-tabbed-content.html)



# TabbedContent



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [TabbedContent](-tabbed-content.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, labelToContent: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)() -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)&gt;, wrapperComposable: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)() -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)? = null)



A composable that displays tabbed content where the active tab's content is displayed below the tab options.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the layout. |
| key | The current active key to decide the content to display. |
| labelToContent | A map where keys represent labels and values are composable content associated with each label. |
| wrapperComposable | A wrapper composable to surround the label/content layout, optional. |



