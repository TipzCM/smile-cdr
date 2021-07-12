package services;

import models.Command;
import org.hl7.fhir.r4.model.Bundle;

public interface IQueryService {

    boolean query(String familyName, Command<Bundle> command, boolean withCaching);

    void reset();

    long getAverageResponseTime();
}
