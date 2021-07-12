package services;

import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import interceptors.ICustomClientInterceptor;
import models.Command;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class QueryService implements IQueryService {

    // better to use DI, but...
    private final IGenericClient client;

    private final ICustomClientInterceptor interceptor;

    public QueryService(IGenericClient client,
                        ICustomClientInterceptor interceptor) {
        this.client = client;
        this.interceptor = interceptor;

        // register interceptor
        client.registerInterceptor(new LoggingInterceptor(false));
        client.registerInterceptor(interceptor);
        System.out.println("Init complete");
    }

    @Override
    public boolean query(String familyName, Command<Bundle> command, boolean withCaching) {

        CacheControlDirective directive = new CacheControlDirective();
        directive.setNoCache(!withCaching);

        // Search for Patient resources
        Bundle response = client
                .search()
                .forResource("Patient")
                .where(Patient.FAMILY.matches().value(familyName))
                .returnBundle(Bundle.class)
                .cacheControl(directive)
                .execute();

        command.exec(response);
        return true;
    }

    @Override
    public void reset() {
        interceptor.reset();
    }

    @Override
    public long getAverageResponseTime() {
        return interceptor.getAverageResponseTime();
    }
}
