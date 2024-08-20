package com.oguz.coinapp.di

import com.oguz.coinapp.view.ListFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

val anothermodule = module {

    scope<ListFragment> {
        scoped(qualifier = named("hello")) {
            "Hello Kotlin"
        }

        scoped(qualifier = named("hi")) {
            "Hi Kotlin"
        }
    }
}