package repository;

import model.exception.MyException;
import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    private final List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getCrtPrg() {
        return this.programStates.get(0);
    }

    @Override
    public void addPrgState(ProgramState prg) {
        this.programStates.add(prg);
    }

    @Override
    public void logPrgStateExec(ProgramState prg, boolean append) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, append)))) {
            logFile.println(prg.toString());
            logFile.flush();
        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }
}