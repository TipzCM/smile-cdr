import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import interceptors.ClientInterceptor;
import models.Command;
import services.FileReader;
import services.QueryRunner;
import services.QueryService;

public class SampleClient {

    private static final String PATH = "last-names.txt";
    private static final int MAX_RUNS = 3;

    public static void main(String[] theArgs) {
        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");

        QueryService service = new QueryService(client,
                new ClientInterceptor());
        FileReader reader = new FileReader();

        QueryRunner runner = new QueryRunner(reader, service);

        Command<Boolean> onComplete = new Command<Boolean>() {
            private int index = 0;

            @Override
            public void exec(Boolean res) {
                if (index < MAX_RUNS) {
                    System.out.println("Starting run " + index);
                    index++;
                    runner.runTest(PATH, this, index == MAX_RUNS - 1);
                }
                else {
                    System.out.println("Complete!");
                }
            }
        };

        onComplete.exec(false);
    }
}
