package com.awinas.learning.dp.structural.facade;

public class FacadeDemo {

    public static void main(String[] args) {

        // Client only uses OrderFacade — never touches any subsystem directly
        OrderFacade orderFacade = new OrderFacade();

        // ---- Place Order ----
        System.out.println("======== Place Order ========");
        orderFacade.placeOrder(
                "ORD-5001",
                "PROD-iPhone15",
                1,
                79999.0,
                "UPI",
                "123, Anna Nagar, Chennai - 600040",
                "john@example.com"
        );

        // ---- Cancel Order ----
        System.out.println("\n======== Cancel Order ========");
        orderFacade.cancelOrder(
                "ORD-5001",
                "PROD-iPhone15",
                1,
                "TXN-001",
                79999.0,
                "TRK-ORD-5001",
                "john@example.com"
        );
    }
}
