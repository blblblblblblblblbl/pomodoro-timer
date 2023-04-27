package blblblbl.simplelife.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(
    startOnClick: () -> Unit,
) {
    val pages = listOf(
        OnBoardingPage.Page1,
        OnBoardingPage.Page2,
        OnBoardingPage.Page3,
        OnBoardingPage.Page4,
        OnBoardingPage.Page5,
    )
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Surface() {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier.weight(10f),
                count = OnBoardingPage.ONBOARDING_PAGES_COUNT,
                state = pagerState,
                verticalAlignment = Alignment.Bottom
            ) { position ->
                PagerScreen(onBoardingPage = pages[position])
            }
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                pagerState = pagerState
            )
            SkipButton(modifier = Modifier.weight(1f),
                pagerState = pagerState ) {

                coroutineScope.launch { pagerState.animateScrollToPage(5) }

            }
            FinishButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            ) {
                startOnClick()
            }
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.8f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )
        Text(
            text = onBoardingPage.title,
            modifier = Modifier
                .fillMaxWidth(),

            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = onBoardingPage.description,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SkipButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = (pagerState.currentPage in ONBOARDING_FIRST_PAGE_INDEX until ONBOARDING_LAST_PAGE_INDEX)
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "skip")
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == ONBOARDING_LAST_PAGE_INDEX
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "ok")
            }
        }
    }
}


const val ONBOARDING_FIRST_PAGE_INDEX = 0
const val ONBOARDING_LAST_PAGE_INDEX = 4