package com.scally_p.themoviedb.di

import com.scally_p.themoviedb.data.local.db.DetailsDbHelper
import com.scally_p.themoviedb.data.local.db.GenresDbHelper
import com.scally_p.themoviedb.data.local.db.ImagesDbHelper
import com.scally_p.themoviedb.data.local.db.MoviesDbHelper
import com.scally_p.themoviedb.data.local.repository.DetailsRepository
import com.scally_p.themoviedb.data.local.repository.GenresRepository
import com.scally_p.themoviedb.data.local.repository.ImagesRepository
import com.scally_p.themoviedb.data.local.repository.MoviesRepository
import com.scally_p.themoviedb.ui.details.DetailsViewModel
import com.scally_p.themoviedb.ui.launcher.GenresViewModel
import com.scally_p.themoviedb.ui.main.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getModules(): List<Module> = listOf(
    genresDbHelperModule,
    moviesDbHelperModule,
    detailsDbHelperModule,
    imagesDbHelperModule,
    genresViewModel,
    moviesViewModel,
    detailsViewModel,
    genresRepository,
    moviesRepository,
    detailsRepository,
    imagesRepository,
)

//Db Helpers
val genresDbHelperModule = module { single { GenresDbHelper() } }
val moviesDbHelperModule = module { single { MoviesDbHelper() } }
val detailsDbHelperModule = module { single { DetailsDbHelper() } }
val imagesDbHelperModule = module { single { ImagesDbHelper() } }

//ViewModels
val genresViewModel = module { viewModel { GenresViewModel() } }
val moviesViewModel = module { viewModel { MoviesViewModel() } }
val detailsViewModel = module { viewModel { DetailsViewModel() } }

//Repositories
val genresRepository = module { factory { GenresRepository() } }
val moviesRepository = module { factory { MoviesRepository() } }
val detailsRepository = module { factory { DetailsRepository() } }
val imagesRepository = module { factory { ImagesRepository() } }