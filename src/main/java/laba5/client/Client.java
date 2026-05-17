package laba5.client;

import laba5.server.manager.CollectionManager;
import laba5.server.manager.InputManager;
import laba5.server.manager.StudyGroupFactory;
import laba5.shared.actions.Request;
import laba5.shared.actions.Response;
import laba5.shared.model.StudyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.UUID;


public class Client {
    private final static Logger logger = LoggerFactory.getLogger(Client.class);
    public static void main(String... args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        InetSocketAddress serverAddress = new InetSocketAddress("localhost", 7777);
        logger.info("Server Address: {}", serverAddress.getAddress().getHostAddress());
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        InputManager inputManager = new InputManager(scanner);
        CollectionManager collectionManager = new CollectionManager();
        StudyGroupFactory studyGroupFactory = new StudyGroupFactory(collectionManager);
        while (scanner.hasNext()) {
            String commandWithArg = scanner.nextLine();
            String[] arg = commandWithArg.trim().split(" ", 2);
            Request request;
            switch (arg[0]) {
                case "add", "add_if_max", "remove_lower":
                    StudyGroup studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
                    request = new Request(arg[0], null, studyGroup);
                    break;
                case "remove_by_id", "count_by_form_of_education", "filter_contains_name":
                    request = new Request(arg[0], ((arg.length > 1) ? arg[1] : ""));
                    break;
                case "update":
                    if (arg.length < 2) {
                        System.out.println("укажите id элемента");
                        continue;
                    }
                    StudyGroup studyGroup1 = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
                    request = new Request(arg[0], (arg.length > 1 ? arg[1] : ""),  studyGroup1);
                    break;
                case "save":
                    System.out.println("Команда save доступна только на сервере");
                    logger.warn("Client tried to execute save command");
                    continue;
                case "exit":
                    System.exit(0);
                    request=new Request(commandWithArg);
                    break;
                case "execute_script":
                    if (arg.length < 2) {
                        System.out.println("укажите имя файла");
                        continue;
                    }
                    File scriptFile = new File(arg[1]);
                    if (!scriptFile.exists()) {
                        System.out.println("файл не найден");
                        continue;
                    }


                default:
                    request=new Request(commandWithArg);
                    break;
            }
            String requestId = UUID.randomUUID().toString().substring(0, 8);
            request.setRequestId(requestId);
            MDC.put("requestId", requestId);
            logger.info("Sending command: {}", request.getName());
            logger.debug("Request details: {}", request);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(request);
            os.flush();
            ByteBuffer bf = ByteBuffer.wrap(bytes.toByteArray());
            channel.send(bf, serverAddress);
            ByteBuffer reciveBuffer = ByteBuffer.allocate(65535);
            long startTime = System.currentTimeMillis();
            while (channel.receive(reciveBuffer) == null) {
                if (System.currentTimeMillis() - startTime > 5000) {
                    System.out.println("сервер недоступен");
                    break;
                }
            }
            byte[] bytes1 = reciveBuffer.array();
            reciveBuffer.clear();
            if (bytes1.length>0){
                ObjectInputStream oos1 = new ObjectInputStream(new ByteArrayInputStream(bytes1));
                Response response = (Response) oos1.readObject();
                collectionManager.setCurrentId(response.getCurrentId());
                System.out.println(response.getResponse());
                logger.info("Response received from server");
                MDC.clear();
            }

        }
    }
}
