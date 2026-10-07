package com.example.setlist.Nav

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.SaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.example.setlist.AddArtist.AddArtistRoute
import com.example.setlist.ArtistDetails.ArtistDetailRoute
import com.example.setlist.ArtistDetails.ArtistDetailScreen
import com.example.setlist.ArtistDetails.ArtistDetailViewModel
import com.example.setlist.ConfirmDeleteDialog
import com.example.setlist.Containers.ArtistApp
import com.example.setlist.Screens.ArtistList.ArtistListRoute
import com.example.setlist.Screens.ArtistList.ArtistListScreen
import com.example.setlist.Screens.ArtistList.ArtistListViewModel

@Composable
fun AppNav() {
    val repository = (LocalContext.current.applicationContext as ArtistApp).container.repository
    val backStack = rememberNavBackStack(AppKey.ArtistList)

    NavDisplay(
        backStack = backStack,
        onBack =  { backStack.removeLastOrNull() },
        sceneStrategy = remember { DialogSceneStrategy<NavKey>() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        transitionSpec = {
            slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
        },
        popTransitionSpec = {
            slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
        },
        entryProvider = entryProvider {
            entry<AppKey.ArtistList> {
                ArtistListRoute(
                    onAddClick = { backStack.add(AppKey.AddArtist) },
                    onArtistClick = { artist -> backStack.add(AppKey.ArtistDetail(artist.id)) }
                )
            }

            entry<AppKey.AddArtist> {
                AddArtistRoute(onAddClick = { backStack.removeLastOrNull() })
            }

            entry<AppKey.ArtistDetail> { key ->
                ArtistDetailRoute(
                    artistId = key.id,
                    onBack = { backStack.removeLastOrNull() },
                    onDelete = { backStack.add(AppKey.ConfirmDelete(key.id)) }
                )
            }

            entry<AppKey.ConfirmDelete>(
                metadata = DialogSceneStrategy.dialog(DialogProperties())
            ) { key ->
                val viewModel: ArtistDetailViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer { ArtistDetailViewModel(key.id, repository) }
                    }
                )
                ConfirmDeleteDialog(
                    onConfirm = {
                        viewModel.deleteArtist()
                        backStack.removeLastOrNull() // close dialog
                        backStack.removeLastOrNull() // leave detail
                    },
                    onDismiss = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}


