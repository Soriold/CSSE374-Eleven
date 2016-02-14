package src.problem.commands;

import java.util.Properties;

import src.problem.components.IModel;

public interface IPhase {

	public void executeOn(IModel m, Properties prop) throws Exception;
}
