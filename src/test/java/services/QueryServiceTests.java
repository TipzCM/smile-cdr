package services;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.IQuery;
import ca.uhn.fhir.rest.gclient.IUntypedQuery;
import interceptors.ICustomClientInterceptor;
import models.Command;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class QueryServiceTests {

    private ICustomClientInterceptor interceptor;

    private IGenericClient client;

    private QueryService queryService;

    @Before
    public void init() {
        interceptor = Mockito.mock(ICustomClientInterceptor.class);
        client = Mockito.mock(IGenericClient.class);

        queryService = new QueryService(client, interceptor);
    }

    @Test
    public void query_test() {
        String path = "anything";

        Bundle bundle = Mockito.mock(Bundle.class);
        IUntypedQuery bq = Mockito.mock(IUntypedQuery.class, Mockito.RETURNS_SELF);
        IQuery q = Mockito.mock(IQuery.class, Mockito.RETURNS_SELF);
        Mockito.when(q.execute())
                .thenReturn(bundle);
        Mockito.when(bq.forResource(Mockito.anyString()))
                .thenReturn(q);
        Mockito.when(client.search()).thenReturn(bq);
        Command<Bundle> c = Mockito.mock(Command.class);

        queryService.query(path, c, true);

        // verify
        Mockito.verify(c, Mockito.timeout(1000)).exec(bundle);

        Mockito.verify(client).search();
    }

    @Test
    public void reset_test() {
        queryService.reset();

        Mockito.verify(interceptor).reset();
    }

    @Test
    public void getAverageResponseTime_test() {
        long exp = 1l;
        Mockito.when(interceptor.getAverageResponseTime())
                .thenReturn(exp);

        long actual = queryService.getAverageResponseTime();
        Assert.assertEquals(exp, actual);
    }
}
