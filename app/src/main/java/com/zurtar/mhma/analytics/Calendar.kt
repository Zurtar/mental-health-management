package com.zurtar.mhma.analytics

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.zurtar.mhma.data.DailyEvaluationEntry
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale
import kotlin.reflect.KSuspendFunction1

/**
 * Displays a horizontal calendar with daily evaluations. Each date can be selected, and associated mood colors
 * are displayed based on the evaluations. Allows navigation through months and displays mood-based color indicators.
 *
 * @param modifier Optional modifier to customize the layout.
 * @param evaluations List of daily evaluations to display on the calendar.
 * @param selectedDate The currently selected date, if any.
 * @param onDateSelect Callback triggered when a date is selected.
 */
@Composable
fun AppHorizontalCalendar(
    modifier: Modifier = Modifier,
    evaluations: List<DailyEvaluationEntry>,
    selectedDate: LocalDate? = null,
    onDateSelect: (LocalDate?) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(12) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )

    val emotions = mapOf(
        "Sad" to Color(0xFF1E88E5), // Blue
        "Happy" to Color(0xFF4CAF50), // Yellow
        "Fearful" to Color(0xFF8E24AA), // Purple
        "Angry" to Color(0xBAD32F2F), // Red
    )

    val foo = evaluations.map {
        Pair(
            it.dateCompleted?.toLocalDate(),
            it.strongestEmotion.first
        )
    }

    HorizontalCalendar(
        state = state,
        dayContent = { it ->
            val moodColor =
                foo.find { p -> p.first == it.date }?.second?.let { emotions[it] }
                    ?: Color.Transparent

            Day(
                it,
                isSelected = it.date == selectedDate,
                onClick = { onDateSelect(it?.toLocalDate()) },
                moodColor = moodColor
            )
        },
        monthHeader = { month ->
            val daysOfWeek = remember { month.weekDays.first().map { it.date.dayOfWeek } }
            MonthHeader(
                month = month.yearMonth,
                daysOfWeek = daysOfWeek,
                animateScrollToMonth = state::animateScrollToMonth
            )
        },

        monthContainer = { _, container ->
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp

            ElevatedCard(
                modifier = Modifier
                    .width(screenWidth)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(
                        color = Color.Black,
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                container() // Render the provided container!
            }
        }
    )

}

/**
 * Displays a header for the month containing navigation buttons and the name of the current month.
 *
 * @param month The current month to display.
 * @param daysOfWeek List of days of the week to display in the header.
 * @param animateScrollToMonth A function to animate scrolling to a specific month.
 */
@Composable
fun MonthHeader(
    month: YearMonth,
    daysOfWeek: List<DayOfWeek>,
    animateScrollToMonth: KSuspendFunction1<YearMonth, Unit>
) {
    val scope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Display Month name at the top
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Ensures space between elements
        ) {
            IconButton(onClick = {
                scope.launch {
                    animateScrollToMonth.invoke(month.minusMonths(1))
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Month"
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = month.format(DateTimeFormatter.ofPattern("MMMM y")),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }

            IconButton(onClick = {
                scope.launch {
                    animateScrollToMonth.invoke(month.plusMonths(1))
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next Month"
                )
            }
        }

        // Each Days Header
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                )
            }
        }
    }
}

/**
 * Displays a single day in the calendar, showing the date and mood color based on the evaluation.
 *
 * @param day The day to display.
 * @param isSelected Whether the day is selected.
 * @param moodColor The background color for the mood associated with the day.
 * @param onClick Callback to be invoked when the day is clicked.
 */
@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    moodColor: Color = Color.Transparent,
    onClick: (Date?) -> Unit
) {

    val isToday = day.date == LocalDate.now()
    val color = when {
        day.position != DayPosition.MonthDate && !isSelected -> Color.Gray
        isToday -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onBackground
    }

    val background_color = when {
        isToday -> MaterialTheme.colorScheme.secondaryContainer
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> moodColor
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f) // Keeps all cells square
            .clip(CircleShape)
            .clickable { onClick(day.date.toDate()) }
            .background(
                color = background_color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = color
        )
    }
}
