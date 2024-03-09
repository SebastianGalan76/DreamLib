package pl.dream.dreamlib;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {
    /**
     * This function translate alternate color code.
     *
     * @param message The message we want to convert
     * @return The converted message
     */
    public static String fix(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    /**
     * This function translate alternate color code.
     *
     * @param messageList The list of strings we want to convert
     * @return The converted message
     */
    public static List<String> fix(List<String> messageList){
        List<String> fixedMessageList = new ArrayList<>();
        for(String message:messageList){
            fixedMessageList.add(fix(message));

        }

        return fixedMessageList;
    }

    /**
     * This function repairs RGB color codes and formatting placeholders.
     *
     * @param message The message we want to convert
     * @return The converted message
     */
    public static String fixRGB(String message) {
        Pattern unicode = Pattern.compile("\\\\u\\+[a-fA-F0-9]{4}");
        Matcher match = unicode.matcher(message);
        while (match.find()) {
            String code = message.substring(match.start(),match.end());
            message = message.replace(code,Character.toString((char) Integer.parseInt(code.replace("\\u+",""),16)));
            match = unicode.matcher(message);
        }
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        match = pattern.matcher(message);
        while (match.find()) {
            String color = message.substring(match.start(),match.end());
            message = message.replace(color,net.md_5.bungee.api.ChatColor.of(color.replace("&","")) + "");
            match = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    /**
     * This function repairs RGB color codes and formatting placeholders
     * for entire list of strings
     *
     * @param messageList The list of strings we want to convert
     * @return The converted list of strings
     */
    public static List<String> fixRGB(List<String> messageList){
        List<String> fixedMessageList = new ArrayList<>();
        for(String message:messageList){
            fixedMessageList.add(fixRGB(message));

        }

        return fixedMessageList;
    }

    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param message The string we want to send to the player
     */
    public static void sendMessage(Player player, String message){
        player.sendMessage(fixRGB(message));
    }

    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param messageList The list of strings we want to send to the player
     */
    public static void sendMessage(Player player, List<String> messageList){
        for(String message:messageList){
            player.sendMessage(fixRGB(message));
        }
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param message The string we want to send to the sender
     */
    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(fixRGB(message));
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param messageList The list of strings we want to send to the sender
     */
    public static void sendMessage(CommandSender sender, List<String> messageList){
        for(String message:messageList){
            sender.sendMessage(fixRGB(message));
        }
    }

    /**
     * Sends the specified message to all online players.
     * This method doesn't send the message to the console.
     *
     * @param message The string we want to send to all online players
     */
    public static void sendGlobalMessage(String message){
        message = fixRGB(message);

        for(Player player: Bukkit.getOnlinePlayers()){
            player.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to all online players.
     * This method doesn't send the message to the console.
     *
     * @param messageList The list of strings we want to send to all online players
     */
    public static void sendGlobalMessage(List<String> messageList){
        messageList = fixRGB(messageList);

        for(Player player: Bukkit.getOnlinePlayers()){
            for(String message:messageList){
                player.sendMessage(message);
            }
        }
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param message The string we want to broadcast
     */
    public static void sendBroadcast(String message){
        Bukkit.getServer().broadcastMessage(fixRGB(message));
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param messageList The list of strings we want to broadcast
     */
    public static void sendBroadcast(List<String> messageList){
        messageList = fixRGB(messageList);
        Server server = Bukkit.getServer();

        for(String message:messageList){
            server.broadcastMessage(message);
        }
    }
}
