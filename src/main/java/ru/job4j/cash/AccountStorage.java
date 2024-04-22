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
        /* null if added, else returns the current value.*/
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    /*	replace(K key, V oldValue, V newValue)
    Replaces the entry for the specified key only if currently mapped to the specified value.*/
    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), getById(account.id()).get(), account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);

    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean managedToTransfer = false;
        Optional<Account> accountTransferFrom =  getById(fromId);
        Optional<Account> accountTransferTo =  getById(toId);

        int fromAccAmount = getById(fromId).get().amount();
        int toAccAmount = getById(toId).get().amount();

        if (accountTransferFrom.isPresent() && accountTransferTo.isPresent()
                && accountTransferFrom.get().amount() >= amount & amount > 0) {

            managedToTransfer = update(new Account(fromId, fromAccAmount - amount))
            && update(new Account(toId, toAccAmount + amount));
        }
        return managedToTransfer;
    }
}