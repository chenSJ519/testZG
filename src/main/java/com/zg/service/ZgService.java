package com.zg.service;

import com.zg.entity.Content;
import com.zg.entity.Program;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public interface ZgService {
    int saveOrUpdate(Program program);

    int saveContentList(List<Content> listContent);

    int delteContentList(Program listContent);

    List<Map<String,Object>> getContentByTaskid(String taskid);
}
