package com.scally_p.themoviedb.di

import com.scally_p.themoviedb.data.local.db.GenresDbHelper
import com.scally_p.themoviedb.data.local.db.MoviesDbHelper
import org.koin.core.module.Module
import org.koin.dsl.module

fun getModules(): List<Module> = listOf(
    moviesDbHelperModule,
    genresDbHelperModule,
)

//Db Helpers
val moviesDbHelperModule = module { single { MoviesDbHelper() } }
val genresDbHelperModule = module { single { GenresDbHelper() } }
