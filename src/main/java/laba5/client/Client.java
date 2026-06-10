package laba5.client;

import laba5.server.manager.CollectionManager;
import laba5.server.manager.FileCsvReader;
import laba5.server.manager.InputManager;
import laba5.server.manager.StudyGroupFactory;
import laba5.shared.actions.PasswordHasher;
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
        // Используем порт 7777 (если менял на сервере, поменяй и тут)
        InetSocketAddress serverAddress = new InetSocketAddress("localhost", 7777);
        logger.info("Server Address: {}", serverAddress.getAddress().getHostAddress());

        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);

        InputManager inputManager = new InputManager(scanner);
        CollectionManager collectionManager = new CollectionManager();
        StudyGroupFactory studyGroupFactory = new StudyGroupFactory(collectionManager);
        PasswordHasher passwordHasher = new PasswordHasher();
        Scanner scanner1 = scanner;

        String currentLogin = "";
        String currentPasswordHash = "";
        boolean isAuthenticated = false;

        // ==========================================
        // КОРРЕКТНЫЙ И НАДЁЖНЫЙ ЦИКЛ АВТОРИЗАЦИИ
        // ==========================================
        while (!isAuthenticated) {
            System.out.println("\n--- Авторизация ---");
            System.out.println("1. Войти (login)");
            System.out.println("2. Зарегистрироваться (register)");
            System.out.println("3. Выйти из программы (exit)");
            System.out.print("Выберите действие: ");

            String authChoice = scanner.nextLine().trim();

            if ("3".equals(authChoice) || "exit".equalsIgnoreCase(authChoice)) {
                System.exit(0);
            }

            if (!"1".equals(authChoice) && !"2".equals(authChoice)) {
                System.out.println("Ошибка: Введите 1, 2 или 3.");
                continue;
            }

            System.out.print("Введите логин: ");
            String login = scanner.nextLine().trim();
            System.out.print("Введите пароль: ");
            String rawPassword = scanner.nextLine().trim();

            if (login.isEmpty() || rawPassword.isEmpty()) {
                System.out.println("Ошибка: Логин и пароль не могут быть пустыми!");
                continue;
            }

            String passwordHash = passwordHasher.hash(rawPassword);

            // Создаем сетевой пакет авторизации/регистрации
            String authCommandName = "1".equals(authChoice) ? "login" : "register";
            Request authRequest = new Request(authCommandName, "");
            authRequest.setUserName(login);
            authRequest.setPassword(passwordHash);
            authRequest.setRequestId(UUID.randomUUID().toString().substring(0, 8));

            System.out.println("Отправка запроса на сервер...");
            Response authResponse = sendAndReceive(channel, authRequest, serverAddress);

            if (authResponse == null) {
                System.out.println("Ошибка: Сервер недоступен. Не удалось выполнить авторизацию.");
                continue;
            }

            System.out.println("Ответ сервера: " + authResponse.getResponse());

            // Если сервер подтвердил успех — сохраняем сессию и выходим в основной цикл команд
            if (authResponse.getResponse().contains("успешна") || authResponse.getResponse().contains("Регистрация успешна")) {
                currentLogin = login;
                currentPasswordHash = passwordHash;
                isAuthenticated = true;
                System.out.println("Авторизация прошла успешно. Добро пожаловать, " + currentLogin + "!");
            } else {
                System.out.println("Авторизация отклонена. Попробуйте еще раз.");
            }
        }

        // ==========================================
        // ОСНОВНОЙ ЦИКЛ ОБРАБОТКИ КОМАНД
        // ==========================================
        FileCsvReader fileCsvReader = null;
        while (true) {
            if (!inputManager.getScanner().hasNextLine()) {
                if (inputManager.isInScript()) {
                    inputManager.getScanner().close();
                    inputManager.setScanner(scanner1);
                    inputManager.setInScript(false);
                    continue;
                } else {
                    break;
                }
            }

            String commandWithArg = inputManager.getScanner().nextLine();
            if (commandWithArg.isEmpty()) {
                continue;
            }

            String[] arg = commandWithArg.trim().split(" ", 2);
            Request request = null;

            switch (arg[0]) {
                case "execute_script":
                    if (arg.length < 2) {
                        logger.info("укажите имя файла");
                        continue;
                    }
                    File scriptFile = new File(arg[1]);
                    fileCsvReader = new FileCsvReader(arg[1]);
                    if (!scriptFile.exists()) {
                        logger.info("файл не найден");
                        continue;
                    }
                    scanner1 = inputManager.getScanner();
                    Scanner scriptScanner = new Scanner(scriptFile);
                    inputManager.setScanner(scriptScanner);
                    inputManager.setInScript(true);
                    continue;

                case "add", "add_if_max", "remove_lower":
                    StudyGroup studyGroup;
                    if (inputManager.isInScript() && arg.length > 1) {
                        String[] parsed = fileCsvReader.parsingCSV(arg[1]);
                        studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), parsed);
                    } else {
                        studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
                    }
                    request = new Request(arg[0], null, studyGroup);
                    break;

                case "clear", "remove_by_id", "count_by_form_of_education", "filter_contains_name":
                    request = new Request(arg[0], ((arg.length > 1) ? arg[1] : ""));
                    break;

                case "update":
                    if (arg.length < 2) {
                        logger.info("укажите id элемента");
                        continue;
                    }

                    String idArg = arg[1].trim();

                    // --- ПЕРВЫЙ ЭТАП: Проверяем права на сервере ДО ввода полей ---
                    Request checkRequest = new Request("update", idArg, null);
                    checkRequest.setUserName(currentLogin);
                    checkRequest.setPassword(currentPasswordHash);
                    checkRequest.setRequestId(UUID.randomUUID().toString().substring(0, 8));

                    logger.info("Проверка прав на изменение объекта с ID {}...", idArg);
                    Response checkResponse = sendAndReceive(channel, checkRequest, serverAddress);

                    if (checkResponse == null) {
                        logger.error("Сервер недоступен, невозможно проверить права.");
                        continue;
                    }

                    if (checkResponse.getResponse().contains("Ошибка")) {
                        // Если сервер вернул ошибку прав — выводим её и прерываем выполнение
                        logger.info(checkResponse.getResponse());
                        continue;
                    }

                    // --- ВТОРОЙ ЭТАП: Если сервер разрешил, запускаем интерактивный ввод полей ---
                    logger.info("Права подтверждены. Введите новые данные объекта:");
                    StudyGroup studyGroup1 = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
                    request = new Request(arg[0], idArg, studyGroup1);
                    break;

                case "save":
                    logger.info("Команда save доступна только на сервере");
                    logger.warn("Client tried to execute save command");
                    continue;

                case "exit":
                    System.exit(0);
                    break;

                default:
                    request = new Request(arg[0], ((arg.length > 1) ? arg[1] : ""));
                    break;
            }

            // Навешиваем метаданные безопасности на готовый запрос перед отправкой
            if (request != null) {
                String requestId = UUID.randomUUID().toString().substring(0, 8);
                request.setRequestId(requestId);
                request.setUserName(currentLogin);
                request.setPassword(currentPasswordHash);

                MDC.put("requestId", requestId);

                // Выполняем отправку основной команды
                Response response = sendAndReceive(channel, request, serverAddress);

                if (response == null) {
                    logger.info("сервер недоступен");
                } else {
                    collectionManager.setCurrentId(response.getCurrentId());
                    logger.info(response.getResponse());
                    MDC.clear();
                }
            }
        }
    }

    /**
     * Вынесенный вспомогательный метод отправки пакета и ожидания ответа по UDP (с 3 попытками)
     */
    private static Response sendAndReceive(DatagramChannel channel, Request request, InetSocketAddress serverAddress) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(request);
            os.flush();
            ByteBuffer bf = ByteBuffer.wrap(bytes.toByteArray());

            int maxAttempts = 3;
            int attempt = 0;
            Response response = null;

            while (attempt < maxAttempts && response == null) {
                attempt++;
                bf.rewind();
                channel.send(bf, serverAddress);

                ByteBuffer receiveBuffer = ByteBuffer.allocate(65535);
                long startTime = System.currentTimeMillis();

                while (channel.receive(receiveBuffer) == null) {
                    if (System.currentTimeMillis() - startTime > 4000) { // таймаут 4 секунды
                        break;
                    }
                }

                if (receiveBuffer.position() > 0) {
                    byte[] bytes1 = receiveBuffer.array();
                    receiveBuffer.clear();
                    try {
                        ObjectInputStream oos1 = new ObjectInputStream(new ByteArrayInputStream(bytes1));
                        response = (Response) oos1.readObject();
                        if (!request.getRequestId().equals(response.getRequestID())) {
                            response = null; // Игнорируем чужой ID пакета
                        }
                    } catch (Exception e) {
                        response = null;
                    }
                }
            }
            return response;
        } catch (IOException e) {
            logger.error("Ошибка сети: " + e.getMessage());
            return null;
        }
    }
}