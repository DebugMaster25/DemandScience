public class EmailValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }
}