public class Main {

    public static void main(String[] args) {

        OrderService creditCardOrder = new OrderService(new CreditCardPayment());
        creditCardOrder.checkout(150.00);

        OrderService brlOrder = new OrderService(new ExternalPaymentGatewayAdapter(Currency.BRL));
        brlOrder.checkout(150.00);

        OrderService usdOrder = new OrderService(new ExternalPaymentGatewayAdapter(Currency.USD));
        usdOrder.checkout(150.00);

        OrderService eurOrder = new OrderService(new ExternalPaymentGatewayAdapter(Currency.EUR));
        eurOrder.checkout(150.00);
    }
}
