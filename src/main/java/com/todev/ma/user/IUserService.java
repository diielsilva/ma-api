package com.todev.ma.user;

import com.todev.ma.shared.ModelNotFoundException;

/** This interface is used to share everything within the User Module without making anything public.
 *
 */
public interface IUserService {
    User findByEmail(String email) throws ModelNotFoundException;
}
