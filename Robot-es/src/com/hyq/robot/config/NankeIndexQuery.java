package com.hyq.robot.config;

import lombok.Data;

/**
 * @author nanke
 * @date 2020/10/16 2:24 上午
 * 致终于来到这里的勇敢的人:
 * 你是上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改我们这最棘手的代码的编程骑士。
 * 你是我们的救世主，我要对你说：
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Data
public class NankeIndexQuery {

    /**
     * 起始
     */
    private Integer start;
    /**
     * 分页数量
     */
    private Integer pageSize;
    /**
     * 关键词
     */
    private String keyWord;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 姓名
     */
    private String name;

    public NankeIndexQuery(String keyWord, Integer age, String name) {
        this.start = 0;
        this.pageSize = 500;
        this.keyWord = keyWord;
        this.age = age;
        this.name = name;
    }
}
