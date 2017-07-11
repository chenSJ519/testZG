package com.zg.service.serviceImpl;

import com.zg.entity.Content;
import com.zg.entity.Program;
import com.zg.mapper.ProgramMapper;
import com.zg.service.ZgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
@Service("zgService")
public class ZgServiceImpl implements ZgService {
    @Autowired
    private ProgramMapper programMapper;

    @Override
    public int saveOrUpdate(Program program) {
        return programMapper.saveOrUpdate(program);
    }

    public int saveContentList(List<Content> listContent) {
        return programMapper.saveContentList(listContent);
    }

    @Override
    public int delteContentList(Program listContent) {
        return programMapper.delteContentList(listContent);
    }

    @Override
    public List<Map<String, Object>> getContentByTaskid(String taskid) {
        return programMapper.getContentByTaskid(taskid);
    }
}
