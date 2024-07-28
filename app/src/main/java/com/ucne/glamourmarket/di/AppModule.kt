package com.ucne.glamourmarket.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ucne.glamourmarket.data.GlamourAPI
import com.ucne.glamourmarket.data.repository.CarritosRepository
import com.ucne.glamourmarket.data.repository.ComprasRepository
import com.ucne.glamourmarket.data.repository.ProductosRepository
import com.ucne.glamourmarket.data.repository.UsuariosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun providesGlamourApi(moshi: Moshi): GlamourAPI {
        return Retrofit.Builder()
            .baseUrl("http://glamourmarketapi.somee.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GlamourAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUsuariosRepository(api: GlamourAPI): UsuariosRepository {
        return UsuariosRepository(api)
    }

    @Provides
    @Singleton
    fun provideProductosRepository(api: GlamourAPI): ProductosRepository {
        return ProductosRepository(api)
    }

    @Provides
    @Singleton
    fun provideComprasRepository(api: GlamourAPI): ComprasRepository {
        return ComprasRepository(api)
    }

    @Provides
    @Singleton
    fun provideCarritosRepository(api: GlamourAPI): CarritosRepository {
        return CarritosRepository(api)
    }
}