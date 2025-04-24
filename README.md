# Acme Financial Services – Transactional Account System

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
Cash Balance = Total Cash Deposits – Total Cash Withdrawals
Stock Balance = (Total Stock Deposits – Total Stock Withdrawals) * $5
Total Balance = Cash Balance + Stock Balance

4. **Out of Scope**  
- Other stock symbols or dynamic pricing  
- Transaction types beyond cash and stock  
- Data persistence (reset on application exit)  

---

## System Overview

This application simulates a session-based financial account supporting two asset classes: cash and ACME stock. It is designed to:

- Preserve correctness under common and edge case inputs  
- Offer human-readable transaction history  
- Maintain clear boundaries between interface and logic

---

## Data Expectations

- CASH transactions accept positive decimal values (e.g., 50.00)
- STOCK transactions must be whole numbers (e.g., 3, 10)
- Menu input expects integers between 1–7
- All invalid input is gracefully handled and logged with CLI feedback

---

## Known Edge Cases

| Input Type                     | System Response                                 |
|-------------------------------|--------------------------------------------------|
| Negative or zero amounts       | Rejected with message                           |
| Fractional stock amounts       | Rejected with message                           |
| Oversized stock deposits       | Rejected via `Math.toIntExact()` overflow check |
| Withdrawal exceeding balance   | Rejected with message                           |
| Non-numeric menu input         | Caught and recovered without crashing           |

---

## Design Intent

- **Predictable control flow**: each branch is explicit and guarded  
- **Readable transaction history**: clean formatting via `toString()`  
- **Separation of concerns**: logic vs CLI clearly divided  
- **Minimal viable robustness**: covers all edge cases even without JUnit  

---

## File Structure

| File | Description |
|------|-------------|
| `TransactionalAccount.java` | Core logic, validation, and balance tracking |
| `Transaction.java` | Models individual deposits/withdrawals with formatting |
| `UserInterface.java` | CLI menu system for user interaction |
| `TransactionType.java` | Enum to restrict and validate transaction types |

---

## How to Run

Compile all Java files and run the main CLI:

```bash
javac com/acme/account/*.java com/acme/application/UserInterface.java
java com.acme.application.UserInterface
```

This launches the menu interface with an initial balance of $1000.

**Testing and Review Notes**
- Run UserInterface.java for full CLI-based interaction
- Run TransactionalAccount.main() for a scripted sanity test
- Code is heavily commented to explain design rationale
- System Design Notes are embedded at the top of each file for clarity

**Reviewer Guidance**
- This project was written to prioritize readability, recoverability, and correctness. Comments and structure are meant to reduce ambiguity and make the logic easy to step through during review or live explanation.