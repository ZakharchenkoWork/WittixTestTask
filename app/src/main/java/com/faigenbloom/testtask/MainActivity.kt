package com.faigenbloom.testtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.faigenbloom.testtask.ui.send.SendPageRoute
import com.faigenbloom.testtask.ui.send.sendPage
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedBottomNavigationIndex by rememberSaveable { mutableStateOf(0) }
            val mainNavController = rememberNavController()
            TestTaskTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            selectedIndex = selectedBottomNavigationIndex,
                            onDestinationChanged = { index, destination ->
                                selectedBottomNavigationIndex = index
                                mainNavController.navigate(destination)
                            },
                        )
                    },
                ) { padding ->
                    NavHost(
                        navController = mainNavController,
                        startDestination = SendPageRoute(),
                    ) {
                        sendPage(
                            padding = padding,
                            onTransfer = {
                                // mainNavController.navigate(LoginRoute())
                            },
                        )
                    }
                }
            }
        }
    }
}
