package edu.nuaa.redpocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/6 15:59
 */
public class PocketMoney {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            List<String> randomMoney = new PocketMoney().generateRandomMoney(10, 100.0);
            System.out.printf("第%d次,生成的随机序列为:%s%n", i + 1, Arrays.toString(randomMoney.toArray()));
        }
    }

    /**
     * 随机法
     * @param personCount
     * @param total
     * @return
     */
    public List<String> generateRandomMoney(int personCount, double total) {
        int totalVal = (int) (100 * total);
        if (totalVal < personCount)
            throw new IllegalArgumentException("total money should not be less than " + personCount * 0.01);
        List<String> result = new ArrayList<>();
        int maxPerPerson = totalVal * (personCount - 1) / personCount;
        int remain = totalVal;
        for (int i = 0; i < personCount - 1; ) {
            int current = (int) (Math.random() * maxPerPerson * Math.random() * Math.random());
            current = Math.max(current, 1);
            if (!isValid(remain - current, personCount - i)) {
                continue;
            }
            remain -= current;
            result.add(String.format("%.2f", current / 100.0));
            if (i == personCount - 2) {
                result.add(String.format("%.2f", remain / 100.0));
            }
            i++;
        }
        return result;
    }

    private boolean isValid(int totalValRemain, int totalPersonRemain) {
        return totalValRemain >= totalPersonRemain;
    }
}
