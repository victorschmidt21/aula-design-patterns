public class Main {

    public static void main(String[] args) {

        ChatMediator room = new ChatRoom();

        ChatUser ana = new ChatUser("Ana", room);
        ChatUser bruno = new ChatUser("Bruno", room);
        ChatUser carla = new ChatUser("Carla", room);

        room.register(ana);
        room.register(bruno);
        room.register(carla);

        System.out.println();
        System.out.println("--- Registering Bruno again ---");
        room.register(bruno);

        System.out.println();
        System.out.println("--- Ana sends a message ---");
        ana.send("Hello, everyone!");

        System.out.println();
        System.out.println("--- Diego joins and Bruno sends a message ---");
        ChatUser diego = new ChatUser("Diego", room);
        room.register(diego);
        bruno.send("Hello, Ana!");

        System.out.println();
        System.out.println("--- Eduardo is associated with the room but not registered ---");
        ChatUser eduardo = new ChatUser("Eduardo", room);
        eduardo.send("Can anyone hear me?");

        System.out.println();
        System.out.println("--- Another user named Carla tries to register ---");
        ChatUser otherCarla = new ChatUser("Carla", room);
        room.register(otherCarla);

        System.out.println();
        System.out.println("--- Private messages ---");
        ana.sendPrivate("Carla", "Are you free later?");
        ana.sendPrivate("Fernanda", "Hi there!");
        ana.sendPrivate("Ana", "Note to self");
    }
}
