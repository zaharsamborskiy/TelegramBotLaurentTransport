package ru.laurent.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.Data;

@Data
public class EmojiBot {
    String emojiTruck = EmojiParser.parseToUnicode(":truck:");
    String emojiHello = EmojiParser.parseToUnicode(":wave:");
    String emojiFactory = EmojiParser.parseToUnicode(":factory:");
    String emojiMotorway = EmojiParser.parseToUnicode(":motorway:");
    String emojiDollar = EmojiParser.parseToUnicode(":dollar:");
    String emojiDate = EmojiParser.parseToUnicode(":date:");
    String emojiFuel = EmojiParser.parseToUnicode(":fuelpump:");
}
