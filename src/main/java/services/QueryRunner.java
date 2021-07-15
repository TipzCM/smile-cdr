package services;

import models.Command;

import java.util.List;

public class QueryRunner {

    private final IFileReader reader;

    private final IQueryService queryService;

    private int index = 0;

    public QueryRunner(IFileReader reader,
                       IQueryService qservice) {
        this.reader = reader;
        this.queryService = qservice;
    }

    public void runTest(String path,
                        Command<Boolean> onComplete,
                        boolean cache) {
        List<String> names = reader.readFile(path);

        int total = names.size();
        queryService.reset();
        index = 0;

        Command<Boolean> cmd = new Command<Boolean>() {

            @Override
            public void exec(Boolean res) {
                index++;
                if (index >= total) {
                    long averageTime = queryService.getAverageResponseTime();
                    System.out.println("Average time " + averageTime + "ms");
                    onComplete.exec(true);
                }
                else {
                    doQuery(names.get(index), this, cache);
                }
            }
        };
        doQuery(names.get(index), cmd, cache);
    }

    private void doQuery(String name, Command<Boolean> onComplete, boolean cache) {
        queryService.query(name, b -> {
            onComplete.exec(true);
        }, cache);
    }
}
