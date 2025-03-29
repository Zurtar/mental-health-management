package com.zurtar.mhma.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.reflect.KSuspendFunction1

@Composable
fun AppHorizontalCalendar() {
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

    val scope = rememberCoroutineScope()

    HorizontalCalendar(
        state = state,
        dayContent = { Day(it) },
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

@Composable
fun Day(day: CalendarDay) {
    val isToday = day.date == LocalDate.now()
    val color = when {
        day.position != DayPosition.MonthDate -> Color.Gray
        isToday -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onBackground
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f) // Keeps all cells square
            .then(
                if (isToday) Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = color
        )
    }
}
