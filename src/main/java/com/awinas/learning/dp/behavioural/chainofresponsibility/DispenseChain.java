package com.awinas.learning.dp.behavioural.chainofresponsibility;

public interface DispenseChain {

	void setNextChain(DispenseChain nextChain);

	void dispense(Currency cur);
}