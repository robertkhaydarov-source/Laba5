package laba5.client.commands;

import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

/**
 * Интерфейс Command
 * Выделяет общие методы для всех команд сервера и клиента.
 */
public interface Command {
   String execute(String... args);
   String execute(String args, StudyGroup studyGroup);
   String execute(Request request);

   String getName();
   String getInfo();
}