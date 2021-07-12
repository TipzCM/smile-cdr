package services;

import models.Command;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class QueryRunnerTests {

    private QueryRunner queryRunner;

    private IFileReader reader;
    private IQueryService service;

    @Before
    public void init() {
        reader = Mockito.mock(IFileReader.class);
        service = new IQueryService() {
            @Override
            public boolean query(String familyName, Command<Bundle> command, boolean withCaching) {
                if (command != null) {
                    command.exec(null);
                }
                return false;
            }

            @Override
            public void reset() {

            }

            @Override
            public long getAverageResponseTime() {
                return 0;
            }
        };

        queryRunner = new QueryRunner(reader, service);
    }

    @Test
    public void runTest_test() {
        String path = "anything";
        List<String> names = new ArrayList<>();
        names.add("smith");
        names.add("johnson");

        Mockito.when(reader.readFile(Mockito.anyString()))
                .thenReturn(names);
        Command<Boolean> callback = Mockito.mock(Command.class);

        queryRunner.runTest(path, callback, true);

        Mockito.verify(callback, Mockito.timeout(1000))
            .exec(true);
    }
}
