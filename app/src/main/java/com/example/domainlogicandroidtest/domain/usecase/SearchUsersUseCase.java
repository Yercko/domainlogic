package com.example.domainlogicandroidtest.domain.usecase;

import com.example.domainlogicandroidtest.domain.model.User;

import java.util.List;

public interface SearchUsersUseCase {
    List<User> searchUsers(String username) throws Exception;

}
