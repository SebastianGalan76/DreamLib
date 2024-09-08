package pl.dream.dreamlib;

import java.util.List;

public class Text {
    /**
     * Centers the line in the list that has <center> tag
     *
     * @param list The list of strings to be centered
     */
    public static void center(List<String> list){
        int maxLength = getListMaxLength(list);

        centerList(list, maxLength);
    }

    /**
     * Centers the line in the list that has <center> tag
     *
     * @param name The name of the item using to calculate the max length of the GUI
     * @param lore the lore of the item to be centered
     */
    public static void centerLore(String name, List<String> lore){
        int loreMaxLength = getListMaxLength(lore);
        int nameLength = getTextLength(name);

        int maxLength = Math.max(loreMaxLength, nameLength);

        centerList(lore, maxLength);
    }

    /**
     * Centers the specified text within a total length of maxLength characters.
     *
     * @param text The text to be centered
     * @param maxLength The maximum length to which the text should be centered
     * @return The centered text
     */
    public static String center(String text, int maxLength){
        if(!text.contains("<center>")){
            return text;
        }
        StringBuilder finalText = new StringBuilder();

        int prefixIndex = text.indexOf("<center>");
        String prefix = text.substring(0, prefixIndex);

        int suffixIndex = text.length();
        String suffix = "";
        if(text.contains("</center>")){
            suffixIndex = text.indexOf("</center>");
            suffix = text.substring(suffixIndex+9);
        }

        String middleText = text.substring(prefixIndex + 8, suffixIndex);

        int prefixLength = getTextLength(prefix);
        int suffixLength = getTextLength(suffix);
        int middleLength = getTextLength(middleText);

        int length = maxLength - prefixLength - suffixLength - middleLength;
        int padding = length / 2;

        String spaces = " ".repeat(padding);
        middleText = spaces + middleText;

        if(!suffix.isEmpty()){
            middleText+= spaces;
            if(length%2!=0){
                middleText += " ";
            }
        }

        finalText.append(prefix).append(middleText).append(suffix);
        return finalText.toString();
    }

    /**
     * Calculates the max length of list without color codes and <center> tag
     *
     * @param list The list for which the max length is to be calculated
     * @return Returns the max length of the provided list
     */
    public static int getListMaxLength(List<String> list){
        if(list==null){
            return 0;
        }

        int maxLength = 0;
        for(String line:list){
            maxLength = Math.max(maxLength, getTextLength(line));
        }

        return maxLength;
    }

    /**
     * Calculates the length of text without color codes and <center> tag
     *
     * @param text The text for which the length is to be calculated
     * @return Returns the length of the provided text
     */
    public static int getTextLength(String text){
        String cleanedText = text.replaceAll("<&#\\w{6}>", "")
                .replaceAll("</&#\\w{6}>", "")
                .replaceAll("&#\\w{6}", "")
                .replaceAll("&\\w", "")
                .replaceAll("<center>", "")
                .replaceAll("</center>", "");

        return cleanedText.length();
    }

    private static void centerList(List<String> text, int maxLength){
        text.replaceAll(s -> center(s, maxLength));
    }
}
