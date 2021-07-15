package interceptors;

import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;

import java.util.List;

public class ClientInterceptor implements ICustomClientInterceptor {

    private static final String XHEADERREQ = "X-Request-ID";

    private int count;

    private long cumulativeResponseTimes;

    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {
        iHttpRequest.addHeader(XHEADERREQ, iHttpRequest.getUri().contains("metadata") + "");
    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse) {
        List<String> reqId = iHttpResponse.getHeaders(XHEADERREQ);
        if (reqId != null && reqId.contains("true")) {
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
