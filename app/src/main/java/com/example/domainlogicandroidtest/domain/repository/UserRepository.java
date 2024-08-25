package com.example.domainlogicandroidtest.domain.repository;

import android.content.Context;

import com.example.domainlogicandroidtest.domain.model.User;
import com.example.domainlogicandroidtest.domain.repository.cachepolicy.CachePolicy;
import com.example.domainlogicandroidtest.domain.repository.cachepolicy.NoCachePolicy;
import com.example.domainlogicandroidtest.domain.repository.cachepolicy.TimedCachePolicy;
import com.example.domainlogicandroidtest.domain.usecase.AsyncSearchUsers;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private CachePolicy cachePolicy;
    private final AsyncSearchUsers apiDataSource;
    private List<User> users = new ArrayList<>();

    public UserRepository(AsyncSearchUsers apiDataSource) {
        this.apiDataSource = apiDataSource;
        this.cachePolicy = new NoCachePolicy();
    }

    public void setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicy = cachePolicy;
    }

    public void getAsync(final AsyncSearchUsers.Listener listener, String username) {
        invalidateCacheIfNecessary(cachePolicy);

        if (!users.isEmpty()) {
            listener.onUsersReceived(users, true);
        } else {
            apiDataSource.getAsync(new AsyncSearchUsers.Listener() {
                @Override
                public void onUsersReceived(List<User> list, boolean isCached) {
                    users = list;
                    listener.onUsersReceived(list, isCached);
                }

                @Override
                public void onError(Exception e) {
                    listener.onError(e);
                }

                @Override
                public void onNoInternetAvailable() {
                    listener.onNoInternetAvailable();
                }
            }, username);
        }
    }

    private void invalidateCacheIfNecessary(CachePolicy cachePolicy) {
        if (!cachePolicy.isCacheValid()) {
            users.clear();
        }
    }
}
