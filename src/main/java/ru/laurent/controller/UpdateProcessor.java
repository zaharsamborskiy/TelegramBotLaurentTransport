package ru.laurent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.laurent.dto.MailParams;
import ru.laurent.service.ExcelFile;
import ru.laurent.service.impl.MailSenderServiceImpl;
import ru.laurent.utils.EmojiBot;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j
@RequiredArgsConstructor
public class UpdateProcessor {
    private TelegramBot telegramBot;
    private Workbook workbook = new XSSFWorkbook();
    private Sheet sheet = workbook.createSheet();
    private ExcelFile excelFile = new ExcelFile(workbook,sheet);
    private Map<Long, String> userStates = new HashMap<>();
    private Map<Long, String[]> userResponses = new HashMap<>();
    private EmojiBot emojiBot = new EmojiBot();
    @Autowired
    MailSenderServiceImpl mailSenderService1;
    @Autowired
    MailParams mailParams1;


    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String userMessage = update.getMessage().getText();
            if (!userStates.containsKey(chatId)) {
                startSurvey(chatId);
            } else {
                handleResponse(chatId, userMessage);
            }
        }
    }
    private void startSurvey(long chatId) {
        sendMessage(chatId, new StringBuilder("Приветствую")
                .append(emojiBot.getEmojiHello())
                .append("\n")
                .append("Введите дату")
                .append(emojiBot.getEmojiDate())
                .append("\n")
                .append("(формат ввода: 21.09.2024)").toString());
        userStates.put(chatId, "question1");
    }
    private void handleResponse(long chatId, String response) {
        String currentState = userStates.get(chatId);
        String[] responses = userResponses.getOrDefault(chatId, new String[9]);

        switch (currentState) {
            case "question1":
                responses[0] = response; // Сохраняем дату
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question2");
                sendMessage(chatId, new StringBuilder("Введите гос.номер")
                        .append(emojiBot.getEmojiFactory())
                        .append("\n")
                        .append("(формат ввода: А555АА) :")
                        .toString());
                break;
            case "question2":
                responses[1] = response; // Сохраняем гос. номер
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question3");
                sendMessage(chatId, new StringBuilder("Введите ФИО")
                        .append(emojiBot.getEmojiTruck())
                        .append("\n")
                        .append("(формат ввода: Иванов Иван Иванович или Иванов И.И.) :")
                        .toString());
                break;
            case "question3":
                responses[2] = response; // Сохраняем ФИО
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question4");
                sendMessage(chatId, new StringBuilder("Введите маршрут")
                        .append(emojiBot.getEmojiMotorway())
                        .append("\n")
                        .append("(формат ввода: Ярск или Назарово - Ачинск) :")
                        .toString());
                break;
            case "question4" :
                responses[3] = response; // Сохраняем маршрут
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question5");
                sendMessage(chatId, new StringBuilder("Введите фирму")
                        .append(emojiBot.getEmojiFuel())
                        .append("\n")
                        .append("(формат ввода: ИП Иванов, ООО Лаурент) :")
                        .toString());
                break;
            case "question5" :
                responses[4] = response; // Сохраняем фирму
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question6");
                sendMessage(chatId, new StringBuilder("Введите ставку")
                        .append(emojiBot.getEmojiDollar())
                        .append("\n")
                        .append("(формат ввода: 15000) :")
                        .toString());
                break;
            case "question6" :
                responses[5] = response; // Сохраняем ставку
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question7");
                sendMessage(chatId, new StringBuilder("Введите начальное показание одометра")
                        .append(emojiBot.getEmojiTruck())
                        .append("\n")
                        .append("(формат ввода: 123827)")
                        .toString());
                break;
            case "question7" :
                responses[6] = response; // сохраняем нач. пок. одом.
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question8");
                sendMessage(chatId, new StringBuilder("Введите конечное показание одометра")
                        .append(emojiBot.getEmojiTruck())
                        .append("\n")
                        .append("формат ввода: 130553")
                        .toString());
                break;
            case "question8" :
                responses[7] = response; // сохраняем кон. пок. одом.
                userResponses.put(chatId, responses);
                userStates.put(chatId, "question9");
                sendMessage(chatId, new StringBuilder("Введите комментарии")
                        .append(emojiBot.getEmojiTruck())
                        .append("\n")
                        .append("Если комментария нет поставьте -")
                        .toString());
                break;
            case "question9" :
                responses[8] = response; // Сохраняем комментарии
                userResponses.put(chatId, responses);
                userStates.remove(chatId);
                sendMessage(chatId, new StringBuilder("Ваши введенные данные:")
                        .append("\n").append("Дата: ").append(responses[0])
                        .append("\n").append("Гос.Номер: ").append(responses[1])
                        .append("\n").append("ФИО: ").append(responses[2])
                        .append("\n").append("Маршрут: ").append(responses[3])
                        .append("\n").append("Фирма: ").append(responses[4])
                        .append("\n").append("Ставка: ").append(responses[5])
                        .append("\n").append("Начальное показание одометра: ").append(responses[6])
                        .append("\n").append("Конечное показание одометра: ").append(responses[7])
                        .append("\n").append("Комментарии: ").append(responses[8])
                        .append("\n").append("Для начала ввода новых данных введите /start").toString());
                if (!excelFile.isActive()){
                    excelFile.createHeaders(workbook);
                    excelFile.setActive(true);
                }
                excelFile.createNewRow(responses);
                mailSenderService1.send(mailParams1);
                break;
            default:
                sendMessage(chatId, "Что-то пошло не так. Начните заново, написав /start.");
                userStates.remove(chatId);
                break;
        }
    }

    private void sendMessage(long chatId, String message) {
        telegramBot.sendAnswerMessage(new SendMessage(String.valueOf(chatId), message));
    }

}
