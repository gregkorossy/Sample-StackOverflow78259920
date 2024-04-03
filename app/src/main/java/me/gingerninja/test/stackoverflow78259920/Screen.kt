package me.gingerninja.test.stackoverflow78259920

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootScreen(
    rootPrefix: String,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "${rootPrefix}1"
    ) {
        composable("${rootPrefix}1") {
            NestedScreen(
                name = "${rootPrefix.uppercase()}1",
                onNextClick = {
                    navController.navigate("${rootPrefix}2")
                }
            )
        }

        composable("${rootPrefix}2") {
            NestedScreen(
                name = "${rootPrefix.uppercase()}2",
            )
        }
    }
}

@Composable
private fun NestedScreen(
    name: String,
    modifier: Modifier = Modifier,
    onNextClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = name, style = MaterialTheme.typography.displayLarge)

        if (onNextClick != null) {
            Button(onClick = onNextClick) {
                Text("Next")
            }
        }
    }
}