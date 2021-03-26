import java.util.Scanner;

public class MainCalculator {

    public static void main(String[] args) {
        DataCalculator data = new DataCalculator();

        System.out.println(
                "Введите математическое выражение состоящее из двух натуральных чисел" +
                        " от 1 до 10 и знака операции (+,-,*,/) между ними.\n" +
                        "Пример: 1+10\n" +
                        "Допускается использовать арабские или римские цифры.\n" +
                        "Не допускается использовать арабские и римские цифры вместе.");

        do {
            try {
                data.read();
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                continue;
            }
            int result = Calculator.calculate(data.getVar1(), data.getVar2(), data.getOper());
            System.out.println(result);
        } while (true);
    }

    static class DataCalculator extends MainCalculator {

        int x;
        int y;
        char operation;
        boolean flag;

        public void read() {

            Scanner scanner = new Scanner(System.in); // объявляем класс сканер
            ArabicToRoman arabicToRoman = new ArabicToRoman();
            String expression = scanner.nextLine(); // сканер считывает строку

            try {
                String[] blocks = expression.split("[+-/*]");
                String[] roman = {"X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};



                for (int i = 0; i < roman.length; i++){
                    if (roman[i].equals(blocks[0])){
                        flag = true;
                    }
                    if(flag){
                        x = arabicToRoman.romanToArab(blocks[0]);
                        y = arabicToRoman.romanToArab(blocks[1]);
                    } else {
                        x = Integer.parseInt(blocks[0]);
                        y = Integer.parseInt(blocks[1]);
                    }
                    operation = expression.charAt(blocks[0].length());
                }

                if ((x > 10 || x < 1) || (y > 10 || y < 1)) {
                    throw new IllegalArgumentException();
                }

            } catch (RuntimeException e) {
                throw new IllegalArgumentException("Неверный формат данных");
            }
        }

        public int getVar1() {
            return x;
        }

        public int getVar2() {
            return y;
        }

        public char getOper() {
            return operation;
        }
    }

    static class Calculator {
        private Calculator() {
        }

        public static int calculate(int number1, int number2, char operation) {
            int result;
            switch (operation) {
                case '+':
                    result = number1 + number2;
                    break;
                case '-':
                    result = number1 - number2;
                    break;
                case '*':
                    result = number1 * number2;
                    break;
                case '/':
                    result = number1 / number2;
                    break;
                default:
                    throw new IllegalArgumentException("Неверный знак операции");
            }
            return result;
        }
    }

    static class ArabicToRoman {
        String[] Rome = {"X", "IX", "V", "IV", "I"};
        int[] Arab = {10, 9, 5, 4, 1};

        int romanToArab(String rome) {

            StringBuilder romeNumber = new StringBuilder(rome);
            int arabNumber = 0, i = 0;

            if (romeNumber.length() > 0) {
                while (true) {
                    do {
                        if (Rome[i].length() <= romeNumber.length()) {

                            if (Rome[i].equals(romeNumber.substring(0,
                                    Rome[i].length()))) {

                                arabNumber += Arab[i];

                                romeNumber.delete(0, Rome[i].length());
                                if (romeNumber.length() == 0)
                                    return arabNumber;
                            }
                            else
                                break;
                        }
                        else
                            break;
                    }
                    while
                    (romeNumber.length() != 0);
                    i++;
                }
            }
            return 0;
        }
    }

}
