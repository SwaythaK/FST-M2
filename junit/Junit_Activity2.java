package activities;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

public class Junit_Activity2 {

    @Test
    public void notEnoughFunds() {
        BankAccount account = new BankAccount(5);

        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10), "Withdraw amount MUST be less than are equal to the balance available");
    }

    @Test
    void enoughFunds() {
        BankAccount account = new BankAccount(100);

        assertDoesNotThrow(() -> account.withdraw(100));
    }
}
