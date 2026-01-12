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
    private List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getPrgList() {
        return programStates;
    }

    @Override
    public void setPrgList(List<ProgramState> list) {
        this.programStates = list;
    }

    @Override
    public void addPrgState(ProgramState prg) {
        programStates.add(prg);
    }

    @Override
    public void logPrgStateExec(ProgramState prgState) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(prgState.toString());
        } catch (IOException e) {
            throw new MyException("Log error: " + e.getMessage());
        }
    }

    @Override
    public void clearLogFile() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)))) {
            logFile.print("");
        } catch (IOException e) {
            throw new MyException("Log error: " + e.getMessage());
        }
    }
}