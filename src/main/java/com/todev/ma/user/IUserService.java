package com.todev.ma.user;

import com.todev.ma.shared.ModelNotFoundException;
import org.springframework.security.core.Authentication;

/** This interface is used to share everything within the User Module without making anything public.
 *
 */
public interface IUserService {
    User findByEmail(String email) throws ModelNotFoundException;

    User findByCpf(String cpf) throws ModelNotFoundException;

    User getSessionOwner(Authentication session) throws ModelNotFoundException;
}
