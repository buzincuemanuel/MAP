package model.state;

import model.exception.MyException;
import model.value.Value;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Heap implements IHeap {
    private Map<Integer, Value> map;
    private AtomicInteger freeLocation;

    public Heap() {
        this.map = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(1);
    }

    @Override
    public int allocate(Value value) {
        int newAddress = freeLocation.getAndIncrement();
        map.put(newAddress, value);
        return newAddress;
    }

    @Override
    public Value get(int address) throws MyException {
        if (!map.containsKey(address))
            throw new MyException("Heap access error: Address " + address + " is not defined.");
        return map.get(address);
    }

    @Override
    public boolean containsKey(int address) {
        return map.containsKey(address);
    }

    @Override
    public void update(int address, Value value) throws MyException {
        if (!map.containsKey(address))
            throw new MyException("Heap update error: Address " + address + " is not defined.");
        map.put(address, value);
    }

    @Override
    public void setContent(Map<Integer, Value> content) {
        this.map = new ConcurrentHashMap<>(content);
    }

    @Override
    public Map<Integer, Value> getContent() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}