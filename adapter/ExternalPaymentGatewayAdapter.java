// Adapter: traduz a interface simples PaymentProcessor.pay(amount) para a
// interface exigida pelo serviço externo, makePayment(currency, value).
// A moeda fica fixada aqui na criação do adapter, então o OrderService
// nunca precisa saber qual moeda está sendo usada.
public class ExternalPaymentGatewayAdapter implements PaymentProcessor {

    private final ExternalPaymentGateway gateway;
    private final Currency currency;

    public ExternalPaymentGatewayAdapter(Currency currency) {
        this(new ExternalPaymentGateway(), currency);
    }

    public ExternalPaymentGatewayAdapter(ExternalPaymentGateway gateway, Currency currency) {
        this.gateway = gateway;
        this.currency = currency;
    }

    @Override
    public void pay(double amount) {
        gateway.makePayment(currency.name(), amount);
    }
}
