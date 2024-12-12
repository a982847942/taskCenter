package growth.bach.common.constant;

import org.apache.commons.lang3.tuple.Pair;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 时间阶段
 * @author brain
 * @version 1.0
 * @date 2024/11/1 11:11
 */
public enum Period {
    /**
     * 每日的
     */
    DAILY(){
        @Override
        public Pair<LocalDateTime, LocalDateTime> getDuration() {
            LocalDate cuurentDate = LocalDate.now();
            // 获取当前时间所在的0点
            LocalDateTime startOfDay = cuurentDate.atStartOfDay();

            // 获取当前时间所在日的24点
            LocalDateTime endOfDay = cuurentDate.atStartOfDay().plusDays(1).minusNanos(1);
            return Pair.of(startOfDay, endOfDay);
        }
    },
    /**
     * 每周的
     */
    WEEKLY(){
        @Override
        public Pair<LocalDateTime, LocalDateTime> getDuration() {
            LocalDate currentDate = LocalDate.now();

            /**
             * 获取当前时间所在的周一
             */
            LocalDate monday = currentDate.with(DayOfWeek.MONDAY);
            LocalDateTime mondayDateTime = monday.atStartOfDay();

            //获取当前时间所在周的周日
            LocalDate sunday = currentDate.with(DayOfWeek.SUNDAY);
            LocalDateTime sundayDateTime = sunday.plusDays(1).atStartOfDay().minusNanos(1);
            return Pair.of(mondayDateTime, sundayDateTime);
        }
    },
    /**
     * 每月的
     */
    MONTHLY{
        @Override
        public Pair<LocalDateTime, LocalDateTime> getDuration() {
            LocalDate currentDate = LocalDate.now();

            // 获取当前时间所在月的第一天的0点
            LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
            LocalDateTime startOfMonth = firstDayOfMonth.atStartOfDay();

            // 获取当前时间所在月的最后一天的24点
            LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);
            LocalDateTime endOfMonth = lastDayOfMonth.atStartOfDay().plusDays(1).minusNanos(1);
            return Pair.of(startOfMonth, endOfMonth);
        }
    },
    /**
     * 整个活动期间
     */
    ACTIVITY
    ;

    /**
     * 获取当前时间段的起止时间
     * 如“每日的”，则返回当天的0-24点
     * @return 起止时间
     */
    public Pair<LocalDateTime, LocalDateTime> getDuration(){
        return null;
    }
}
