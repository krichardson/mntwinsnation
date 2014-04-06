import com.mntwinsnation.retrofit.RestClientBuilder
import com.mntwinsnation.retrofit.client.OpenCalaisApi
import com.mntwinsnation.retrofit.client.OpenCalaisRequestInterceptor

// Place your Spring DSL code here
beans = {

    //restClientBuilder(RestClientBuilder)

    openCalaisApiRequestInterceptor(OpenCalaisRequestInterceptor) {
        licenseId = '${services.opencalais.licenseId}'
    }

    openCalaisApi(RestClientBuilder,
            OpenCalaisApi,
            '${services.opencalais.serviceUrl}',
            ref('openCalaisApiRequestInterceptor')) { bean ->
        bean.factoryMethod = 'buildClient'
    }

}
