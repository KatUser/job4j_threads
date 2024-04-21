package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean managedToAddAccount = false;
        if (!accounts.containsValue(account)) {
            accounts.put(account.id(), account);
            managedToAddAccount = true;
        }
        return managedToAddAccount;
    }

    public synchronized boolean update(Account account) {
        boolean managedToUpdateAccount = false;
        if (getById(account.id()).isPresent()) {
            add(account);
        }
        return managedToUpdateAccount;
    }

    public synchronized void delete(int id) {
        if (getById(id).isPresent()) {
            accounts.remove(id);
        }
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean managedToTransfer = false;
        int fromAccAmount = getById(fromId).get().amount();
        int toAccAmount = getById(toId).get().amount();
        if (getById(fromId).isPresent() && getById(toId).isPresent()
        && getById(fromId).get().amount() >= amount & amount > 0) {

            update(new Account(fromId, fromAccAmount - amount));
            update(new Account(toId, toAccAmount + amount));

            managedToTransfer = true;
        }
        return managedToTransfer;
    }
}