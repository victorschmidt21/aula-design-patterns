public class Main {

    public static void main(String[] args) {

        ReportGenerator csvReport = new CsvReport();
        ReportGenerator htmlReport = new HtmlReport();
        ReportGenerator textReport = new TextReport();

        System.out.println("--- CSV report ---");
        csvReport.generate();

        System.out.println();
        System.out.println("--- HTML report ---");
        htmlReport.generate();

        System.out.println();
        System.out.println("--- Text report (overrides the afterGeneration hook) ---");
        textReport.generate();
    }
}
