package Entity;

public class Validator {
    //Must at least one uppercase letter in Username
    private static final String usernameRegex = ".*[A-Z].*";

    //Must have at least one number, lower and uppercase letters and symbols
    private static final String passwordRegex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,12}$";

    //Must end with @xxxxxx.com
    private static final String emailRegex = "^.+@\\w+\\.com$";

    //Validate YYYY-MM-DD
    private static final String dateRegex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    //Validate IC have 12 number only
    private static final String icRegex = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])((01|21|22|23|24|02|25|26|27|03|28|29|04|30|05|31|59|06|32|33|07|34|35|08|36|37|38|39|09|40|10|41|42|43|44|11|45|46|12|47|48|49|13|50|51|52|53|14|54|55|56|57|15|58|16|82))\\d{4}$";


    public static boolean validateUsername(String username) {
        return username.matches(usernameRegex);
    }

    public static boolean validatePassword(String password) {
        return password.matches(passwordRegex);
    }

    public static boolean validateEmail(String email) {
        return email.matches(emailRegex);
    }

    public static boolean validateHireDate(String date) {
        return date.matches(dateRegex);
    }

    public static boolean validateDOB(String date) {
        return date.matches(dateRegex);
    }

    public static boolean validateIc(String ic) {
        return ic.matches(icRegex);
    }
}
