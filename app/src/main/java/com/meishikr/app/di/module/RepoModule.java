package com.meishikr.app.di.module;

import com.meishikr.app.model.api.AuthApi;
import com.meishikr.app.model.api.BlogApi;
import com.meishikr.app.model.api.LampApi;
import com.meishikr.app.domain.repository.AuthRepo;
import com.meishikr.app.domain.repository.BlogRepo;
import com.meishikr.app.domain.repository.CurrentUserRepo;
import com.meishikr.app.domain.repository.LampRepo;
import com.meishikr.app.domain.repository.PhotoRepo;
import com.meishikr.app.model.repository.AuthRepository;
import com.meishikr.app.model.repository.BlogRepository;
import com.meishikr.app.model.repository.CurrentUserRepository;
import com.meishikr.app.model.repository.LampRepository;
import com.meishikr.app.model.repository.PhotoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by yinhang on 16/11/4.
 */

@Module(includes = NetworkModule.class)
public class RepoModule {

    @Provides
    @Singleton
    public AuthApi authApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    @Provides
    @Singleton
    public BlogApi blogApi(Retrofit retrofit) {
        return retrofit.create(BlogApi.class);
    }

    @Provides
    @Singleton
    public LampApi lampApi(Retrofit retrofit) {
        return retrofit.create(LampApi.class);
    }

    @Provides
    @Singleton
    public CurrentUserRepo currentUserRepo(CurrentUserRepository currentUserRepository) {
        return currentUserRepository;
    }

    @Provides
    @Singleton
    public AuthRepo authRepo(AuthRepository authRepository) {
        return authRepository;
    }

    @Provides
    @Singleton
    public BlogRepo blogRepo(BlogRepository blogRepository) {
        return blogRepository;
    }

    @Provides
    @Singleton
    public LampRepo lampRepo(LampRepository lampRepository) {
        return lampRepository;
    }

    @Provides
    @Singleton
    public PhotoRepo photoRepo(PhotoRepository photoRepository) {
        return photoRepository;
    }
}
