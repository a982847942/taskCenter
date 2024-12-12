package edu.muying.component.useraction.impl;

import edu.muying.component.useraction.ActionContext;
import edu.muying.component.useraction.Activity;
import edu.muying.component.useraction.MuYingConstans;
import edu.muying.component.useraction.UserAction;

/**
 * 母婴答题首页  返回杂项信息
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:20
 */
@Activity(activityId = MuYingConstans.ACTIVITY_ID)
public class MuYingHomeAction implements UserAction {
    @Override
    public Object doAction(ActionContext actionContext) {
        // 1.查询userId对应信息
        // 2.查询活动对应的物料信息(Component.material)
        // 3. 设置响应结果
        return null;
    }
}
