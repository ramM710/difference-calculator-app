package com.calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author empc46
 */
public class DifferenceCalculator {

    private static Scanner input;

    private static String[] serialSet;

    private static Integer firstNum, secondNum;

    static String tempSecondNum = "";

    private static Boolean canProceed;

    static {

        serialSet = new String[]{"111  116  166  666", "112  117  126  167  266  667",
            "113  118  136  168  366  668", "114  119  146  169  466  669",
            "110  115  156  160  566  660", "122  127  177  226  267  677",
            "123  128  137  178  236  268  367  678", "124  129  147  179  246  269  467  679",
            "120  125  157  170  256  260  567  670", "133  138  188   336  368  688",
            "134  139  148  189  346  369  468  689", "130  135  158  180  356  360  568  680",
            "144  149  199  446  469  699", "140  145  159  190  456  460  569  690",
            "100  150  155   556  560  600", "222  227  277  777",
            "223   228  237   278  377  778", "224   229   247  279  477  779",
            "220  225   257  270  577  770", "233  238  288  337  378  788",
            "234  239  248  289  347   379  478  789", "230  235  258  280  357  370  578  780",
            "244  249  299  447  479  799", "240  245   259   290   457  470  579   790",
            "200  250  255  557  570  700", "333   338  388  888",
            "334   339   348  389  488  889", "330  335  358   380   588   880",
            "344  349   399  448  489  899", "340  345  359  390  458  480  589  890",
            "300   350  355    558   580   800", "444   449    499   999",
            "440   445   459   490   599   990", "400   450   455   559   590   900",
            "000   500   550   555"};
    }

    public static void main(String[] args) {
        input = new Scanner(System.in);
        getInputs();
    }

    private static Integer askInput() {
        try {
            int num = input.nextInt();
            if (num <= 100 || num >= 999) {
                System.out.println("wrong length of number!!!");
                num = askInput();
            }
            return num;
        } catch (Exception e) {
            System.exit(0);
            return null;
        }
    }

    private static void getInputs() {
        firstNum = askInput();
        secondNum = askInput();
        tempSecondNum = String.valueOf(secondNum);
        canProceed = false;

        diffCalculation();

        // re-call the mechanism
//        System.out.println("\n\n New Iteration !!");
        getInputs();
    }

    private static String diffCalculation() {
        String firstTemp = Integer.toString(firstNum);
        int[] firstNumArr = new int[firstTemp.length()];
        for (int i = 0; i < firstTemp.length(); i++) {
            firstNumArr[i] = firstTemp.charAt(i) - '0';
        }

        String secondTemp = Integer.toString(secondNum);
        int[] secondNumArr = new int[secondTemp.length()];
        for (int i = 0; i < secondTemp.length(); i++) {
            secondNumArr[i] = secondTemp.charAt(i) - '0';
        }

        StringBuilder diffNumBuilder = new StringBuilder();
        for (int i = 0; i < firstNumArr.length; i++) {
            if (secondNumArr.length < firstNumArr.length) {
                int[] tempArr = secondNumArr;
                secondNumArr = new int[firstNumArr.length];

                for (int j = 0; j < tempArr.length; j++) {
                    secondNumArr[j] = tempArr[j];
                }

                for (int k = tempArr.length; k < firstNumArr.length; k++) {
                    secondNumArr[k] = 0;
                }
            }

            int firstNumVal = firstNumArr[i];
            int secondNumVal = secondNumArr[i];

            if (firstNumArr[i] == 0) {
                firstNumVal = 10;
            }

            if (secondNumArr[i] == 0) {
                secondNumVal = 10;
            }

            diffNumBuilder.append(Math.abs(firstNumVal - secondNumVal));
        }

        //organize the number order
        String arrangeNumber = arrangeNumberOrder(diffNumBuilder.toString());
        if (!arrangeNumber.isEmpty()) {
            return arrangeNumber;
        }

        return null;
    }

    private static String arrangeNumberOrder(String diffNum) {
        Integer[] diffNumArr = new Integer[diffNum.length()];
        for (int i = 0; i < diffNum.length(); i++) {
            diffNumArr[i] = diffNum.charAt(i) - '0';
        }

        Collections.sort(Arrays.asList(diffNumArr));

        StringBuilder builder = new StringBuilder();
        for (Integer val : diffNumArr) {
            if (val != 0) {
                builder.append(val);
            }
        }

        if (builder.length() != diffNumArr.length) {
            int diffCount = diffNumArr.length - builder.length();

            for (int i = 0; i < diffCount; i++) {
                builder.append("0");
            }
        }

        //tempSecondNum = builder.toString();
        //validate the differnce number exists in the serial array or not
        String diff = diffNum.concat("...").concat(builder.toString());
        if (!canProceed) {
            System.out.println(diff + "\n");

            canProceed = true;
            validateNumberExistsInSeries();
        }

        return diff;
    }

    private static void validateNumberExistsInSeries() {
        try {
            for (String mainSeries : serialSet) {
                String[] seriesArr = mainSeries.split(" ");
                if (Arrays.asList(seriesArr).contains(tempSecondNum)) {
//                StringBuilder seriesDiffBuilder = new StringBuilder();
                    for (String matchedSeries : seriesArr) {
                        if (!matchedSeries.isEmpty()) {
                            secondNum = Integer.parseInt(matchedSeries);

                            System.out.println(firstNum);
                            System.out.println(matchedSeries);

                            String diff = diffCalculation();

                            System.out.println(diff + "\n");
                        }
                    }

//                System.out.println(mainSeries);
//                System.out.println(seriesDiffBuilder.toString());
                    canProceed = false;
                }
            }

            Thread.sleep(1000);
            // clear the console screen...
            clearScreen();
        } catch (Exception e) {
        }
    }

    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[J");
                System.out.flush();
            }
        } catch (Exception | Error e) {
            System.out.println(e.getMessage());
        }
    }
}
