package buzz.model.state;

import buzz.model.exception.MyException;
import buzz.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapFileTable implements IFileTable {
    private final Map<StringValue, BufferedReader> fileTable;

    public MapFileTable() {
        this.fileTable = new ConcurrentHashMap<>();
    }

    @Override
    public void add(StringValue fileName, BufferedReader bufferedReader) throws MyException {
        if (isDefined(fileName))
            throw new MyException("File " + fileName + " is already open.");
        fileTable.put(fileName, bufferedReader);
    }

    @Override
    public void remove(StringValue fileName) throws MyException {
        if (!isDefined(fileName))
            throw new MyException("File " + fileName + " is not defined.");
        fileTable.remove(fileName);
    }

    @Override
    public BufferedReader lookup(StringValue fileName) throws MyException {
        if (!isDefined(fileName))
            throw new MyException("File " + fileName + " is not defined.");
        return fileTable.get(fileName);
    }

    @Override
    public boolean isDefined(StringValue fileName) {
        return fileTable.containsKey(fileName);
    }

    @Override
    public Map<StringValue, BufferedReader> getContent() {
        return fileTable;
    }

    @Override
    public String toString() {
        return fileTable.keySet().toString();
    }
}