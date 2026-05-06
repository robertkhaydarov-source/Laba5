package laba5.server;

import laba5.client.commands.*;
import laba5.manager.*;
import laba5.server.commandServer.AddIfMaxServer;
import laba5.server.commandServer.AddServer;
import laba5.server.commandServer.RemoveLowerServer;
import laba5.server.commandServer.UpdateServer;
import laba5.shared.actions.Request;
import laba5.shared.actions.Response;
import laba5.shared.model.StudyGroup;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.ZonedDateTime;
import java.util.List;

public class Server {
    public static void main(String[] args){
        try(DatagramChannel channel = DatagramChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 7777);
            channel.bind(inetSocketAddress);
            channel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(65535 );
            CollectionManager collectionManager = new CollectionManager();
            StudyGroupFactory studyGroupFactory = new StudyGroupFactory(collectionManager);
            String fileName = System.getenv("FILE_NAME");
            FileCsvReader fileManager = new FileCsvReader(fileName);
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
            InputManager inputManager =new InputManager(null);
            CommandInvoker invoker = new CommandInvoker(inputManager);
            invoker.register(new Help(invoker));
            invoker.register(new Info(collectionManager));
            invoker.register(new Show(collectionManager));
            invoker.register(new Clear(collectionManager));
            invoker.register(new Remove_by_id(collectionManager));
            invoker.register(new AddServer(collectionManager));
            invoker.register(new UpdateServer(collectionManager));
            invoker.register(new Save(collectionManager, fileName));
            invoker.register(new Exit());
            invoker.register(new Remove_last(collectionManager));
            invoker.register(new AddIfMaxServer(collectionManager));
            invoker.register(new RemoveLowerServer(collectionManager));
            invoker.register(new Count_by_form_of_education(collectionManager));
            invoker.register(new Filter_contains_name(collectionManager));
            invoker.register(new Print_field_ascending_should_be_expelled(collectionManager));
            invoker.register(new Execute_script(invoker, inputManager));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                invoker.execute("save");
                System.out.println("колекция сохранена");
            }));
            while(true){
                SocketAddress clientAddress = channel.receive(buffer);
                if(clientAddress!=null) {
                    buffer.flip();
                    byte[] bytes = buffer.array();
                    buffer.clear();
                    ObjectInputStream obj = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    try {
                        Request request = (Request) obj.readObject();
                        String result;
                        if(request.getStudyGroup()!=null){
                            result = invoker.execute(request);
                        } else {
                            result = invoker.execute(request.getName() + " " + (request.getArgs()!=null ? request.getArgs() : ""));
                        }
                        System.out.println("Получен запрос: " + request.getName() + " studyGroup: " + request.getStudyGroup());
                        System.out.println("Результат: " + result);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(bos);
                        oos.writeObject(new Response(result));
                        oos.flush();
                        ByteBuffer buffer1 = ByteBuffer.wrap(bos.toByteArray());
                        channel.send(buffer1, clientAddress);
                    } catch (ClassNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }catch (IOException e){
            System.err.println("" + e.getMessage());
        }

    }
}
