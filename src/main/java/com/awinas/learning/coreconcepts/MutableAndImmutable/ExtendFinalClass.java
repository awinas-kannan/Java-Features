package com.awinas.learning.coreconcepts.MutableAndImmutable;

/**
 * Demonstrates that FINAL classes cannot be extended.
 * Integer is declared as: public final class Integer extends Number
 *
 * The following would cause a compile error:
 *   "cannot inherit from final java.lang.Integer"
 *
 * public class ExtendFinalClass extends Integer {}
 */
public class ExtendFinalClass {
    // Intentionally left empty — extending a final class like Integer is not allowed in Java.
}
