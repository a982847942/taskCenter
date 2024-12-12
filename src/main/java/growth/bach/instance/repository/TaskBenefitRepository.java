package growth.bach.instance.repository;

import com.google.common.collect.Lists;
import growth.bach.instance.entity.TaskBenefitDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 18:52
 */
@Slf4j
@Repository
public class TaskBenefitRepository {


//    @Resource
//    private TaskBenefitMapper taskBenefitMapper;

    public void insert(long taskMetaId, long benefitId, String sceneKey, String customizeKey) {
        TaskBenefitDO it = new TaskBenefitDO();
        it.setBenefitId(benefitId);
        it.setTaskMetaId(taskMetaId);
        it.setCustomizeKey(customizeKey);
        it.setSceneKey(sceneKey);
//        taskBenefitMapper.insert(it);
    }

    public TaskBenefitDO query(long taskMetaId, long benefitId, String sceneKey, String customizeKey) {
//        QueryWrapper<TaskBenefitDO> qw = new QueryWrapper<>();
//        qw.eq("task_meta_id", taskMetaId)
//                .eq("benefit_id", benefitId)
//                .eq("delete_flag", false);
//        if (!StringUtils.isEmpty(sceneKey)) {
//            qw.eq("scene_key", sceneKey);
//        }
//
//        if (!StringUtils.isEmpty(customizeKey)) {
//            qw.eq("customize_key", customizeKey);
//        }
//
//        return taskBenefitMapper.selectOne(qw);
        return new TaskBenefitDO();
    }

    public List<TaskBenefitDO> batchQuery(long taskMetaId) {
//        QueryWrapper<TaskBenefitDO> qw = new QueryWrapper<>();
//        qw.eq("task_meta_id", taskMetaId)
//                .eq("delete_flag", false);
//
//        return taskBenefitMapper.selectList(qw);
        return Lists.newArrayList();
    }
}
