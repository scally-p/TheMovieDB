package com.scally_p.themoviedb.di

import com.scally_p.themoviedb.data.local.IMoviesRepository
import com.scally_p.themoviedb.data.local.MoviesRepository
import com.scally_p.themoviedb.data.local.db.MoviesDbHelper
import org.koin.core.module.Module
import org.koin.dsl.module

fun getModules(): List<Module> = listOf(
    moviesDbHelperModule,
    moviesRepository
)

//Db Helpers
val moviesDbHelperModule = module { single { MoviesDbHelper() } }

//Repositories
val moviesRepository = module { factory<IMoviesRepository> { MoviesRepository() } }
