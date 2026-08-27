public class Main {

    public static void main(String[] args) {

        CharacterPrototypeRegistry registry = new CharacterPrototypeRegistry();

        // Each new character starts as a clone of a prototype, only the
        // specific fields that differ (name, weapon, skills...) are changed.
        Character warrior = registry.get("WARRIOR");
        warrior.setName("Arthos");

        Character anotherWarrior = registry.get("WARRIOR");
        anotherWarrior.setName("Brakus");
        anotherWarrior.setWeapon("Axe");

        Character mage = registry.get("MAGE");
        mage.setName("Elara");
        mage.addSkill("Ice Storm");

        Character archer = registry.get("ARCHER");
        archer.setName("Robin");
        archer.getSkills().remove("Multishot");

        System.out.println(warrior);
        System.out.println(anotherWarrior);
        System.out.println(mage);
        System.out.println(archer);

        // Proof that mutating a clone's skill list never touches the
        // original prototype stored in the registry (deep copy in action).
        Character mageFromPrototypeAgain = registry.get("MAGE");
        System.out.println("Fresh mage clone still has original skills: "
            + mageFromPrototypeAgain.getSkills());
    }

}
