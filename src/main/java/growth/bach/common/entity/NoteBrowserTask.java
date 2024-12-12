package growth.bach.common.entity;

import growth.bach.common.holder.ApplicationContextHolder;
import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.task.meta.BaseSuccessCondition;
import growth.bach.instance.engine.task.meta.condition.success.DefaultSuccessCondition;
import growth.bach.manager.NotificationManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 话题笔记浏览任务
 *
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:40
 */
@Slf4j
@Identity("TOPIC_NOTE_BROWSER")
public class NoteBrowserTask extends BaseOneStepTask implements SimpleDeliverPrizeTask {
    private final NotificationManager notificationManager = ApplicationContextHolder.getApplicationContext().getBean(NotificationManager.class);

    @Override
    public boolean deliverPrize() {
        BaseSuccessCondition successCondition = getTaskInstanceDTO().getTaskMeta().getTaskSuccessCondition();
        if (successCondition instanceof DefaultSuccessCondition){
            Integer quantity = ((DefaultSuccessCondition) successCondition).getPrizeQuantity();
            List<Pair<Long, Boolean>> result = deliverPrize(getTaskInstanceDTO(), quantity);
            if (result.stream().anyMatch(p -> p.getRight().equals(Boolean.FALSE))){
                // 错误信息 日志
                return false;
            }else {
                // 错误日志
                return false;
            }
        }
        return true;
    }
}
