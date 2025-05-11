package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

val LightBrown = Color(0x7091735E)
val DarkBrown = Color(0xFF593E33)
val LightTextOnBrown = Color(0xFFC9A284)
val LightTextLighter = Color(0xFFE5D2BD)
val LighterBrownForDark = Color(0xA8493A31)

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String,
    val credit: String
)

// 15 courses with shorter descriptions
val sampleCourses = listOf(
    Course(
        title = "Introduction to Kotlin",
        code = "KOT101",
        creditHours = 3,
        description = "Covers Kotlin programming basics and syntax.",
        prerequisites = "None",
        credit = "JetBrains Academy"
    ),
    Course(
        title = "Android Fundamentals",
        code = "AND201",
        creditHours = 4,
        description = "Essential Android app development concepts.",
        prerequisites = "Introduction to Kotlin",
        credit = "Google Android Team"
    ),
    Course(
        title = "Jetpack Compose Basics",
        code = "JCP110",
        creditHours = 3,
        description = "Learn to build UIs with Jetpack Compose.",
        prerequisites = "Android Fundamentals",
        credit = "Android Developers"
    ),
    Course(
        title = "Advanced Compose Layouts",
        code = "JCP210",
        creditHours = 3,
        description = "Create complex layouts using Compose.",
        prerequisites = "Jetpack Compose Basics",
        credit = "Compose Community"
    ),
    Course(
        title = "State in Compose",
        code = "JCP220",
        creditHours = 3,
        description = "Manage UI state in Compose apps.",
        prerequisites = "Jetpack Compose Basics",
        credit = "Compose Team"
    ),
    Course(
        title = "Material Design in Compose",
        code = "JCP310",
        creditHours = 2,
        description = "Use Material Design components in Compose.",
        prerequisites = "State in Compose",
        credit = "Material Design Team"
    ),
    Course(
        title = "Networking with Retrofit",
        code = "NET301",
        creditHours = 2,
        description = "Connect your app to the internet with Retrofit.",
        prerequisites = "Android Fundamentals",
        credit = "Square, Inc."
    ),
    Course(
        title = "Room Database Essentials",
        code = "DB201",
        creditHours = 3,
        description = "Store data locally with Room.",
        prerequisites = "Android Fundamentals",
        credit = "Android Architecture Team"
    ),
    Course(
        title = "Dependency Injection with Hilt",
        code = "DI101",
        creditHours = 2,
        description = "Use Hilt for dependency injection.",
        prerequisites = "Android Fundamentals",
        credit = "Google Dagger Team"
    ),
    Course(
        title = "Testing in Android",
        code = "TST101",
        creditHours = 2,
        description = "Basics of unit and UI testing in Android.",
        prerequisites = "Android Fundamentals",
        credit = "Android Testing Team"
    ),
    Course(
        title = "Publishing Your App",
        code = "PUB110",
        creditHours = 1,
        description = "Steps for releasing your app on Play Store.",
        prerequisites = "Testing in Android",
        credit = "Google Play Team"
    ),
    Course(
        title = "Animations in Compose",
        code = "JCP320",
        creditHours = 2,
        description = "Add simple animations to Compose UIs.",
        prerequisites = "Jetpack Compose Basics",
        credit = "Compose Animations Team"
    ),
    Course(
        title = "Accessibility Best Practices",
        code = "ACC101",
        creditHours = 2,
        description = "Make your app accessible to all users.",
        prerequisites = "Material Design in Compose",
        credit = "Android Accessibility Team"
    ),
    Course(
        title = "Compose for Wear OS",
        code = "JCP410",
        creditHours = 2,
        description = "Develop UIs for Wear OS with Compose.",
        prerequisites = "Jetpack Compose Basics",
        credit = "Wear OS Team"
    ),
    Course(
        title = "Compose for Desktop",
        code = "JCP420",
        creditHours = 2,
        description = "Create desktop apps using Compose.",
        prerequisites = "Jetpack Compose Basics",
        credit = "JetBrains Compose Multiplatform"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(courses = sampleCourses)
                }
            }
        }
    }
}

@Composable
fun MyApp(courses: List<Course>, modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    val isDark = isSystemInDarkTheme()
    val globalTextColor = if (isDark) LightTextOnBrown else DarkBrown
    val globalTextLighter = if (isDark) LightTextLighter else DarkBrown

    CompositionLocalProvider(LocalContentColor provides globalTextColor) {
        Column(modifier = modifier) {
            if (shouldShowOnboarding) {
                OnboardingScreen(
                    onContinueClicked = { shouldShowOnboarding = false },
                    globalTextColor = globalTextColor
                )
            } else {
                CourseListScreen(
                    courses = courses,
                    globalTextColor = globalTextColor,
                    globalTextLighter = globalTextLighter
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
    globalTextColor: Color = DarkBrown
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to the Basics Codelab!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = globalTextColor,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBrown,
                    contentColor = globalTextColor
                ),
                modifier = Modifier.padding(top = 8.dp),
                onClick = onContinueClicked
            ) {
                Text(
                    "Continue",
                    color = globalTextColor
                )
            }
        }
    }
}

@Composable
fun CourseListScreen(
    courses: List<Course>,
    modifier: Modifier = Modifier,
    globalTextColor: Color = DarkBrown,
    globalTextLighter: Color = DarkBrown
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        item {
            Text(
                text = "My Courses",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = globalTextColor,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
        items(courses) { course ->
            CourseCard(
                course = course,
                globalTextColor = globalTextColor,
                globalTextLighter = globalTextLighter
            )
        }
    }
}

@Composable
fun CourseCard(
    course: Course,
    modifier: Modifier = Modifier,
    globalTextColor: Color = DarkBrown,
    globalTextLighter: Color = DarkBrown
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()

    val cardBackgroundColor =
        if (isDark) LighterBrownForDark else LightBrown

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .padding(12.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title and Course Code: Use main text color (darker in dark mode)
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = globalTextColor
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = course.code,
                    color = globalTextColor,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.End
                )
            }
            // All other text: Use lighter text color in dark mode
            Text(
                text = course.credit,
                color = globalTextLighter,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Credit Hours: ${course.creditHours}",
                    color = globalTextLighter,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less)
                        } else {
                            stringResource(R.string.show_more)
                        },
                        tint = globalTextLighter
                    )
                }
            }
            if (expanded) {
                Text(
                    text = "Description: ${course.description}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = globalTextLighter),
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text(
                    text = "Prerequisites: ${course.prerequisites}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = globalTextLighter),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    name = "CourseListScreenLight"
)
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "CourseListScreenDark"
)
@Composable
fun CourseListScreenPreview() {
    BasicsCodelabTheme {
        val isDark = isSystemInDarkTheme()
        val globalTextColor = if (isDark) LightTextOnBrown else DarkBrown
        val globalTextLighter = if (isDark) LightTextLighter else DarkBrown
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CourseListScreen(
                courses = sampleCourses,
                globalTextColor = globalTextColor,
                globalTextLighter = globalTextLighter
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    name = "OnboardingLight"
)
@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "OnboardingDark"
)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        val isDark = isSystemInDarkTheme()
        val globalTextColor = if (isDark) LightTextOnBrown else DarkBrown
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OnboardingScreen(onContinueClicked = {}, globalTextColor = globalTextColor)
        }
    }
}