package at.styrialabs.bucketlist.di.module

import at.styrialabs.bucketlist.database.AppDatabase
import at.styrialabs.bucketlist.repository.BucketListElementRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesProductRepository(appDatabase: AppDatabase): BucketListElementRepository {
        return appDatabase.bucketListElementRepository()
    }

}