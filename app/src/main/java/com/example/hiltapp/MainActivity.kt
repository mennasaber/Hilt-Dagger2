package com.example.hiltapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var someClass: SomeClass //field injection

    @Inject
    @Impl1
    lateinit var someInterfaceImpl: SomeInterface

    @Inject
    @Impl2
    lateinit var someInterfaceImp2: SomeInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doSomeThing())
        println(someClass.doAnotherSomeThing())

        println(someInterfaceImpl.doSomeThing())
        println(someInterfaceImpl.doAnotherSomeThing())

        println(someInterfaceImp2.doSomeThing())
        println(someInterfaceImp2.doAnotherSomeThing())
    }
}

//constructor injection
class SomeClass @Inject constructor(private val anotherSomeClass: AnotherSomeClass) {
    fun doSomeThing(): String = "i do some thing!"
    fun doAnotherSomeThing(): String = anotherSomeClass.doAnotherSomeThing()
}

class AnotherSomeClass @Inject constructor() {
    fun doAnotherSomeThing(): String = "i do another some thing!"
}

class SomeInterfaceImpl @Inject constructor() : SomeInterface {
    override fun doSomeThing(): String = "i do thing1!"

    override fun doAnotherSomeThing(): String = "i do thing2!"

}

class SomeInterfaceImp2 @Inject constructor() : SomeInterface {
    override fun doSomeThing(): String = "i do thing12!"

    override fun doAnotherSomeThing(): String = "i do thing22!"

}

interface SomeInterface {
    fun doSomeThing(): String
    fun doAnotherSomeThing(): String
}

@Module
@InstallIn(ApplicationComponent::class)
class SomeProvider {
    @Singleton
    @Provides //@Binds
    @Impl1
    fun provideSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl()
    }

    @Singleton
    @Provides //@Binds
    @Impl2
    fun provideSomeInterface2(): SomeInterface {
        return SomeInterfaceImp2()
    }
}

//declare custom annotation
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2