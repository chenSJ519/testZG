package com.zg.mapper;

import com.zg.entity.Content;
import com.zg.entity.Program;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
@Repository("programMapper")
public interface ProgramMapper {
    List<Map<String,Object>> selectAllProgram();

    int saveOrUpdate(Program program);

    int saveContentList(List<Content> listContent);

    int delteContentList(Program listContent);

    List<Map<String,Object>> getContentByTaskid(String taskid);
}
