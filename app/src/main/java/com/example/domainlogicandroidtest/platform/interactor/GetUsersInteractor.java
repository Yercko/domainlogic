package com.example.domainlogicandroidtest.platform.interactor;

import com.example.domainlogicandroidtest.data.api.GetUsersApiImpl;
import com.example.domainlogicandroidtest.domain.model.User;
import com.example.domainlogicandroidtest.domain.repository.UserRepository;
import com.example.domainlogicandroidtest.domain.usecase.AsyncSearchUsers;

import java.util.List;
import javax.inject.Inject;
public class GetUsersInteractor implements Interactor {
    private final UserRepository userRepository;
    private final Executor executor;
    private final MainThread mainThread;
    private AsyncSearchUsers.Listener listener;
    private String username;

    @Inject
    public GetUsersInteractor(UserRepository userRepository, Executor executor, MainThread mainThread) {
        this.userRepository = userRepository;
        this.executor = executor;
        this.mainThread = mainThread;
    }

    public void execute(AsyncSearchUsers.Listener listener, String username) {
        this.listener = listener;
        this.username = username;
        executor.run(this);
    }

    @Override
    public void run() {
        userRepository.getAsync(new AsyncSearchUsers.Listener() {
            @Override
            public void onUsersReceived(final List<User> users, final boolean isCached) {
                mainThread.post(() -> listener.onUsersReceived(users, isCached));
            }

            @Override
            public void onError(final Exception e) {
                mainThread.post(() -> listener.onError(e));
            }

            @Override
            public void onNoInternetAvailable() {
                mainThread.post(() -> listener.onNoInternetAvailable());
            }
        }, username);
    }
}