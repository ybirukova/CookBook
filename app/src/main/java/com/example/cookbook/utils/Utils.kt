package com.example.cookbook.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addToComposite(compositeSubscription: CompositeDisposable) =
    compositeSubscription.add(this)