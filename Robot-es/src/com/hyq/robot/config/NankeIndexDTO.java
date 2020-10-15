package com.hyq.robot.config;

import io.searchbox.annotations.JestId;
import lombok.Data;

/**
 * @author nanke
 * @date 2020/10/16 2:06 上午
 * 致终于来到这里的勇敢的人:
 * 你是上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改我们这最棘手的代码的编程骑士。
 * 你是我们的救世主，我要对你说：
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Data
public class NankeIndexDTO {

    @JestId
    private String id;

    private Integer age;

    private String name;

    public NankeIndexDTO(String id, Integer age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
}
