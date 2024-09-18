package com.myproject.cardshop.service;

import com.myproject.cardshop.security.auth.AuthenticateRequset;
import com.myproject.cardshop.security.auth.AuthenticationResponse;
import com.myproject.cardshop.security.auth.RegisterRequset;

public interface AuthenticationService {

	AuthenticationResponse register(RegisterRequset request);

	AuthenticationResponse authenticate(AuthenticateRequset request);

}
