package com.gkgio.borsch.di

import com.gkgio.data.cookers.CookersServiceImpl
import com.gkgio.data.support.ChatServiceImpl
import com.gkgio.domain.cookers.*
import com.gkgio.domain.support.ChatService
import com.gkgio.domain.support.SupportChatUseCase
import com.gkgio.domain.support.SupportChatUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.Binds
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [ChatModule.BindsModule::class])
class ChatModule {
    @Provides
    fun chatServiceApi(retrofit: Retrofit): ChatServiceImpl.ChatServiceApi =
        retrofit.create()

    @Module
    abstract inner class BindsModule {

        @Binds
        abstract fun chatService(arg: ChatServiceImpl): ChatService

        @Binds
        abstract fun supportChatUseCase(arg: SupportChatUseCaseImpl): SupportChatUseCase
    }
}