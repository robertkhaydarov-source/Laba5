package laba5;

import laba5.commands.*;
import laba5.manager.*;
import laba5.model.StudyGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
/**
 * Класс Main является точкой входа в приложение.
 * Инициализирует основные менеджеры программы,
 * регистрирует команды и запускает цикл обработки пользовательского ввода.
 *
 * Программа управляет коллекцией объектов StudyGroup.
 *
 * @author Robert
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        String fileName = System.getenv("FILE_NAME");
        if (fileName==null){
            System.out.println("Переменная окружения FILE_NAME не установлена.");
            return;
        }
        Scanner scanner =new Scanner(System.in);
        CollectionManager collectionManager = new CollectionManager();
        StudyGroupFactory studyGroupFactory = new StudyGroupFactory(collectionManager);
        FileCsvReader fileManager = new FileCsvReader(fileName);
        InputManager inputManager = new InputManager(scanner);
        CommandInvoker invoker = new CommandInvoker(inputManager);
        invoker.register(new Help(invoker));
        invoker.register(new Info(collectionManager));
        invoker.register(new Show(collectionManager));
        invoker.register(new Clear(collectionManager));
        invoker.register(new Remove_by_id(collectionManager));
        invoker.register(new Add(collectionManager, inputManager, studyGroupFactory, fileManager));
        invoker.register(new Update(inputManager, studyGroupFactory, fileManager, collectionManager));
        invoker.register(new Save(collectionManager, fileName));
        invoker.register(new Exit());
        invoker.register(new Remove_last(collectionManager));
        invoker.register(new Add_if_max(collectionManager, studyGroupFactory, inputManager, fileManager));
        invoker.register(new Remove_lower(collectionManager, inputManager, studyGroupFactory, fileManager));
        invoker.register(new Count_by_form_of_education(collectionManager));
        invoker.register(new Filter_contains_name(collectionManager));
        invoker.register(new Print_field_ascending_should_be_expelled(collectionManager));
        invoker.register(new Execute_script(invoker, inputManager));
        try {
            List<String[]> lines = fileManager.readCSV();
            for (String[] line:lines){
                try{
                    StudyGroup studyGroup=studyGroupFactory.createFromFile(ZonedDateTime.now(), line);
                    if(studyGroup!=null) collectionManager.add(studyGroup);
                }catch (IllegalArgumentException e){
                    System.err.println("повреждены данные " + e.getMessage());
                }
            }
            if (!collectionManager.showCollection().isEmpty()) System.out.println("элементы из файла добавлены в коллекцию");
            collectionManager.updateCurrentId();
        } catch (FileNotFoundException e) {
            System.err.println("файла не существует " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода " + e.getMessage());
        }
        while (scanner.hasNextLine()) {
            String commandWithArgs = scanner.nextLine();
            invoker.execute(commandWithArgs);

        }
        }
}

