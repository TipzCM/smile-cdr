package interceptors;

import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ca.uhn.fhir.util.StopWatch;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class ClientInterceptorTest {

    private final ClientInterceptor interceptor = new ClientInterceptor();

    @Test
    public void interceptResponse_test() throws IOException {
        IHttpResponse res = Mockito.mock(IHttpResponse.class);
        StopWatch sw = Mockito.mock(StopWatch.class);
        Mockito.when(res.getRequestStopWatch())
                .thenReturn(sw);

        interceptor.interceptResponse(res);

        Assert.assertTrue(interceptor.getAverageResponseTime() > 0);
    }
}
