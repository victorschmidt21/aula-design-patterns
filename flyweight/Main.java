import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        TreeTypeFactory factory = new TreeTypeFactory();
        List<Tree> trees = new ArrayList<>();

        TreeType oakType = factory.getTreeType("Oak", "Green", "oak-bark-01");
        TreeType pineType = factory.getTreeType("Pine", "Dark green", "pine-bark-01");

        for (int i = 0; i < 100; i++) {
            // Small/large trees of the same type, to show scale varying freely
            // as extrinsic state without affecting the shared type or its cache key.
            double scale = (i % 2 == 0) ? 0.5 : 1.5;

            trees.add(new Tree(i, i * 2, scale, factory.getTreeType("Oak", "Green", "oak-bark-01")));
            trees.add(new Tree(i, i * 3, scale, factory.getTreeType("Pine", "Dark green", "pine-bark-01")));
        }

        for (Tree tree : trees) {
            tree.draw();
        }

        System.out.println();
        System.out.println("Trees created: " + trees.size());
        System.out.println("Tree types created: " + factory.getCreatedTypesCount());

        TreeType oakAgain = factory.getTreeType("Oak", "Green", "oak-bark-01");
        TreeType pineAgain = factory.getTreeType("Pine", "Dark green", "pine-bark-01");
        TreeType oakDifferentTexture = factory.getTreeType("Oak", "Green", "oak-bark-02");

        System.out.println("Oak type reused across equivalent requests: " + (oakType == oakAgain));
        System.out.println("Pine type reused across equivalent requests: " + (pineType == pineAgain));
        System.out.println("Different textureId produces a distinct type: " + (oakType == oakDifferentTexture));
    }
}
