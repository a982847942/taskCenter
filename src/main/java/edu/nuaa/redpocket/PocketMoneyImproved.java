package edu.nuaa.redpocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/6 15:57
 */
public class PocketMoneyImproved {
    static class RedPocket {
        /**
         * 单位为分，规避浮点数的精度问题
         */
        private Integer remainMoney;

        public Integer getRemainMoney() {
            return remainMoney;
        }

        public Integer getRemainSize() {
            return remainSize;
        }

        private Integer remainSize;

        public RedPocket(Integer remainMoney, Integer remainSize) {
            if (remainMoney < remainSize) {
                throw new IllegalArgumentException("total money as cent should be at least the same as total person count.");
            }
            this.remainMoney = remainMoney;
            this.remainSize = remainSize;
        }

        public void setRemainMoney(int remainMoney) {
            this.remainMoney = remainMoney;
        }

        public void setRemainSize(Integer remainSize) {
            this.remainSize = remainSize;
        }
    }

    /**
     * 二倍均值法
     * @param redPocket
     * @return
     */
    public static String getRandomMoney(RedPocket redPocket) {
        Integer remainMoney = redPocket.getRemainMoney();
        Integer remainSize = redPocket.getRemainSize();
        if (remainSize <= 1) {
            redPocket.setRemainSize(0);
            return String.format("%.2f", remainMoney / 100.0);
        }
        Random random = new Random();
        int max = remainMoney / remainSize * 2;
        int current = (int) (random.nextDouble() * max);
        current = Math.max(1, current);
        redPocket.setRemainMoney(remainMoney - current);
        remainSize--;
        redPocket.setRemainSize(remainSize);
        return String.format("%.2f", current / 100.0);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            RedPocket redPocket = new RedPocket(10000, 10);
            List<String> pockets = new ArrayList<>();
            for (int j = redPocket.getRemainSize(); j > 0; j--) {
                pockets.add(getRandomMoney(redPocket));
            }
            System.out.printf("第%d次,生成的随机序列为:%s%n", i + 1, Arrays.toString(pockets.toArray()));
        }
    }
}
