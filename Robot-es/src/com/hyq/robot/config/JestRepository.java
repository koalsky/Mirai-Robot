package com.hyq.robot.config;

import freemarker.template.Configuration;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nanke
 * @date 2020/10/16 1:55 上午
 * 致终于来到这里的勇敢的人:
 * 你是上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改我们这最棘手的代码的编程骑士。
 * 你是我们的救世主，我要对你说：
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
public class JestRepository {

    @Resource
    private JestClient jestClient;
    @Resource(name = "ftlConfig")
    private Configuration freeMarkerCfg;

    /**
     * 创建索引,并建立映射
     * @param indexName
     */
    public void creatIndex(String indexName) {
        //创建新的索引（防止未删除的文档继续存在）
        try {
            jestClient.execute(new CreateIndex.Builder(indexName).build());
        } catch (Exception e) {
            System.out.println("创建索引失败！");
        }

        String jsonStr = JsonDataUtils.getJsonString("nanke-mapping");
        // 创建实体映射
        PutMapping putMapping = new PutMapping.Builder(indexName, "doc", jsonStr).build();
        try {
            jestClient.execute(putMapping);
        } catch (Exception e) {
            System.out.println("创建索引映射失败！");
        }
    }

    /**
     * 删除索引
     * @param indexName
     */
    public void deleteIndex(String indexName) {
        try {
            jestClient.execute(new DeleteIndex.Builder(indexName).build());
        } catch (Exception e) {
            System.out.println("删除索引失败！");
        }
    }

    /**
     * 删除文档
     * @param docId
     */
    public void deleteDoc(String indexName,String docId) {
        try {
            jestClient.execute(new Delete.Builder(docId).index(indexName).type("doc").build());
        } catch (Exception e) {
            System.out.println("删除文档失败！" + indexName + "-" + docId);
        }
    }

    /**
     * 批量插入文档
     * @param indexName
     * @param docList
     */
    public void addDoc(String indexName, List<NankeIndexDTO> docList) {
        try {
            Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType("doc");
            for (NankeIndexDTO doc : docList) {
                Index.Builder indexBuilder = new Index.Builder(doc);
                bulk.addAction(indexBuilder.build());
            }
            BulkResult result = jestClient.execute(bulk.build());
            if (!result.isSucceeded()) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            System.out.println("批量插入文档失败！");
        }
    }

    /**
     * 更新索引文档
     * @param indexName
     * @param docList
     */
    public void updateDoc(String indexName, List<NankeIndexDTO> docList) {

        for (NankeIndexDTO doc : docList) {
            String docJson = JsonDataUtils.obj2Json(doc, false);
            String script = "{\"doc\" : " + docJson + "}";

            Update.Builder updateBuilder = new Update.Builder(script).index(indexName).type("doc").id(doc.getId());
            try {
                jestClient.execute(updateBuilder.build());
            } catch (Exception e) {
                System.out.println("更新文档失败！");
            }
        }
    }

    /**
     * 条件查询索引
     * @param indexName
     * @param query
     * @return
     */
    public List<NankeIndexDTO> queryByCondition(String indexName, NankeIndexQuery query) {
        StringWriter writer = new StringWriter();
        try {
            freeMarkerCfg.getTemplate("queryByCondition" + ".ftl").process(query, writer);
            String queryStr = writer.toString();

            Search search = new Search.Builder(queryStr).addIndex(indexName).addType("doc").build();
            SearchResult searchResult = jestClient.execute(search);
            List<SearchResult.Hit<NankeIndexDTO, Void>> hits = searchResult.getHits(NankeIndexDTO.class);
            if (hits.isEmpty()) {
                return new ArrayList<>();
            }
            List<NankeIndexDTO> result = new ArrayList<>();
            for (SearchResult.Hit<NankeIndexDTO, Void> hit : hits) {
                result.add(hit.source);
            }
            return result;
        } catch (Exception e) {
            System.out.println("查询索引数据失败！" + e.getMessage());
        }
        return new ArrayList<>();
    }



}
