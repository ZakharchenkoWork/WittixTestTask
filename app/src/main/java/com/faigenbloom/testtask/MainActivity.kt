package com.faigenbloom.testtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.faigenbloom.testtask.ui.common.WittixBottomNavigationBar
import com.faigenbloom.testtask.ui.common.WittixFloatingActionButton
import com.faigenbloom.testtask.ui.placeholder.PlaceholderPageRoute
import com.faigenbloom.testtask.ui.placeholder.placeholderPage
import com.faigenbloom.testtask.ui.send.SendPageRoute
import com.faigenbloom.testtask.ui.send.sendPage
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedBottomNavigationIndex by rememberSaveable { mutableStateOf(0) }
            val mainNavController = rememberNavController()
            TestTaskTheme {
                Scaffold(
                    bottomBar = {
                        WittixBottomNavigationBar(
                            selectedIndex = selectedBottomNavigationIndex,
                            onDestinationChanged = { index, destination ->
                                selectedBottomNavigationIndex = index
                                mainNavController.navigate(destination)
                            },
                        )
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = {
                        WittixFloatingActionButton(
                            onClicked = {
                                selectedBottomNavigationIndex = 2
                                mainNavController.navigate(PlaceholderPageRoute())
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
                                mainNavController.navigate(PlaceholderPageRoute())
                            },
                        )
                        placeholderPage(
                            padding = padding,
                        )
                    }
                }
            }
        }
    }
}
