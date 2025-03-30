package com.zurtar.mhma.journal

import java.time.Instant
import java.util.Date

/*The following contains examples of Journal entries In updated version of application, Journal entries should
/be taken from a database. This modification will be done by modifying the functions found in JournalEntryManager
and JournalViewModel
 */
data class JournalEntry(
    var id: Int,
    var title : String,
    var content : String,
    var createdAt: Date
)

//These are just "example" entries for demo purposes. I just took them from random sections of
//the cognitive behavioural therapy document.
fun getExampleEntry() : List<JournalEntry>{
    return listOf<JournalEntry>(
        JournalEntry(
            123,
            "Goal Setting #5",
            "This involves the identification of the problems a person is dealing with, determining what goals the individual wants to achieve with respect to that problem, then formulating a plan to achieve that goal. The individual should try to create “SMART” goals: specific, measurable, achievable, realistic, and time-limited goals.\n",
            Date.from(Instant.now())
        ),
        JournalEntry(
            124,
            "Cognitive Reframing Exercise #9",
            "The focus of this technique is in identifying any harmful or nonconstructive patterns in thinking, and transforming them to be more accurate or beneficial. Cognitive Therapy Loss Angeles applies four steps in this process (see example cognitive restructuring questionnaire here: Thought Record) \n" +
                    "\n" +
                    "Record your situation, thoughts, and feelings\n" +
                    "Identify an automatic thought which troubles you\n" +
                    "Develop different points of view about the situation\n" +
                    "Craft an alternative response\n" +
                    "\n" +
                    "In short, this technique is about interrupting the cycle in the cognitive triangle, and resolving dysfunctional automatic thoughts and cognitive distortions.\n",
            Date.from(Instant.now())
        ),
        JournalEntry(
            125,
            "Trying Behavioural Activation #2",
            "As the name implies, this treatment focuses on the behaviour aspect of the cognitive triangle, with the aim of “helping us connect with and routinely involving ourselves in personally rewarding activities.” The Association for Behavioural and Cognitive Therapies outlines 8 steps for this technique:\n" +
                    "\n" +
                    "Keep track of daily activities and mood for a specific limited period of time.\n" +
                    "Review notes to figure out which activities improve mood and quality of life.\n" +
                    "Consider restarting past rewarding activities (that may have been stopped for a variety of reasons).\n" +
                    "Look over developed lists of positive activities to get new ideas.\n" +
                    "Proactively schedule and plan for rewarding activities each day.\n",
            Date.from(Instant.now())
        ),
        JournalEntry(
            126,
            "Thought Record #1",
            "A type of exercise in which the individual will record their thoughts about a situation or more general circumstances, often following guided prompts, for the purpose of analyzing where those thoughts are coming from, and the evidence for them. And example of the contents of a thought record include the following:\n" +
                    "\n" +
                    "The situation–what happened?\n" +
                    "My feelings–how this made you feel at first\n" +
                    "Unhelpful thoughts I had\n" +
                    "Evidence to support my unhelpful thoughts\n" +
                    "Evidence against my unhelpful thoughts\n" +
                    "Alternative, more realistic or neutral thoughts\n" +
                    "How I feel now–how your feelings have changed after completing your thought record\n",
            Date.from(Instant.now())
        ),
    )
}