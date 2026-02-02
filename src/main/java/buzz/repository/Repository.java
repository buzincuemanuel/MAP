package buzz.repository;

import buzz.model.exception.MyException;
import buzz.model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(ProgramState programState, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.programStates.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getPrgList() {
        return programStates;
    }

    @Override
    public void setPrgList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void addPrgState(ProgramState programState) {
        this.programStates.add(programState);
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programState.toString());
            logFile.close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public void clearLogFile() throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }
}