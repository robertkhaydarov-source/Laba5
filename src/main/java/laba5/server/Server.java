package laba5.server;

import laba5.client.commands.*;
import laba5.server.commandServer.*;
import laba5.server.manager.*;
import laba5.shared.actions.Request;
import laba5.shared.actions.Response;
import laba5.shared.model.StudyGroup;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.*;
import java.net.*;
import java.time.ZonedDateTime;
import java.util.*;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    public static void main(String[] args) { // datagram socket
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 7777);
            DatagramSocket socket = new DatagramSocket(inetSocketAddress);
            byte[] buffer = new byte[65507];
            CollectionManager collectionManager = new CollectionManager();
            StudyGroupFactory studyGroupFactory = new StudyGroupFactory(collectionManager);
            String fileName = System.getenv("FILE_NAME");
            if (fileName == null) {
                logger.error("Environment variable FILE_NAME not set");
                return;
            }
            FileCsvReader fileManager = new FileCsvReader(fileName);
            try {
                List<String[]> lines = fileManager.readCSV();
                for (String[] line : lines) {
                    try {
                        StudyGroup studyGroup = studyGroupFactory.createFromFile(ZonedDateTime.now(), line);
                        if (studyGroup != null) collectionManager.add(studyGroup);
                    } catch (IllegalArgumentException e) {
                        logger.error("повреждены данные " + e.getMessage());
                    }
                }
                if (!collectionManager.showCollection().isEmpty())
                    logger.error("элементы из файла добавлены в коллекцию");
                collectionManager.updateCurrentId();
            } catch (FileNotFoundException e) {
                logger.error("файла не существует " + e.getMessage());
            } catch (IOException e) {
                logger.error("Ошибка ввода-вывода " + e.getMessage());
            }
            InputManager inputManager = new InputManager(null);
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
            Thread consoleThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                try {
                    while (true) {
                        String comand = scanner.nextLine();
                        if ("save".equals(comand)) {
                            invoker.execute("save");
                            System.out.println("Сохранено!");
                        } else if ("exit".equals(comand)) {
                            System.exit(0);
                        }
                    }
                } catch (NoSuchElementException | IllegalStateException e) {
                    logger.warn("Консольный ввод закрыт, поток завершается");
                }
            });
            consoleThread.setDaemon(true);
            consoleThread.start();
            logger.info("Server started on port 7777");
            Map<String, Response> processedRequests = new LinkedHashMap<>() {
                protected boolean removeEldestEntry(Map.Entry<String, Response> eldest) {
                    return size() > 100; // хранить максимум 100 последних
                }
            };
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                ByteArrayInputStream bits = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                ObjectInputStream oos = new ObjectInputStream(bits);
                Request request = (Request) oos.readObject();
                String requestId = request.getRequestId() != null
                        ? request.getRequestId()
                        : UUID.randomUUID().toString().substring(0, 8);
                MDC.put("requestId", requestId);
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                if (processedRequests.containsKey(requestId)) {
                    logger.warn("Duplicate request {}, returning cached result", requestId);
                    Response cachedResponse = processedRequests.get(requestId);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos2 = new ObjectOutputStream(bos);
                    oos2.writeObject(cachedResponse);
                    oos2.flush();
                    byte[] responseByte = bos.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(
                            responseByte, responseByte.length, clientAddress, clientPort
                    );
                    socket.send(sendPacket);
                    logger.info("Cached response sent to: {}", clientAddress);
                    MDC.clear();
                    continue;
                }

                if (clientAddress != null) {
                    logger.info("New connection from {}", clientAddress);
                    String result;
                    if ("exit".equals(request.getName())) {
                        // Отклонить exit - это только для клиента
                        result = "Команда exit недоступна на сервере";
                    } else {
                        if (request.getStudyGroup() != null) {
                            result = invoker.execute(request);
                        } else {
                            result = invoker.execute(request.getName() + " " + (request.getArgs() != null ? request.getArgs() : ""));
                        }
                    }
                    logger.info("Executing command: {} args: {}", request.getName(), request.getArgs());
                    logger.info("Получен запрос: " + request.getName() + " studyGroup: " + request.getStudyGroup());
                    logger.info("Результат: " + result);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos2 = new ObjectOutputStream(bos);
                    Response response = new Response(result, collectionManager.getCurrentId()), requestId);
                    oos2.writeObject(response);
                    processedRequests.put(requestId, response);
                    oos2.flush();
                    byte[] responseByte = bos.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(
                            responseByte, responseByte.length, clientAddress, clientPort
                    );
                    socket.send(sendPacket);
                    logger.info("Response sent to: {}", clientAddress);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Critical server error", e);
        }
        finally {
            MDC.clear();
        }

    }
}

