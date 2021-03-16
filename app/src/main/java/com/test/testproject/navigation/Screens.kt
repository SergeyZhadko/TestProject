package com.test.testproject.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.test.testproject.ui.screens.PopularMoviesListFragment

object Screens {

    val popularMovies = FragmentScreen("PopularMovies") { PopularMoviesListFragment.newInstance() }

}