package com.lgtm;

import com.lgtm.domain.Invoice;
import com.lgtm.domain.Performance;
import com.lgtm.domain.Play;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StatementTest {

    @Test
    @DisplayName("연극 청구 내역을 텍스트 형식으로 출력한다.")
    void testStatement() {
        // given
        Invoice invoice = new Invoice(
                "BigCo",
                List.of(
                        new Performance("hamlet", 55),
                        new Performance("as-like", 35),
                        new Performance("othello", 40)
                )
        );
        Map<String, Play> plays = Map.of(
                "hamlet", new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy")
        );
        String expected = "청구내역 (고객명: BigCo)\n" +
                "Hamlet: $650.00 55석\n" +
                "As You Like It: $580.00 35석\n" +
                "Othello: $500.00 40석\n" +
                "총액: $1730.00\n" +
                "적립 포인트: 47 점";

        // when
        String printString = Statement.statement(invoice, plays);

        // then
        assertThat(printString).isEqualTo(expected);
    }


    @Test
    @DisplayName("연극 청구 내역을 html 형식으로 출력한다.")
    void testHtmlStatement() {
        // given
        Invoice invoice = new Invoice(
                "BigCo",
                List.of(
                        new Performance("hamlet", 55),
                        new Performance("as-like", 35),
                        new Performance("othello", 40)
                )
        );
        Map<String, Play> plays = Map.of(
                "hamlet", new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy")
        );
        String expected = "<h1>청구내역 (고객명: BigCo)</h1>\n" +
                "<table>\n" +
                "<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>\n" +
                "<tr><td>Hamlet</td><td>55</td><td>$650.00</td></tr>\n" +
                "<tr><td>As You Like It</td><td>35</td><td>$580.00</td></tr>\n" +
                "<tr><td>Othello</td><td>40</td><td>$500.00</td></tr>\n" +
                "</table>\n" +
                "<p>총액: <em>$1730.00</em></p>\n" +
                "<p>적립 포인트: <em>47</em> 점</p>";

        // when
        String printString = Statement.htmlStatement(invoice, plays);

        // then
        assertThat(printString).isEqualTo(expected);
    }
}
