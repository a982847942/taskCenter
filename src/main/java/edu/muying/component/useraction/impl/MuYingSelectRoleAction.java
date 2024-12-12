package edu.muying.component.useraction.impl;

import edu.common.exception.AssertUtils;
import edu.common.exception.CommonResultCodeEnum;
import edu.muying.component.useraction.ActionContext;
import edu.muying.component.useraction.Activity;
import edu.muying.component.useraction.MuYingConstans;
import edu.muying.component.useraction.UserAction;
import edu.muying.component.useraction.service.UserEntityService;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 母婴答题角色选择 用户行动点
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:19
 */
@Activity(activityId = MuYingConstans.ACTIVITY_ID)
public class MuYingSelectRoleAction implements UserAction {

    private TransactionTemplate transactionTemplate;

    /**
     * 用户选择的角色
     */
    private static final String PARAMS_KEY_SELECTED_ROLE = "selectedCode";

    private UserEntityService userEntityService;
    @Override
    public Object doAction(ActionContext actionContext) {
        String selectedRole = actionContext.getParamsMap().get(PARAMS_KEY_SELECTED_ROLE);
        AssertUtils.isNotBlank(selectedRole, CommonResultCodeEnum.PARAMETER_ILLEGAL, "传入的selectedRole为空");
        transactionTemplate.execute(action -> {
            // 加锁查询用户信息
            MuYingUserInfo userInfo = userEntityService.lock(actionContext.getActivityId(), actionContext.getUserId(), MuYingUserInfo.class);
            if (userInfo == null){
                // 插入
                userInfo = new MuYingUserInfo();
                userInfo.setSelectRole(selectedRole);
                userEntityService.insert(actionContext.getActivityId(), actionContext.getUserId(), userInfo);
            }else {
                userInfo.setSelectRole(selectedRole);
                userEntityService.update(actionContext.getActivityId(), actionContext.getUserId(), userInfo);
            }
            return null;
        });
        return new MuYingSelectRoleActionResponse();
    }


    private static class MuYingSelectRoleActionResponse{

    }


    private static class MuYingUserInfo{
        private String selectRole;

        public String getSelectRole() {
            return selectRole;
        }

        public void setSelectRole(String selectRole) {
            this.selectRole = selectRole;
        }

        @Override
        public String toString() {
            return "MuYingUserInfo{" +
                    "selectRole='" + selectRole + '\'' +
                    '}';
        }
    }
}
