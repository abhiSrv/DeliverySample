package com.abhi.deliverylist.rx

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxImmediateSchedulerRule : TestRule {

    private val immediate = Schedulers.trampoline()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {


                RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }


                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }

            }
        }
    }
}