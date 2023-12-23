package com.lgtm;

import com.lgtm.domain.Invoice;
import com.lgtm.domain.Performance;
import com.lgtm.domain.Play;

import java.util.Map;

public class Statement {
    public static void main(String[] args) {
    }

    public static String statement(Invoice invoice, Map<String, Play> plays) {
        int totalAmount = 0;
        int volumeCredit = 0;

        invoice.getCustomer();
        StringBuilder result = new StringBuilder("청구내역 (고객명: " + invoice.getCustomer() + ")\n");

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayID());
            if (play == null) {
                throw new RuntimeException("알 수 없는 장르");
            }
            int thisAmount = 0;

            switch (play.getType()) {
                case "tragedy":
                    thisAmount = 40000;
                    if (performance.getAudience() > 30) {
                        thisAmount += 1000 * (performance.getAudience() - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (performance.getAudience() > 20) {
                        thisAmount += 10000 + 500 * (performance.getAudience() - 20);
                    }
                    thisAmount += 300 * performance.getAudience();
                    break;
                default:
                    throw new RuntimeException("알 수 없는 장르: " + play.getType());
            }

            // 포인트를 적립한다.
            volumeCredit += Math.max(performance.getAudience() - 30, 0);

            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if (play.getType() == "comedy") {
                volumeCredit += (performance.getAudience() / 5);
            }

            // 청구 내역을 출력한다.
            result.append(play.getName() + ": " + String.format("$%.2f", thisAmount / 100.0) + " " + performance.getAudience() + "석\n");
            totalAmount += thisAmount;
        }

        result.append("총액: " + String.format("$%.2f", totalAmount / 100.0) + "\n");
        result.append("적립 포인트: " + volumeCredit + " 점");
        return result.toString();
    }

    public static String htmlStatement(Invoice invoice, Map<String, Play> plays) {
        // todo: Ch 1.6, p.60 코드 참고
        return "";
    }
}