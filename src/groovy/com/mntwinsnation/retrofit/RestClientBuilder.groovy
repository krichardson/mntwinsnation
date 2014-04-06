package com.mntwinsnation.retrofit

import retrofit.RequestInterceptor
import retrofit.RestAdapter

class RestClientBuilder {

    public static <T> T buildClient(final Class<T> client,
                             final String serviceUrl,
                             RequestInterceptor requestInterceptor = null) {

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(serviceUrl)
        if (requestInterceptor) {
            builder.setRequestInterceptor(requestInterceptor)
        }
        RestAdapter restAdapter = builder.build()

        return restAdapter.create(client)
    }

}
