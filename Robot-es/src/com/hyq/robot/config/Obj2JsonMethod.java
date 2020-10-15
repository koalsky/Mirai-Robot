package com.hyq.robot.config;

import freemarker.ext.beans.StringModel;
import freemarker.template.*;

import java.util.List;

/**
 * 对象转Json字符串函数
 */
public class Obj2JsonMethod implements TemplateMethodModelEx {
    @Override
    public TemplateModel exec(List args) throws TemplateModelException {
        if (args.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String json = "";
        if(args.get(0) instanceof DefaultListAdapter){
            DefaultListAdapter defaultListAdapter = (DefaultListAdapter)args.get(0);
            json = JsonDataUtils.obj2json(defaultListAdapter.getWrappedObject());
        } else if(args.get(0) instanceof StringModel){
            StringModel stringModel = (StringModel)args.get(0);
            Object obj = stringModel.getWrappedObject();
            json = JsonDataUtils.obj2json(obj);
        }
        
        return new SimpleScalar(json);
    }
}