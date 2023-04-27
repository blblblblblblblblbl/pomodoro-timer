package blblblbl.simplelife.onboarding

import androidx.annotation.DrawableRes

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object Page1 : OnBoardingPage(
        image = R.drawable.onboarding1,
        title = "Timer",
        description = "keep track of time with a pomodoro timer"
    )

    object Page2 : OnBoardingPage(
        image = R.drawable.onboarding2,
        title = "Configuration",
        description = "set the timer to your preference"
    )

    object Page3 : OnBoardingPage(
        image = R.drawable.onboarding3,
        title = "History",
        description = "take a look at the calendar and check your progress"
    )
    object Page4 : OnBoardingPage(
        image = R.drawable.onboarding4,
        title = "Settings",
        description = "configure app with your preference, pick alarm sound and theme"
    )
    object Page5 : OnBoardingPage(
        image = R.drawable.onboarding5,
        title = "Custom theme",
        description = "choose the color you want for the theme"
    )
    companion object{
        const val ONBOARDING_PAGES_COUNT = 5
    }
}
