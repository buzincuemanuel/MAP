package repository;

import model.exception.MyException;
import model.state.ProgramState;

public interface IRepository {
    ProgramState getCrtPrg();
    void addPrgState(ProgramState prg);
    void logPrgStateExec() throws MyException;
    void clearLogFile() throws MyException;
}