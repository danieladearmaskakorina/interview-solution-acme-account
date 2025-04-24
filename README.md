# Acme Financial Services – Transactional Account System

## How to Run

Compile all Java files and run the main CLI:

```bash
javac com/acme/account/*.java com/acme/application/UserInterface.java
java com.acme.application.UserInterface
```

This launches the menu interface with an initial balance of $1000.

**Prerequisites**
- Java Development Kit (JDK) 21 or higher
- No external dependencies - runs standalone via terminal

**Testing and Review Notes**
- Run `UserInterface.java` for full CLI-based interaction
- Run `TransactionalAccount.main()` for a scripted sanity test
- Code is heavily commented to explain design rationale, includes embedded design notes for all major components
- System Design Notes are embedded at the top of each file for clarity

**Known Edge Cases (Discovered & Handled)**
| Input Type | System Response After Solution Implementation | Source |
|------------|-----------------------------------------------|--------|
| Negative or zero deposit/withdrawal amounts | Rejected with descriptive message | Requirements |
| Fractional stock units (e.g. 2.5) | Rejected with message | Requirements |
| Oversized stock deposits (e.g. units > `Integer.MAX_VALUE`, overflow if cast to `int`) | Rejected via `Math.toIntExact()` to prevent overflow | Self-tested |
| Withdrawal exceeding balance/shares (e.g., own $10 → try to withdraw $11; hold 1 share → try to withdraw 5 ) | Rejected with descriptive message | Requirements |
| Non-numeric menu input (e.g., typing "B" or "xyz") | Triggers error message and safely re-prompts without crashing | Self-Tested |

---
## System Overview

This application simulates a session-based financial account supporting two asset classes: cash and ACME stock. It is designed to:

- Preserve correctness under common and edge case inputs  
- Offer human-readable transaction history  
- Maintain clear boundaries between interface and logic

---

## Data Expectations

- CASH transactions only accept positive decimal values (e.g., 50.00)
- STOCK transactions must be positive integers (e.g., 3, 10)
- Menu input must be positive integers between 1–7
- All invalid input (eg., letters, negative values, overflow) is gracefully handled and logged with CLI feedback

### CLI Menu Reference (Shown at Runtime)

Users interact with the system via the menu below. To perform an action, enter the corresponding number and press **Enter**:

```text
-- Acme Financial Menu --
1. Cash - Deposit
2. Cash - Withdraw
3. Stock - Deposit
4. Stock - Withdraw
5. Check Balance
6. Get History
7. Exit
Choose an option:
```
### Transaction History Formatting

Transaction entries are formatted using the `toString()` method inside `Transaction.java`. Each transaction is printed in order with type, action, and amount:

```text
1. Cash - Deposit - $50.00
2. Stock - Withdrawal - 2
3. Cash - Withdrawal - $30.00
```

---

## Requirements Summary

**Goal:** Expand the transactional account system to support both CASH and STOCK transactions, with accurate tracking of balances and in-session transaction history.

### Business Requirements

1. **Existing Functionality**  
- Only cash transactions were previously supported  
- No transaction history was maintained  

2. **New Functionality**  
- Track both cash and ACME stock holdings  
- All stock transactions are in whole units (integers only)  
- ACME stock has a fixed price of $5 per share  
- Show full transaction history within the session  

3. **Balance Calculation**  
- Cash Balance = Total Cash Deposits – Total Cash Withdrawals
- Stock Balance = (Total Stock Deposits – Total Stock Withdrawals) * $5
- Total Balance = Cash Balance + Stock Balance

4. **Out of Scope (Suggestions for future improvements)**  
- Other stock symbols or dynamic pricing to relfect market conditions 
- Addition of securities: Options, Futures, Bonds 
- Data persistence (reset on application exit)
- Fractional stock ownership  

---

## Design Intent

- **Predictable and explicit control flow**: each branch is explicit and guarded  
- **History traceability**: Standardized format via `Transaction.toString()`  
- **Separation of concerns**: logic vs CLI clearly divided with core in `TransactionalAccount` and interaction in `UserInterface`
- **Minimal viable robustness**: covers all edge cases even without JUnit
- **Future extensibility**: Enum pattern (`TransactionType`) allows new asset types for later 

---

## File Structure

| File | Description |
|------|-------------|
| `TransactionalAccount.java` | Core business logic, validation, and balance computation |
| `Transaction.java` | Defines and formats individual transaction entries |
| `UserInterface.java` | CLI-based interface for user input and flow control |
| `TransactionType.java` | Enum that limits valid transaction types and allows future additions |

---

## Reviewer Guidance

This project was written to prioritize readability, recoverability, and correctness. Comments and structure are meant to reduce ambiguity and make the logic easy to step through during review or live explanation.