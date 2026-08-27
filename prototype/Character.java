import java.util.ArrayList;
import java.util.List;

public class Character implements Prototype<Character> {

    private String name;
    private String type;
    private int health;
    private int attack;
    private int defense;
    private String weapon;
    private List<String> skills;

    public Character(
        String name,
        String type,
        int health,
        int attack,
        int defense,
        String weapon,
        List<String> skills
    ) {
        this.name = name;
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.weapon = weapon;
        this.skills = new ArrayList<>(skills);
    }

    // Copy constructor used by clone() to build a fully independent instance.
    private Character(Character original) {
        this.name = original.name;
        this.type = original.type;
        this.health = original.health;
        this.attack = original.attack;
        this.defense = original.defense;
        this.weapon = original.weapon;
        // New ArrayList instance -> deep copy of the list itself, so the
        // clone and the prototype never share the same skills collection.
        this.skills = new ArrayList<>(original.skills);
    }

    @Override
    public Character clone() {
        return new Character(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void addSkill(String skill) {
        this.skills.add(skill);
    }

    public void removeSkill(String skill) {
        this.skills.remove(skill);
    }

    @Override
    public String toString() {
        return "Character{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", health=" + health +
            ", attack=" + attack +
            ", defense=" + defense +
            ", weapon='" + weapon + '\'' +
            ", skills=" + skills +
            '}';
    }

}
