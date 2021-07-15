package interceptors;

import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;

import java.io.IOException;

public class ClientInterceptor implements ICustomClientInterceptor {

    private int count;

    private long cumulativeResponseTimes;

    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {
        // System.out.println("Request " + iHttpRequest.getUri());
    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse) throws IOException {
        if (iHttpResponse.getAllHeaders().containsKey("content-location")) {
            return;
        }

        long ms = iHttpResponse.getRequestStopWatch().getMillis();
        iHttpResponse.getRequestStopWatch().endCurrentTask();

        cumulativeResponseTimes += ms;
        count += 1;
    }

    @Override
    public void reset() {
        count = 0;
        cumulativeResponseTimes = 0;
    }

    @Override
    public long getAverageResponseTime() {
        System.out.println("Count is " + count);
        if (count > 0) {
            return cumulativeResponseTimes / count;
        }
        else {
            return 0;
        }
    }
}
