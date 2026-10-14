import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Image> gallery = new ArrayList<>();

        gallery.add(new ImageProxy("forest.jpg"));
        gallery.add(new ImageProxy("beach.jpg"));
        gallery.add(new ImageProxy("mountain.jpg"));

        System.out.println("Gallery created");

        System.out.println();
        System.out.println("--- Displaying forest.jpg twice ---");
        gallery.get(0).display();
        gallery.get(0).display();

        System.out.println();
        System.out.println("--- Displaying beach.jpg ---");
        gallery.get(1).display();

        System.out.println();
        System.out.println("--- mountain.jpg was never displayed, so it was never loaded ---");

        System.out.println();
        System.out.println("--- Protected access to a fourth image ---");
        Image restrictedImage = new ProtectedImageProxy(new ImageProxy("vault.jpg"), false);
        restrictedImage.display();

        Image allowedImage = new ProtectedImageProxy(new ImageProxy("sunset.jpg"), true);
        allowedImage.display();
    }
}
