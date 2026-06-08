package laba5.server;

import laba5.client.commands.*;
import laba5.server.commandServer.*;
import laba5.server.manager.*;
import laba5.shared.actions.Request;
import laba5.shared.actions.Response;import laba5.shared.model.StudyGroup;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.*;
import java.net.*;
import java.sql.DriverManager;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) { // datagram socket
        try {
            ExecutorService readPool = Executors.newFixedThreadPool(4);
            ExecutorService executionPool = Executors.newFixedThreadPool(4);
            ExecutorService sendPool = Executors.newCachedThreadPool();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 7777);
            DatagramSocket socket = new DatagramSocket(inetSocketAddress);
            byte[] buffer = new byte[65507];
            CollectionManager collectionManager = new CollectionManager();
            StudyGroupFactory studyGroupFactory = new StudyGroupFactory(collectionManager);
            DatabaseHandler databaseHandler = new DatabaseHandler("jdbc:postgresql://pg:5432/studs",
                    "s501445", // Твой логин ИСУ из скриншота
                    "VWJwnpCECNwFSV0w");
            UserDao userDao = new UserDao(databaseHandler);
            if (databaseHandler.connect() != null) {
                logger.info("Подключение к БД успешно!");
            } else {
                logger.info("Подключение к БД не удалось");
                System.exit(-1);
            }
            InputManager inputManager = new InputManager(null);
            CollectionDao collectionDao = new CollectionDao(databaseHandler);
            CommandInvoker invoker = new CommandInvoker(inputManager);
            invoker.register(new Help(invoker));
            invoker.register(new Info(collectionManager));
            invoker.register(new Show(collectionManager));
            invoker.register(new Clear(collectionManager, collectionDao));
            invoker.register(new Remove_by_id(collectionManager, collectionDao));
            invoker.register(new AddServer(collectionManager, collectionDao));
            invoker.register(new UpdateServer(collectionManager, collectionDao));
            invoker.register(new Exit());
            invoker.register(new Remove_last(collectionManager, collectionDao));
            invoker.register(new AddIfMaxServer(collectionManager, collectionDao));
            invoker.register(new RemoveLowerServer(collectionManager, collectionDao));
            invoker.register(new Count_by_form_of_education(collectionManager));
            invoker.register(new Filter_contains_name(collectionManager));
            invoker.register(new Print_field_ascending_should_be_expelled(collectionManager));
            invoker.register(new Execute_script(invoker, inputManager));
            Thread consoleThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                try {
                    while (true) {
                        String comand = scanner.nextLine();
                        if ("exit".equals(comand)) {
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
                    return size() > 100;
                }
            };

            List<StudyGroup> studyGroups = collectionDao.loadCollection();
            if (studyGroups != null) {
                for (StudyGroup studyGroup : studyGroups) {
                    collectionManager.add(studyGroup);
                }
                logger.info("Элементы из базы данных успешно загружены в коллекцию памяти (всего: {})", studyGroups.size());
                collectionManager.updateCurrentId();
            } else {
                logger.error("Не удалось загрузить коллекцию из базы данных!");
            }
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                byte[] packetData = Arrays.copyOf(packet.getData(), packet.getLength());
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                readPool.submit(() -> {
                    try {
                        ByteArrayInputStream bits = new ByteArrayInputStream(packetData);
                        ObjectInputStream oos = new ObjectInputStream(bits);
                        final Request request = (Request) oos.readObject();
                        executionPool.submit(() -> {
                            try {
                                String requestId = request.getRequestId() != null
                                        ? request.getRequestId()
                                        : UUID.randomUUID().toString().substring(0, 8);
                                MDC.put("requestId", requestId);

                                if (processedRequests.containsKey(requestId)) {
                                    logger.warn("Duplicate request ID: {}", requestId);
                                    Response cashedResponse = processedRequests.get(requestId);
                                    ByteArrayOutputStream bits1 = new ByteArrayOutputStream();
                                    ObjectOutputStream oos1 = new ObjectOutputStream(bits1);
                                    oos1.writeObject(cashedResponse);
                                    oos1.flush();
                                    byte[] responseByte1 = bits1.toByteArray();
                                    DatagramPacket sendPacket = new DatagramPacket(
                                            responseByte1, responseByte1.length, clientAddress, clientPort
                                    );
                                    socket.send(sendPacket);
                                    logger.info("Cached response sent to: {}", clientAddress);
                                    MDC.clear();
                                    return;
                                }
                                if (clientAddress != null) {
                                    logger.info("New connection from {}", clientAddress);
                                    String result;
                                    synchronized (collectionManager) {
                                        if ("register".equals(request.getName())) {
                                            if (userDao.register(request.getUserName(), request.getPassword())) {
                                                result = "Регистрация успешна";
                                            } else result = "Логин уже занят";
                                        } else {
                                            boolean isAuthenticate = userDao.authenticate(request.getUserName(), request.getPassword());
                                            if (!isAuthenticate) {
                                                result = "Ошибка: неверный логин или пароль! Выполнение команды запрещено.";
                                            } else {
                                                if ("exit".equals(request.getName())) {
                                                    // Отклонить exit - это только для клиента
                                                    result = "Команда exit недоступна на сервере";
                                                } else {
                                                    result = invoker.execute(request);
                                                }
                                            }
                                        }
                                    }
                                    logger.info("Received command: {} studyGroup: {}", request.getName(), request.getStudyGroup());
                                    logger.info("Результат: " + result);
                                    sendPool.submit(() -> {
                                        try {
                                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                            ObjectOutputStream oos2 = new ObjectOutputStream(bos);
                                            Response response = new Response(result, collectionManager.getCurrentId(), requestId);
                                            processedRequests.put(requestId, response);
                                            oos2.writeObject(response);
                                            oos2.flush();
                                            byte[] responseByte = bos.toByteArray();
                                            DatagramPacket sendPacket = new DatagramPacket(
                                                    responseByte, responseByte.length, clientAddress, clientPort
                                            );
                                            socket.send(sendPacket);
                                            logger.info("Response sent to: {}", clientAddress);
                                        } catch (IOException e) {
                                            logger.error("Ошибка отправки: " + e.getMessage());
                                        }

                                    });
                                }
                            } catch (IOException e) {
                                logger.error(e.getMessage());
                            } finally {
                                MDC.clear();
                            }
                        });
                    } catch (IOException | ClassNotFoundException e) {
                        logger.error("Ошибка десериализации пакета: ", e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}






