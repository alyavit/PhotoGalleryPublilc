package com.example.photogallery.app

import android.app.Application
import androidx.room.Room
import com.example.photogallery.data.PhotoDao
import com.example.photogallery.data.PhotoDatabase
import com.example.photogallery.service.PhotoGalleryApiService
import com.example.photogallery.service.PhotoGalleryDataService
import com.example.photogallery.ui.viewmodel.MainViewModel
import com.example.photogallery.usecase.GetFavoritePhotoUseCase
import com.example.photogallery.usecase.GetRemotePhotoGalleryUseCase
import com.example.photogallery.usecase.RemoveFavoritePhotoUseCase
import com.example.photogallery.usecase.SaveFavoriteUseCase
import okhttp3.OkHttpClient
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Application class
class PhotoGalleryApp : Application(), DIAware
{
    //Dependency Injection in app
    override val di by DI.lazy{
        import(androidXModule(this@PhotoGalleryApp))
        bind<PhotoDatabase>() with singleton {
            Room.databaseBuilder(instance<Application>(), PhotoDatabase::class.java, "movies.db")
                .build()
        }
        //Retrofit
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl("https://api.pexels.com/v1/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        // PhotoGalleryApiService
        bind<PhotoGalleryApiService>() with singleton {
            instance<Retrofit>().create(PhotoGalleryApiService::class.java)
        }

        bind<PhotoDao>() with singleton {
            instance<PhotoDatabase>().photoDao()
        }

        // PhotoGalleryDataService
        bind<PhotoGalleryDataService>() with singleton {
            PhotoGalleryDataService(instance(), instance())
        }

        // UseCases
        bind<GetRemotePhotoGalleryUseCase>() with singleton {
            GetRemotePhotoGalleryUseCase(instance())
        }
        bind<GetFavoritePhotoUseCase>() with singleton {
            GetFavoritePhotoUseCase(instance())
        }
        bind<SaveFavoriteUseCase>() with singleton {
            SaveFavoriteUseCase(instance())
        }
        bind<RemoveFavoritePhotoUseCase>() with singleton {
            RemoveFavoritePhotoUseCase(instance())
        }

        //ViewModels
        bind<MainViewModel>() with singleton{
            MainViewModel(instance(), instance(), instance(), instance())
        }
    }
}