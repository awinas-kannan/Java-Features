package com.awinas.learning.oop;

/**
 * Demonstrates that a class with DEFAULT (package-private) access modifier
 * CANNOT be extended from a different package.
 *
 * DefaultClass is declared as: class DefaultClass (no public modifier)
 * so it is only visible within its own package: com.awinas.learning.oop.accessmodifier
 *
 * The following would cause: "DefaultClass is not public in ...accessmodifier; cannot be accessed from outside package"
 *
 * import com.awinas.learning.oop.accessmodifier.DefaultClass;
 * public class ExtendingDefaultClass extends DefaultClass {}
 */
public class ExtendingDefaultClass {
    // Intentionally left empty — default-access classes cannot be extended across packages.
}
