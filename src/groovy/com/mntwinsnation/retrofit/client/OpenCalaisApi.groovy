package com.mntwinsnation.retrofit.client

import retrofit.http.Body
import retrofit.http.Header
import retrofit.http.POST


public interface OpenCalaisApi {

    /**
     * @param content       The content to be analyzed
     * @param contentType   The type of the content being submitted for semantics
     *                      e.g., text/raw or text/html
     * @return              The semantics results (json)
     */
    @POST('/tag/rs/enrich')
    Map analyzeContent(@Body content, @Header("Content-Type") String contentType)

}