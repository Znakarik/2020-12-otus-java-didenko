package ru.znakarik.otus.core;

import java.util.*;

public class CustomerService {

    private final NavigableMap<Customer, String> data =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return new AbstractMap.SimpleEntry<>(
                data.firstEntry().getKey().copy(),
                data.firstEntry().getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (data.higherEntry(customer) == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(
                data.higherEntry(customer).getKey().copy(),
                data.higherEntry(customer).getValue());
    }

    public void add(Customer customer, String data) {
        this.data.put(customer, data);
    }
}
