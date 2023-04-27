package blblblbl.simplelife.pomodorotimer.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import blblblbl.simplelife.pomodorotimer.ui.navigateSingleTopTo
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState
){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "pomodoro timer", textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))
        NavigationDrawerItems(
            navController = navController,
            drawerState = drawerState
        )
        Divider()
        Spacer(modifier = Modifier.weight(1F))

        Button(onClick = {
            try {
                startActivity(
                    context,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + context.packageName)
                    ),
                    null
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    context,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)
                    ),
                    null
                )
            }
        }) {
            Text(text = "rate app")
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerItems(
    navController: NavHostController,
    drawerState: DrawerState
){
    var scope = rememberCoroutineScope()
    var currentBackStackEntryAsState = navController.currentBackStackEntryAsState()
    var destination = currentBackStackEntryAsState.value?.destination

    Column(modifier = Modifier.fillMaxWidth()) {
        menuDestinations.forEach {dest->
            NavigationDrawerItem(
                label = { Text(text = dest.name) },
                selected = destination?.route ==dest.route,
                onClick = {
                    navController.navigateSingleTopTo(dest.route)
                    scope.launch {
                        drawerState.close()
                    }
                })
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}