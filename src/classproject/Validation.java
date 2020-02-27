package classproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Validation {

    public static final List<String> INVALID_CHARACTERS = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "&", "(", ")", ".", "-", "!", "@", "#", "$", "%", "^", "*", "+", "_", "/");
    public static final List<String> INVALID_CHARACTERS_NO_NUMBER = Arrays.asList("&", "(", ")", ".", "-", "!", "@", "#", "$", "%", "^", "*", "+", "_", "/");
    public static final List<String> INVALID_CHARACTERS_NO_SHARP = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "&", "(", ")", ".", "-", "!", "@", "$", "%", "^", "*", "+", "_", "/");
    public static final String NUMBERS_ONLY = "^[0-9]*$";

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_DB = "dd-MM-yyyy";
    public static final String DATE_PATTERN = "([0-9]{2})/([0-9]{2})/([0-9]{4})";
    public static final SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);

    public static boolean stringChecker(String name, List<String> invalidCharacters) {
        for (String s : invalidCharacters) {
            if (name.contains(s)) {
                return false;
            }
        }
        return true;
    }

    public static Date parseDate(String date) throws ParseException {
            return sf.parse(date);

    }

    public static String formatDate(Date date) {
        return sf.format(date);
    }


}
