package repository;

import model.exception.MyException;
import model.state.ProgramState;
import model.statement.Statement;
import model.value.Value;
import model.value.StringValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository implements IRepository {
    private final List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getCrtPrg() { return programStates.get(0); }

    @Override
    public void addPrgState(ProgramState prg) { programStates.add(prg); }

    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            ProgramState state = getCrtPrg();

            logFile.println("ExeStack:");
            for (Statement s : state.getExeStack().getStatements()) logFile.println(s);

            logFile.println("SymTable:");
            for (var entry : state.getSymTable().getContent().entrySet())
                logFile.println(entry.getKey() + " --> " + entry.getValue());

            logFile.println("Out:");
            for (Value v : state.getOut().getValues()) logFile.println(v);

            logFile.println("FileTable:");
            for (StringValue f : state.getFileTable().getContent().keySet()) logFile.println(f);

            logFile.println("---------------------------------");
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