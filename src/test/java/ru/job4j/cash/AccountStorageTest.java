package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStorageTest {

    @Test
    void whenAddIsSuccessful() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenAddFails() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.add(new Account(1, 100))).isFalse();
    }

    @Test
    void whenUpdateIsSuccessfulCheckAmount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenUpdateIsSuccessfulCheckBoolean() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.update(new Account(1, 200))).isTrue();
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransferIsSuccessful() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenTransferFailsDueToZeroBalanceOnSourceAccount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 0));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenTransferFailsDueToNonExistingDestinationAccount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.transfer(1, 2, 100)).isFalse();
    }

    @Test
    void whenTransferFailsDueToNonExistingSourceAccount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.transfer(2, 1, 100)).isFalse();
    }

    @Test
    void whenTransferFailsDueToZeroTransferAmount() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 0);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(firstAccount.amount()).isEqualTo(100);
        assertThat(secondAccount.amount()).isEqualTo(100);
        assertThat(storage.transfer(1, 2, 0)).isFalse();
    }
}