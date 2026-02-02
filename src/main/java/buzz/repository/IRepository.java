package buzz.repository;

import buzz.model.exception.MyException;
import buzz.model.state.ProgramState;

import java.util.List;

public interface IRepository {
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> list);
    void addPrgState(ProgramState prg);
    void logPrgStateExec(ProgramState prgState) throws MyException;
    void clearLogFile() throws MyException;
}