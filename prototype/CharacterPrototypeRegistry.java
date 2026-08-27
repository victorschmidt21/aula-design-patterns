import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterPrototypeRegistry {

    private final Map<String, Character> prototypes = new HashMap<>();

    public CharacterPrototypeRegistry() {
        registerDefaultPrototypes();
    }

    private void registerDefaultPrototypes() {
        List<String> warriorSkills = new ArrayList<>();
        warriorSkills.add("Slash");
        warriorSkills.add("Shield Block");
        prototypes.put("WARRIOR", new Character(
            "Warrior Prototype", "Warrior", 100, 30, 20, "Sword", warriorSkills
        ));

        List<String> mageSkills = new ArrayList<>();
        mageSkills.add("Fireball");
        mageSkills.add("Teleport");
        prototypes.put("MAGE", new Character(
            "Mage Prototype", "Mage", 70, 40, 10, "Staff", mageSkills
        ));

        List<String> archerSkills = new ArrayList<>();
        archerSkills.add("Precise Shot");
        archerSkills.add("Multishot");
        prototypes.put("ARCHER", new Character(
            "Archer Prototype", "Archer", 80, 35, 15, "Bow", archerSkills
        ));
    }

    public void registerPrototype(String key, Character prototype) {
        prototypes.put(key.toUpperCase(), prototype);
    }

    public Character get(String key) {
        Character prototype = prototypes.get(key.toUpperCase());
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype registered for type: " + key);
        }
        // Clients always receive a clone, never the stored prototype itself.
        return prototype.clone();
    }

}
