import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        UserService userService = new UserService();
        userService.connect();

        ReportService reportService = new ReportService();
        reportService.generate();

        AppConfig configFromUserService = AppConfig.getInstance();
        AppConfig configFromReportService = AppConfig.getInstance();
        System.out.println("UserService e ReportService compartilham a mesma instância? "
            + (configFromUserService == configFromReportService));

        demonstrateConcurrentAccess();
    }

    // Desafio adicional: várias threads pedindo a instância ao mesmo tempo.
    private static void demonstrateConcurrentAccess() throws InterruptedException {
        int threadCount = 20;
        AtomicReferenceArray<AppConfig> results = new AtomicReferenceArray<>(threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int index = i;
            executor.submit(() -> results.set(index, AppConfig.getInstance()));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        AppConfig first = results.get(0);
        boolean allSameInstance = true;
        for (int i = 1; i < threadCount; i++) {
            if (results.get(i) != first) {
                allSameInstance = false;
                break;
            }
        }

        System.out.println("Todas as " + threadCount
            + " threads receberam a mesma instância? " + allSameInstance);
        System.out.println("Identidade da instância (hash): " + System.identityHashCode(first));
    }

}
