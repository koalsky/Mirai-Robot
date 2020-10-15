package com.hyq.robot.controller;

import com.hyq.robot.config.JestRepository;
import com.hyq.robot.config.NankeIndexDTO;
import com.hyq.robot.config.NankeIndexQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nanke
 * @date 2020/10/16 2:15 上午
 * 致终于来到这里的勇敢的人:
 * 你是上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改我们这最棘手的代码的编程骑士。
 * 你是我们的救世主，我要对你说：
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@RestController
public class EsController {

    private static String INDEX = "myselfindex";

    @Resource
    private JestRepository jestRepository;

    @RequestMapping("test")
    public boolean test () {
        // 创建一个索引 Mappping NankeIndexDTO
        jestRepository.creatIndex(INDEX);
        // 添加文档
        List<NankeIndexDTO> docList = new ArrayList<>();
        docList.add(new NankeIndexDTO("doc1",1,"文档1"));
        docList.add(new NankeIndexDTO("doc2",2,"文档2"));
        docList.add(new NankeIndexDTO("doc3",3,"文档3"));
        docList.add(new NankeIndexDTO("doc4",4,"文档4"));
        jestRepository.addDoc(INDEX,docList);
        // 查询文档
        List<NankeIndexDTO> selectOnde = jestRepository.queryByCondition(INDEX, new NankeIndexQuery("文档", null, null));
        // 修改文档
        List<NankeIndexDTO> updateDocList = new ArrayList<>();
        updateDocList.add(new NankeIndexDTO("doc1",5,"文档5"));
        jestRepository.updateDoc(INDEX,updateDocList);
        // 查询文档
        selectOnde = jestRepository.queryByCondition(INDEX, new NankeIndexQuery("文档", null, null));
        // 删除文档
        jestRepository.deleteDoc(INDEX,"doc1");
        // 查询文档
        selectOnde = jestRepository.queryByCondition(INDEX, new NankeIndexQuery("文档", null, null));
        // 删除索引
        jestRepository.deleteIndex(INDEX);
        return true;
    }




}
