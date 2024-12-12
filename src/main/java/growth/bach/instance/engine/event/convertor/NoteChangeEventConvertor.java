package growth.bach.instance.engine.event.convertor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.consumer.MessageWrapper;
import growth.bach.instance.engine.event.content.NoteChange;
import growth.bach.instance.engine.event.hub.UniqueEventConvertor;
import growth.bach.instance.enums.EventType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 笔记发布事件转换器
 * @author brain
 * @version 1.0
 * @date 2024/11/2 13:32
 */
public class NoteChangeEventConvertor implements UniqueEventConvertor<MessageWrapper, NoteChange> {
//    private IdMa

    @Override
    public boolean support(Object msg) {
        if (msg instanceof MessageWrapper){
            return EventType.NOTE_CHANGE.equals(((MessageWrapper) msg).getEventType());
        }
        return false;
    }

    @Override
    public UniqEvent<NoteChange> convert(MessageWrapper msg) {
        JSONObject jsonObject = JSON.parseObject(msg.getRawMsg().getBody());
        UniqEvent<NoteChange> event = new UniqEvent<>();
        event.setSource(msg.getSource().name());
        event.setEventType(msg.getEventType().name());
        NoteChange content = convertContent(jsonObject);
        event.setUserId(content.getUserOid());
        event.setContent(content);
        return event;
    }

    private NoteChange convertContent(JSONObject jsonObject) {
        NoteChange noteChange = new NoteChange();
        // 从内部服务中 找到 oid和uid（MongoID和MysqlID）
        Pair<String, Long> pair = Pair.of("1",1L);
        noteChange.setUserOid(pair.getLeft());
        noteChange.setUserId(pair.getRight());
        // 从内部服务中 找到 Noteoid和uid（MongoID和MysqlID）
        Pair<String, Long> noteIdPair = Pair.of("1",1L);
        noteChange.setNoteOid(noteIdPair.getLeft());
        noteChange.setNoteId(noteIdPair.getRight());
        noteChange.setQueryTagIdList(this::queryTopicIdList);
        return noteChange;
    }

    private List<String> queryTopicIdList(Long noteId, Long userId) {
        //从内服务获取NoteBaseInfo.getTagList()
        return Lists.newArrayList();
    }


}
