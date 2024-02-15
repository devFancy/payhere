package com.payhere.product.domain;

public class ChoSungQuery {
    private static final char[] CHO_SUNG = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    public static String extractChoSung(final String word) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (ch >= '가' && ch <= '힣') {
                int choSungIndex = (ch - '가') / 588;
                sb.append(CHO_SUNG[choSungIndex]);
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
