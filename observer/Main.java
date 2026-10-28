public class Main {

    public static void main(String[] args) {

        WeatherStation station = new WeatherStation();
        CurrentConditionsDisplay display = new CurrentConditionsDisplay();
        TemperatureAlert alert = new TemperatureAlert();

        station.addObserver(display);
        station.addObserver(alert);

        System.out.println("--- Registering the same display again ---");
        station.addObserver(display);

        System.out.println();
        System.out.println("--- Setting 28.0 C ---");
        station.setTemperature(28.0);

        System.out.println();
        System.out.println("--- Setting 36.0 C ---");
        station.setTemperature(36.0);

        System.out.println();
        System.out.println("--- Removing the alert and setting 38.0 C ---");
        station.removeObserver(alert);
        station.setTemperature(38.0);

        System.out.println();
        System.out.println("--- Removing the alert again (no effect) and the display ---");
        station.removeObserver(alert);
        station.removeObserver(display);
        station.setTemperature(40.0);
        System.out.println("Station still running with no observers");

        System.out.println();
        System.out.println("--- New station with an average display ---");
        WeatherStation newStation = new WeatherStation();
        newStation.addObserver(new AverageTemperatureDisplay());
        newStation.setTemperature(20.0);
        newStation.setTemperature(30.0);
        newStation.setTemperature(40.0);
    }
}
