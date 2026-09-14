// Classe de outra equipe: interface fixa, não pode ser alterada.
public class ExternalPaymentGateway {

    public void makePayment(String currency, double value) {
        System.out.println("External payment approved: " + currency + " " + value);
    }
}
