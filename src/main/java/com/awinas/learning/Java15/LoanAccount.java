package com.awinas.learning.Java15;

public sealed class LoanAccount extends Account permits HomeloanAccount,AutoloanAccount {

}

final class HomeloanAccount extends LoanAccount {
}

final class AutoloanAccount extends LoanAccount {
}