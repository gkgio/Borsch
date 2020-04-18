package com.gkgio.borsch.di

import com.gkgio.data.chats.ChatServiceImpl
import com.gkgio.domain.chats.*
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

        @Binds
        abstract fun orderChatUseCase(arg: OrderChatUseCaseImpl): OrderChatUseCase
    }
}