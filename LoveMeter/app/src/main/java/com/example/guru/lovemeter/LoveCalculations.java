package com.example.guru.lovemeter;

public class LoveCalculations {

    String name, partnerName;

    public LoveCalculations(String name, String partnerName) {
        this.name = name.toLowerCase();
        this.partnerName = partnerName.toLowerCase();
    }

    public double calc() {
        int sumName = find(name);
        int sumPartnerName = find(partnerName);

        if (sumName == 0 || sumPartnerName == 0) {
            return 0.0;
        }

        if (sumName < sumPartnerName) {
            double temp = sumName * 100.0 / sumPartnerName;

            return temp;
        }
        else {
            double temp = sumPartnerName * 100.0 / sumName;
            return temp;
        }
    }

    private int find(String str) {
        int sum = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) - 96 > 0 && str.charAt(i) - 96 < 27) {
                sum += str.charAt(i) - 96;
            }
        }
        int result = sumHelp(sum);

        return result;
    }

    private int sumHelp(int temp) {

        int sum = temp;
        int result = 0;

        while (sum > 0) {
            result += sum % 10;
            sum /= 10;
        }

        if (result > 9) {
            result = sumHelp(result);
        }

        return result;
    }
}
