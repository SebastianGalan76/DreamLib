package pl.dream.dreamlib;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.dream.dreamlib.gradient.Gradient;

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
    public static @NotNull String fix(@NotNull String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    /**
     * This function translate alternate color code.
     *
     * @param messageList The list of strings we want to convert
     * @return The converted message
     */
    public static @NotNull List<String> fix(@NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return messageList;
        }

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
    public static @NotNull String fixRGB(@NotNull String message) {
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
    public static @NotNull List<String> fixRGB(@NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return messageList;
        }

        List<String> fixedMessageList = new ArrayList<>();
        for(String message:messageList){
            fixedMessageList.add(fixRGB(message));

        }

        return fixedMessageList;
    }

    /**
     * This function repairs RGB color codes, gradient(LinearInterpolator) and formatting placeholders.
     *
     * @param message The message we want to convert
     * @return The converted message
     */
    public static @NotNull String fixAll(@NotNull String message){
        message = Gradient.fixLinear(message);
        message = fixRGB(message);

        return message;
    }

    /**
     * This function repairs RGB color codes, gradient(LinearInterpolator) and formatting placeholders
     * for entire list of strings
     *
     * @param messageList The list of strings we want to convert
     * @return The converted list of strings
     */
    public static @NotNull List<String> fixAll(@NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return messageList;
        }

        List<String> fixedMessageList = new ArrayList<>();
        for(String message:messageList){
            fixedMessageList.add(fixAll(message));

        }

        return fixedMessageList;
    }

    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param message The string we want to send to the player
     */
    public static void sendMessage(@NotNull Player player, @NotNull String message){
        player.sendMessage(fixAll(message));
    }

    /**
     * Sends the specified message to the specified player.
     *
     * @param player Message recipient
     * @param messageList The list of strings we want to send to the player
     */
    public static void sendMessage(@NotNull Player player, @NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

        messageList = fixAll(messageList);
        for(String message:messageList){
            player.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param message The string we want to send to the sender
     */
    public static void sendMessage(@NotNull CommandSender sender, @NotNull String message){
        sender.sendMessage(fixAll(message));
    }

    /**
     * Sends the specified message to the specified command sender.
     *
     * @param sender Message recipient
     * @param messageList The list of strings we want to send to the sender
     */
    public static void sendMessage(@NotNull CommandSender sender, @NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

        messageList = fixAll(messageList);
        for(String message:messageList){
            sender.sendMessage(message);
        }
    }

    /**
     * Sends the specified message to all online players.
     * This method doesn't send the message to the console.
     *
     * @param message The string we want to send to all online players
     */
    public static void sendGlobalMessage(@NotNull String message){
        message = fixAll(message);

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
    public static void sendGlobalMessage(@NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

        messageList = fixAll(messageList);

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
    public static void sendBroadcast(@NotNull String message){
        Bukkit.getServer().broadcastMessage(fixAll(message));
    }

    /**
     * Sends the specified message to all online players
     * and to the console.
     *
     * @param messageList The list of strings we want to broadcast
     */
    public static void sendBroadcast(@NotNull List<String> messageList){
        if(messageList.isEmpty()){
            return;
        }

        messageList = fixAll(messageList);
        Server server = Bukkit.getServer();

        for(String message:messageList){
            server.broadcastMessage(message);
        }
    }
}
