package com.domain.usecases;

import rx.Observable;

public interface DeleteUserAgent {
  Observable<Boolean> deleteUser(String name);
}
