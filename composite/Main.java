public class Main {

    public static void main(String[] args) {

        DocumentFile report = new DocumentFile("report.pdf", 500);
        System.out.println("File size: " + report.getSize() + " KB");

        Folder emptyFolder = new Folder("empty");
        System.out.println("Empty folder size: " + emptyFolder.getSize() + " KB");

        DocumentFile photo = new DocumentFile("photo.png", 1_500);
        DocumentFile invoice = new DocumentFile("invoice.pdf", 300);
        DocumentFile backup = new DocumentFile("backup.zip", 5_000);

        Folder photos = new Folder("photos");
        photos.add(photo);

        Folder invoices = new Folder("invoices");
        invoices.add(invoice);

        Folder documents = new Folder("documents");
        documents.add(invoices);
        documents.add(report);

        Folder root = new Folder("root");
        root.add(documents);
        root.add(photos);
        root.add(backup);

        System.out.println("Root folder size: " + root.getSize() + " KB");

        root.display("");
    }
}
