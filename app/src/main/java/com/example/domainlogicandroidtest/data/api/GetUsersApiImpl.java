package com.example.domainlogicandroidtest.data.api;

import com.example.domainlogicandroidtest.data.api.model.UserApiEntry;
import com.example.domainlogicandroidtest.data.api.retrofit.GetUsersRetrofitRequest;
import com.example.domainlogicandroidtest.data.api.retrofit.RetrofitClient;
import com.example.domainlogicandroidtest.domain.model.User;
import com.example.domainlogicandroidtest.domain.usecase.AsyncSearchUsers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUsersApiImpl implements AsyncSearchUsers {
    private final GetUsersRetrofitRequest request;
    private Listener listener = new NullListener();

    public GetUsersApiImpl() {
        request = RetrofitClient.getInstance().create(GetUsersRetrofitRequest.class);
    }

    @Override
    public void getAsync(Listener listener, String username) {
        if (listener == null) {
            this.listener = new NullListener();
        } else {
            this.listener = listener;
        }

        Call<GetUsersResponse> call = request.searchUsers(username);
        call.enqueue(new Callback<GetUsersResponse>() {
            @Override
            public void onResponse(Call<GetUsersResponse> call, Response<GetUsersResponse> response) {
                List<User> users = (response.body() != null && response.body().getItems() != null) ?
                        mapToDomain(response.body()) :
                        new ArrayList<>();
                GetUsersApiImpl.this.listener.onUsersReceived(users, false);
            }

            @Override
            public void onFailure(Call<GetUsersResponse> call, Throwable t) {
                GetUsersApiImpl.this.listener.onError(new Exception(t));
            }
        });
    }

    private List<User> mapToDomain(GetUsersResponse apiResponse) {
        List<User> domainUsers = new ArrayList<>();
        for (UserApiEntry apiUser : apiResponse.getItems()) {
            domainUsers.add(new User(
                    apiUser.getLogin(),
                    apiUser.getId(),
                    apiUser.getAvatarUrl(),
                    apiUser.getHtmlUrl()
            ));
        }
        return domainUsers;
    }

    public static class NullListener implements Listener {
        @Override
        public void onUsersReceived(List<User> users, boolean isCached) {
        }

        @Override
        public void onError(Exception e) {
        }

        @Override
        public void onNoInternetAvailable() {
        }
    }
}