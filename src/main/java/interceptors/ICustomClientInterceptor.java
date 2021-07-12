package interceptors;

import ca.uhn.fhir.rest.client.api.IClientInterceptor;

public interface ICustomClientInterceptor extends IClientInterceptor {
    void reset();
    long getAverageResponseTime();
}
