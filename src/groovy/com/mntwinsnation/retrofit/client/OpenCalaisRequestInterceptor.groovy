package com.mntwinsnation.retrofit.client

import retrofit.RequestInterceptor
import retrofit.RequestInterceptor.RequestFacade

class OpenCalaisRequestInterceptor implements RequestInterceptor {

    String licenseId

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader('x-calais-licenseID', licenseId)
        request.addHeader('Accept', 'application/json')
    }
}
