package com.awinas.learning.solid.walmartreturns.interfacesegregation.good;

public interface SellerCommunicator {
    void contactThirdPartySeller(String sellerId, String message);
}
