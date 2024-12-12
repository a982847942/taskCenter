package edu.nuaa.redpocket;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/6 16:26
 */
public class Test {
    static class RedPocket {
        private Integer totalMoney;
        private Integer remainSize;

        public RedPocket() {
        }

        public RedPocket(Integer totalMoney, Integer remainSize) {
            this.totalMoney = totalMoney;
            this.remainSize = remainSize;
        }

        public Integer getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(Integer totalMoney) {
            this.totalMoney = totalMoney;
        }

        public Integer getRemainSize() {
            return remainSize;
        }

        public void setRemainSize(Integer remainSize) {
            this.remainSize = remainSize;
        }

        @Override
        public String toString() {
            return "RedPocket{" +
                    "totalMoney=" + totalMoney +
                    ", remainSize=" + remainSize +
                    '}';
        }
    }

    public static String getMoney(RedPocket redPocket) {
        Integer curTotalMoney = redPocket.getTotalMoney();
        Integer curRemainSize = redPocket.getRemainSize();
        if (curRemainSize <= 1) {
            redPocket.setRemainSize(0);
            return String.format("%.2f", curTotalMoney / 100.0);
        }
        //(0.01, 2* m / n);
        Integer max = (curTotalMoney / curRemainSize) * 2;
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(max) + 1;
        i = Math.max(1, i);
        redPocket.setTotalMoney(curTotalMoney - i);
        redPocket.setRemainSize(curRemainSize - 1);
        return String.format("%.2f", i / 100.0);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            RedPocket redPocket = new RedPocket(10000, 10);
            List<String> pockets = new ArrayList<>();
            for (int j = redPocket.getRemainSize(); j > 0; j--) {
                pockets.add(getMoney(redPocket));
            }
            System.out.printf("第%d次,生成的随机序列为:%s%n", i + 1, Arrays.toString(pockets.toArray()));
        }
    }
}
