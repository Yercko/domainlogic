package com.example.domainlogicandroidtest.domain.repository.cachepolicy;

public class NoCachePolicy implements CachePolicy {
    @Override
    public boolean isCacheValid() {
        return false;
    }
}
