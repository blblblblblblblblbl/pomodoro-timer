package blblblbl.simplelife.pomodorotimer.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
        Button(onClick = { /*TODO*/ }) {
            Text(text = "wiki")
        }
        Button(onClick = { /*TODO*/ }) {
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