public class AppConfig {

    // volatile evita que uma thread enxergue uma instância parcialmente
    // construída por causa de reordenação de instruções pelo JIT/CPU.
    private static volatile AppConfig instance;

    private final String databaseUrl;
    private final String environment;

    private AppConfig() {
        this.databaseUrl = "jdbc:mysql://localhost/app";
        this.environment = "development";
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                // segunda checagem: outra thread pode ter criado a
                // instância enquanto esta esperava para entrar no bloco.
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getEnvironment() {
        return environment;
    }

}
