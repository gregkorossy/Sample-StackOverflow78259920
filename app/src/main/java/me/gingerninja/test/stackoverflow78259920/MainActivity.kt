package me.gingerninja.test.stackoverflow78259920

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map
import me.gingerninja.test.stackoverflow78259920.ui.theme.StackOverflow78259920Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StackOverflow78259920Theme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val isRootRoute = false // TODO where is this coming from?

            TopAppBar(
                title = {
                    Text("Nice")
                },
                navigationIcon = {
                    if (!isRootRoute) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                //tint = Color.White,
                                contentDescription = "back"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController
            )
        }
    ) { innerPadding ->
        MainContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
private fun MainContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "a",
    ) {
        composable("a") {
            RootScreen(
                rootPrefix = "a",
                modifier = Modifier.fillMaxSize()
            )
        }

        composable("b") {
            RootScreen(
                rootPrefix = "b",
                modifier = Modifier.fillMaxSize()
            )
        }

        composable("c") {
            RootScreen(
                rootPrefix = "c",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val bottomTabs = remember {
        listOf(
            BottomTab(
                icon = Icons.Default.Home,
                title = "A",
                rootRoute = Route(
                    name = "a"
                )
            ),
            BottomTab(
                icon = Icons.Default.Face,
                title = "B",
                rootRoute = Route(
                    name = "b"
                )
            ),
            BottomTab(
                icon = Icons.Default.Settings,
                title = "C",
                rootRoute = Route(
                    name = "c"
                )
            )
        )
    }

    /*var currentRouteName by remember(navController) {
        mutableStateOf(navController.currentDestination?.route)
    }*/

    val currentRouteName by navController.currentBackStackEntryFlow
        .map { it.destination.route }
        .collectAsState(initial = null)

    NavigationBar(
        modifier = modifier
    ) {
        bottomTabs.forEachIndexed { _, bottomTab ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = { Icon(bottomTab.icon!!, contentDescription = bottomTab.title) },
                label = { Text(bottomTab.title) },
                selected = bottomTab.rootRoute.name == currentRouteName,
                onClick = {
                    //currentRouteName = bottomTab.rootRoute.name
                    navController.navigate(bottomTab.rootRoute.name) {
                        navController.graph.startDestinationRoute?.let { startRoute ->
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private data class BottomTab(
    val icon: ImageVector,
    val title: String,
    val rootRoute: Route
)

private data class Route(
    val name: String
)