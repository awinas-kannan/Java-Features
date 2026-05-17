package com.awinas.learning.solid.walmartreturns;

/*
 * ╔══════════════════════════════════════════════════════════════════════════════╗
 * ║                                                                              ║
 * ║              SOLID PRINCIPLES — WALMART RETURNS DOMAIN GUIDE                 ║
 * ║                                                                              ║
 * ╚══════════════════════════════════════════════════════════════════════════════╝
 *
 * Author  : Awinas Kannan
 * Domain  : Walmart Returns (RAP - Returns & Accommodations Platform)
 * Purpose : Learn SOLID principles through real-world Walmart Returns examples
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * TABLE OF CONTENTS
 * ════════════════════════════════════════════════════════════════════════════════
 *
 *   1. [S] Single Responsibility Principle (SRP)
 *   2. [O] Open/Closed Principle (OCP)
 *   3. [L] Liskov Substitution Principle (LSP)
 *   4. [I] Interface Segregation Principle (ISP)
 *   5. [D] Dependency Inversion Principle (DIP)
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * FOLDER STRUCTURE
 * ════════════════════════════════════════════════════════════════════════════════
 *
 *   walmartreturns/
 *   ├── SOLID_PRINCIPLES_GUIDE.java       ← This file (documentation)
 *   ├── singleresponsibility/
 *   │   ├── bad/
 *   │   │   └── ReturnService.java        ← God class doing 4 things
 *   │   └── good/
 *   │       ├── ReturnRequest.java        ← Data holder only
 *   │       ├── ReturnValidator.java      ← Validates eligibility only
 *   │       ├── RefundProcessor.java      ← Processes refunds only
 *   │       ├── NotificationService.java  ← Sends notifications only
 *   │       ├── ReturnAuditLogger.java    ← Logs for audit only
 *   │       └── ReturnOrchestrator.java   ← Coordinates workflow only
 *   ├── openclosed/
 *   │   ├── bad/
 *   │   │   └── ReturnRefundCalculator.java  ← if-else chain for return types
 *   │   └── good/
 *   │       ├── RefundStrategy.java          ← Interface (extension point)
 *   │       ├── InStoreRefund.java           ← Strategy implementation
 *   │       ├── OnlineRefund.java            ← Strategy implementation
 *   │       ├── CurbsideRefund.java          ← Strategy implementation
 *   │       ├── MarketplaceRefund.java       ← Strategy implementation
 *   │       └── ReturnRefundProcessor.java   ← Closed for modification
 *   ├── liskov/
 *   │   ├── bad/
 *   │   │   ├── ReturnProcessor.java         ← Parent with full contract
 *   │   │   ├── InStoreOnlyReturn.java       ← Breaks shipping contract
 *   │   │   ├── FinalSaleReturn.java         ← Breaks everything
 *   │   │   └── BadMain.java                 ← Shows runtime crash
 *   │   └── good/
 *   │       ├── Refundable.java              ← Focused interface
 *   │       ├── Shippable.java               ← Focused interface
 *   │       ├── OnlineReturn.java            ← Implements both safely
 *   │       ├── InStoreReturn.java           ← Implements only Refundable
 *   │       ├── CurbsideReturn.java          ← Implements only Refundable
 *   │       └── GoodMain.java                ← Shows safe substitution
 *   ├── interfacesegregation/
 *   │   ├── bad/
 *   │   │   ├── IReturnHandler.java          ← Fat interface (7 methods!)
 *   │   │   ├── StoreAssociate.java          ← Forced to implement irrelevant methods
 *   │   │   └── AnalyticsService.java        ← 6 out of 7 methods are dead stubs
 *   │   └── good/
 *   │       ├── RefundProcessor.java         ← Focused interface
 *   │       ├── ItemInspector.java           ← Focused interface
 *   │       ├── ShippingHandler.java         ← Focused interface
 *   │       ├── SellerCommunicator.java      ← Focused interface
 *   │       ├── ReportGenerator.java         ← Focused interface
 *   │       ├── CustomerNotifier.java        ← Focused interface
 *   │       ├── StoreAssociate.java          ← Implements only what it needs
 *   │       ├── OnlineReturnsBot.java        ← Implements only what it needs
 *   │       ├── MarketplaceHandler.java      ← Implements only what it needs
 *   │       ├── AnalyticsService.java        ← Implements only ReportGenerator
 *   │       └── GoodMain.java                ← Shows clean, type-safe usage
 *   └── dependencyinversion/
 *       ├── bad/
 *       │   ├── ReturnService.java           ← Tightly coupled to implementations
 *       │   ├── MySQLReturnRepository.java   ← Concrete class (no interface)
 *       │   ├── EmailNotificationSender.java ← Concrete class (no interface)
 *       │   └── FixedRateRefundCalculator.java ← Concrete class (no interface)
 *       └── good/
 *           ├── ReturnRepository.java        ← Abstraction (interface)
 *           ├── NotificationSender.java      ← Abstraction (interface)
 *           ├── RefundCalculator.java        ← Abstraction (interface)
 *           ├── MySQLReturnRepository.java   ← Implementation A
 *           ├── CassandraReturnRepository.java ← Implementation B (swap-in!)
 *           ├── EmailNotificationSender.java ← Implementation A
 *           ├── KafkaNotificationSender.java ← Implementation B (swap-in!)
 *           ├── DynamicRefundCalculator.java ← Implementation with complex rules
 *           └── ReturnService.java           ← Depends ONLY on abstractions
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ [S] SINGLE RESPONSIBILITY PRINCIPLE (SRP)                                   │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * DEFINITION:
 *   "A class should have only ONE reason to change."
 *   — Robert C. Martin (Uncle Bob)
 *
 * ONE-LINER:
 *   One class = One job = One reason to change.
 *
 * WALMART RETURNS EXAMPLE:
 *   When a customer returns an item, multiple things happen:
 *   validate → refund → notify → log.
 *
 *   BAD:  One "ReturnService" class does ALL four things.
 *         If notification logic changes, you risk breaking refund logic.
 *
 *   GOOD: Separate classes — ReturnValidator, RefundProcessor,
 *         NotificationService, ReturnAuditLogger — each with ONE job.
 *
 * BENEFITS:
 *   • Easier to test (unit test each class independently)
 *   • Easier to maintain (change one thing without breaking others)
 *   • Parallel development (different devs work on different classes)
 *   • Lower blast radius of changes
 *
 * REAL-WORLD ANALOGY:
 *   In a Walmart store: the cashier handles checkout, the returns desk handles
 *   returns, the stock room handles inventory. You wouldn't ask the cashier to
 *   also restock shelves and handle returns simultaneously!
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ [O] OPEN/CLOSED PRINCIPLE (OCP)                                             │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * DEFINITION:
 *   "Software entities should be OPEN for extension but CLOSED for modification."
 *   — Bertrand Meyer
 *
 * ONE-LINER:
 *   Add new behavior by writing new code, not by changing existing code.
 *
 * WALMART RETURNS EXAMPLE:
 *   Walmart keeps adding new return channels:
 *   In-Store → Online → Curbside → Marketplace → Locker → Drone Pickup...
 *
 *   BAD:  One method with if-else for each return type. Every new channel
 *         requires modifying existing code. Risk of breaking existing channels.
 *
 *   GOOD: RefundStrategy interface + one class per return type. Adding
 *         "LockerReturnRefund" means creating a NEW class — existing code
 *         remains untouched.
 *
 * DESIGN PATTERNS THAT HELP:
 *   • Strategy Pattern (used in our example)
 *   • Template Method Pattern
 *   • Decorator Pattern
 *   • Plugin Architecture
 *
 * REAL-WORLD ANALOGY:
 *   A power strip is OPEN for extension (plug in new devices) but CLOSED for
 *   modification (you don't rewire the strip to add a new outlet).
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ [L] LISKOV SUBSTITUTION PRINCIPLE (LSP)                                     │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * DEFINITION:
 *   "Objects of a superclass should be replaceable with objects of a subclass
 *    without breaking the application."
 *   — Barbara Liskov
 *
 * ONE-LINER:
 *   If it looks like a duck but throws exceptions when you call quack(),
 *   it's NOT a proper subtype.
 *
 * WALMART RETURNS EXAMPLE:
 *   Not all "returns" behave the same way:
 *   - Regular returns: can refund + can generate shipping label
 *   - In-store only: can refund, but CANNOT generate shipping label
 *   - Final sale items: CANNOT refund at all
 *
 *   BAD:  InStoreOnlyReturn extends ReturnProcessor but throws
 *         UnsupportedOperationException for generateShippingLabel().
 *         Code processing a list of ReturnProcessor objects CRASHES.
 *
 *   GOOD: Split into proper interfaces — Refundable and Shippable.
 *         InStoreReturn implements only Refundable. OnlineReturn implements both.
 *         No surprises, no exceptions, no broken contracts.
 *
 * KEY RULES FOR LSP:
 *   • Subclass must NOT throw new exceptions the parent doesn't declare
 *   • Subclass must NOT weaken/ignore parent's contract
 *   • Subclass preconditions must NOT be stronger than parent's
 *   • Subclass postconditions must NOT be weaker than parent's
 *
 * REAL-WORLD ANALOGY:
 *   If a restaurant menu says "All burgers come with fries", a veggie burger
 *   that says "Sorry, no fries for this one" violates LSP. The customer
 *   (client code) expects fries (behavior) with ANY burger (subtype).
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ [I] INTERFACE SEGREGATION PRINCIPLE (ISP)                                   │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * DEFINITION:
 *   "No client should be forced to depend on methods it does not use."
 *   — Robert C. Martin
 *
 * ONE-LINER:
 *   Many small, focused interfaces > one big "do everything" interface.
 *
 * WALMART RETURNS EXAMPLE:
 *   Different return handlers have different capabilities:
 *   - Store Associate: refund + inspect + notify
 *   - Online Bot: refund + shipping + notify
 *   - Marketplace Handler: refund + seller communication + notify
 *   - Analytics Service: reporting ONLY
 *
 *   BAD:  One IReturnHandler interface with 7 methods. AnalyticsService is
 *         forced to implement processRefund(), inspectItem(), generateShippingLabel()
 *         — all with UnsupportedOperationException. Polluted, bloated code.
 *
 *   GOOD: Six focused interfaces (RefundProcessor, ItemInspector, ShippingHandler,
 *         SellerCommunicator, ReportGenerator, CustomerNotifier). Each class
 *         implements ONLY what it genuinely needs.
 *
 * ISP vs SRP:
 *   • SRP = A class should have one reason to change (implementation side)
 *   • ISP = A client should not depend on unused methods (consumer/interface side)
 *   • They complement each other but operate from different perspectives.
 *
 * REAL-WORLD ANALOGY:
 *   A TV remote with 100 buttons where you only use 5. ISP says: give the user
 *   a simple remote with just the buttons they need. A streaming remote doesn't
 *   need a "VCR Eject" button!
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ [D] DEPENDENCY INVERSION PRINCIPLE (DIP)                                    │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * DEFINITION:
 *   "High-level modules should NOT depend on low-level modules.
 *    Both should depend on ABSTRACTIONS (interfaces)."
 *   — Robert C. Martin
 *
 * ONE-LINER:
 *   Depend on WHAT something does (interface), not HOW it does it (implementation).
 *
 * WALMART RETURNS EXAMPLE:
 *   ReturnService needs: data storage + notifications + refund calculation.
 *
 *   BAD:  ReturnService directly creates MySQLReturnRepository,
 *         EmailNotificationSender, FixedRateRefundCalculator.
 *         Switching MySQL → Cassandra means MODIFYING ReturnService.
 *         Testing requires a real database and email server!
 *
 *   GOOD: ReturnService depends on interfaces: ReturnRepository,
 *         NotificationSender, RefundCalculator. Implementations are INJECTED
 *         via constructor. Swap MySQL for Cassandra? Just pass a different
 *         implementation. Unit test? Inject mocks!
 *
 * DIP IN SPRING BOOT:
 *   This is exactly what @Autowired / @Inject does:
 *     @Service
 *     class ReturnService {
 *         @Autowired ReturnRepository repo;  // Spring injects the right impl
 *     }
 *
 * DEPENDENCY INJECTION TYPES:
 *   • Constructor Injection (recommended — used in our example)
 *   • Setter Injection
 *   • Field Injection (@Autowired on field — least recommended)
 *
 * REAL-WORLD ANALOGY:
 *   A wall outlet (interface) lets you plug in ANY device — lamp, charger, TV.
 *   The house electrical system doesn't care WHAT you plug in (implementation).
 *   It just provides power through a standard interface. If you hardwired a lamp
 *   directly (no outlet), you'd need an electrician to change it — that's tight coupling!
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ QUICK REFERENCE — ALL 5 PRINCIPLES SUMMARY                                  │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * ┌──────────┬──────────────────────────────────┬─────────────────────────────┐
 * │ Principle│ One-Liner                         │ Walmart Returns Example     │
 * ├──────────┼──────────────────────────────────┼─────────────────────────────┤
 * │ [S] SRP  │ One class = One job               │ Separate validator, refund, │
 * │          │                                    │ notifier, logger classes   │
 * ├──────────┼──────────────────────────────────┼─────────────────────────────┤
 * │ [O] OCP  │ Add new code, don't change old    │ New return type = new class,│
 * │          │                                    │ existing code stays same   │
 * ├──────────┼──────────────────────────────────┼─────────────────────────────┤
 * │ [L] LSP  │ Subtypes must honor parent contract│ Don't force InStoreReturn  │
 * │          │                                    │ to implement shipping      │
 * ├──────────┼──────────────────────────────────┼─────────────────────────────┤
 * │ [I] ISP  │ Small interfaces > fat interfaces  │ 6 focused interfaces, not  │
 * │          │                                    │ one 7-method monster       │
 * ├──────────┼──────────────────────────────────┼─────────────────────────────┤
 * │ [D] DIP  │ Depend on abstractions            │ Inject interfaces, not     │
 * │          │                                    │ MySQL/Email directly       │
 * └──────────┴──────────────────────────────────┴─────────────────────────────┘
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * HOW TO RUN EXAMPLES
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * Each principle folder has a main class you can run directly:
 *
 *   SRP Bad:  singleresponsibility.bad.ReturnService.main()
 *   SRP Good: singleresponsibility.good.ReturnOrchestrator.main()
 *   OCP Bad:  openclosed.bad.ReturnRefundCalculator.main()
 *   OCP Good: openclosed.good.ReturnRefundProcessor.main()
 *   LSP Bad:  liskov.bad.BadMain.main()
 *   LSP Good: liskov.good.GoodMain.main()
 *   ISP Good: interfacesegregation.good.GoodMain.main()
 *   DIP Bad:  dependencyinversion.bad.ReturnService.main()
 *   DIP Good: dependencyinversion.good.ReturnService.main()
 *
 *
 * ════════════════════════════════════════════════════════════════════════════════
 * INTERVIEW TIPS
 * ════════════════════════════════════════════════════════════════════════════════
 *
 * When asked "Explain SOLID with a real example":
 *
 *   1. Start with: "Let me explain using Walmart's Returns system..."
 *   2. Pick 2-3 principles and explain bad → good transition
 *   3. Mention design patterns: Strategy (OCP), Constructor Injection (DIP)
 *   4. Connect to Spring Boot: "DIP is basically what @Autowired does"
 *   5. Emphasize benefits: testability, maintainability, team collaboration
 *
 * Common Follow-up Questions:
 *   Q: "How does SRP differ from ISP?"
 *   A: SRP is about the CLASS having one reason to change.
 *      ISP is about the INTERFACE not forcing unused methods on clients.
 *      SRP = implementation perspective. ISP = consumer perspective.
 *
 *   Q: "How does LSP relate to OCP?"
 *   A: LSP ensures subclasses don't break parent contracts.
 *      OCP ensures you extend via new classes, not modify existing ones.
 *      Together: new subclasses (OCP) must honor parent contracts (LSP).
 *
 *   Q: "What pattern helps achieve OCP?"
 *   A: Strategy Pattern — define interface, implement per variant, inject.
 *      Exactly what we did with RefundStrategy.
 *
 * ════════════════════════════════════════════════════════════════════════════════
 */
public class SOLID_PRINCIPLES_GUIDE {
    // This file serves as documentation. See the folder structure above to run examples.
}
