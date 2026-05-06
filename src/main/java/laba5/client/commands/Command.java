package laba5.client.commands;

import laba5.shared.model.StudyGroup;

/**
 * Интерфейс Comand
 * выделяет общие методы для всех команд: execute, getName, getInfo.
 */
public interface Command {

   String execute(String... args);
   String execute(String args, StudyGroup studyGroup);
   String getName();
   String getInfo();
}
