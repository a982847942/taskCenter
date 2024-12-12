package growth.bach.instance.engine.event.content;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;

/**
 * 笔记发布DTO
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:07
 */
@Data
public class NoteChange {
    private Long noteId;
    private String noteOid;
    private Long userId;
    private String userOid;
    @Getter(AccessLevel.NONE)
    private BiFunction<Long, Long, List<String>> queryTagIdList;
    /**
     * 话题/tag id list
     */
    @Getter(lazy = true)
    private final List<String> tagIdList = queryTagIdList.apply(noteId, userId);
}
