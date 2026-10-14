import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TreeTypeFactory {

    private final Map<TreeTypeKey, TreeType> cache = new HashMap<>();

    public TreeType getTreeType(String name, String color, String textureId) {
        TreeTypeKey key = new TreeTypeKey(name, color, textureId);
        TreeType type = cache.get(key);
        if (type == null) {
            type = new TreeType(name, color, loadTexture(textureId));
            cache.put(key, type);
        }
        return type;
    }

    public int getCreatedTypesCount() {
        return cache.size();
    }

    private byte[] loadTexture(String textureId) {
        System.out.println("Loading texture: " + textureId);
        return new byte[100_000];
    }

    // Dedicated key with field-by-field equals/hashCode, so name/color/textureId
    // are compared by value and never ambiguously merged into a single string
    // (e.g. "Oak" + "GreenBark01" would collide with "OakGreen" + "Bark01").
    private static final class TreeTypeKey {

        private final String name;
        private final String color;
        private final String textureId;

        private TreeTypeKey(String name, String color, String textureId) {
            this.name = name;
            this.color = color;
            this.textureId = textureId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TreeTypeKey)) {
                return false;
            }
            TreeTypeKey other = (TreeTypeKey) obj;
            return name.equals(other.name) && color.equals(other.color) && textureId.equals(other.textureId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, color, textureId);
        }
    }
}
