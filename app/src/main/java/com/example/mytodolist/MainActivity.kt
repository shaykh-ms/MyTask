package com.example.mytodolist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytodolist.presentation.task_listing.TaskListingScreen
import com.example.mytodolist.presentation.task_listing.TaskListingViewModel
import com.example.mytodolist.ui.theme.MyTodoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TaskListingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isReady
            }
         /*   setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()

            }*/

        }
        enableEdgeToEdge()
        setContent {
            MyTodoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskListingScreen(context = this)
                }
            }
        }
    }
}