package com.wefin.wefin.util;

public class IdentifierValidator {

    public static boolean isCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }

        cpf = StringUtils.removeAllExceptNumber(cpf);

        if (cpf.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }

        if (firstDigit != cpf.charAt(9) - '0') {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }

        return secondDigit == cpf.charAt(10) - '0';
    }

    public static boolean isCnpj(String cnpj) {
        cnpj = StringUtils.removeAllExceptNumber(cnpj);

        if (cnpj.length() != 14) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (cnpj.charAt(i) - '0') * ((i % 8) + 2);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }

        if (firstDigit != cnpj.charAt(12) - '0') {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += (cnpj.charAt(i) - '0') * ((i % 8) + 2);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }

        return secondDigit == cnpj.charAt(13) - '0';
    }

    public static boolean isStudent(String identifier) {
        identifier = StringUtils.removeAllExceptNumber(identifier);

        if (identifier.length() != 8) {
            return false;
        }

        String firstDigit = identifier.substring(0, 1);
        String lastDigit = identifier.substring(identifier.length() - 1);

        int firstDigitInt = Integer.parseInt(firstDigit);
        int lastDigitInt = Integer.parseInt(lastDigit);

        return firstDigitInt + lastDigitInt == 9;
    }

    public static boolean isRetiree(String identifier) {
        identifier = StringUtils.removeAllExceptNumber(identifier);

        if (identifier.length() != 10) {
            return false;
        }

        String lastDigit = identifier.substring(identifier.length() - 1);

        for (int i = 0; i < 9; i++) {
            if (identifier.substring(i, i + 1).equals(lastDigit)) {
                return false;
            }
        }

        return true;
    }
}
