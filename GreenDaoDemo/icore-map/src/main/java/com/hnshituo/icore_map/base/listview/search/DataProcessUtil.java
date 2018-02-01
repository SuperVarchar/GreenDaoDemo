package com.hnshituo.icore_map.base.listview.search;

import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/1/16.
 */

public class DataProcessUtil<T> {
    //倒叙
    public final static int DESC = -1;
    //顺序
    public final static int ASC = 1;


    /**
     * 单一字段单一条件数据筛选
     *
     * @param filterName 筛选字段名
     * @param filterStr  筛选字符串
     */
    public List<T> filterData(List<T> originalInfos, String filterName, String filterStr) {
        List<T> processedInfos = new ArrayList<>();
        CharacterParser characterParser = CharacterParser.getInstance();
        if (originalInfos == null) {
            return null;
        }
        if (TextUtils.isEmpty(filterStr)) {
            return originalInfos;
        } else {
            processedInfos.clear();
            for (T info : originalInfos) {
                String name = (String) getFieldValueByName(filterName, info);
                if (name != null) {
                    if (name.contains(filterStr)
                            || characterParser.getSelling(name).contains(
                            filterStr)) {
                        processedInfos.add(info);
                    }
                }
            }
            return processedInfos;
        }
    }

    /**
     * 多字段单一条件数据筛选
     *
     * @param filterNames 字段的数组
     * @param filterStr   筛选条件字符串
     */

    public List<T> filterData(List<T> originalInfos, String[] filterNames, String filterStr) {
        CharacterParser characterParser = CharacterParser.getInstance();
        List<T> processedInfos = new ArrayList<>();
        if (originalInfos == null) {
            return null;
        }
        if (TextUtils.isEmpty(filterStr)) {
            return originalInfos;
        } else {
            processedInfos.clear();
            for (T info : originalInfos) {
                String name = "";
                for (int i = 0; i < filterNames.length; i++) {
                    name += (String) getFieldValueByName(filterNames[i], info) + ",";
                }
                if (name != null) {
                    if (name.contains(filterStr)
                            || characterParser.getSelling(name).contains(
                            filterStr)) {
                        processedInfos.add(info);
                    }
                }
            }
            return processedInfos;
        }

    }

    /**
     * 多字段多条件数据筛选(需要满足所有条件)
     *
     * @param condition 筛选字段名(key)和条件字符串(value)的MAP集合
     */

    public List<T> filterData(List<T> originalInfos, Map<String, String> condition) {
        List<T> processedInfos = new ArrayList<>();
        CharacterParser characterParser = CharacterParser.getInstance();
        if (originalInfos == null) {
            return null;
        }
        if (condition == null) {
            return originalInfos;
        }
        List<T> filterInfo = new ArrayList<>();
        filterInfo.addAll(originalInfos);
        Set<String> keys = condition.keySet();
        for (String key : keys) {
            String value = condition.get(key);
            processedInfos.clear();
            for (T info : filterInfo) {
                String name = (String) getFieldValueByName(key, info);
                if (name != null) {
                    if (name.contains(value)
                            || characterParser.getSelling(name).contains(
                            value)) {
                        processedInfos.add(info);
                    }
                }

            }
            filterInfo = processedInfos;
        }
        return processedInfos;
    }


    /**
     * 使用反射根据属性名称获取属性值
     *
     * @param fieldName 属性名称
     * @param o         操作对象
     * @return Object 属性值
     */

    private Object getFieldValueByName(String fieldName, T o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public List<T> dataSort(List<T> originalInfos, String sortFildName, int orderBy) {
        PinyinComparator comparator;
        if (originalInfos == null) {
            return null;
        }
        switch (orderBy) {
            case DESC: //倒叙
                comparator = new PinyinComparator(sortFildName, DESC);
                break;
            case ASC: //顺序
                comparator = new PinyinComparator(sortFildName, ASC);
                break;
            default: //默认
                comparator = new PinyinComparator(sortFildName, ASC);
                break;
        }
        Collections.sort(originalInfos, comparator);
        return originalInfos;
    }

    /**
     * 拼音比较器
     */
    class PinyinComparator implements Comparator<T> {
        private String sortFildName;
        private int orderBy;
        CharacterParser characterParser = CharacterParser.getInstance();
        public PinyinComparator(String sortFildName, int orderBy) {
            this.sortFildName = sortFildName;
            this.orderBy = orderBy;
        }

        @Override
        public int compare(T o1, T o2) {
            switch (orderBy) {
                case ASC:
                    if ((String) getFieldValueByName(sortFildName, o1) != null && (String) getFieldValueByName(sortFildName, o2) != null) {
                        return  characterParser.getSelling((String) getFieldValueByName(sortFildName, o1)).compareTo(characterParser.getSelling((String) getFieldValueByName(sortFildName, o2)));
                    }
                    break;
                case DESC:
                    if ((String) getFieldValueByName(sortFildName, o1) != null && (String) getFieldValueByName(sortFildName, o2) != null) {
                        return characterParser.getSelling((String) getFieldValueByName(sortFildName, o2)).compareTo(characterParser.getSelling((String) getFieldValueByName(sortFildName, o1)));
                    }
                    break;
            }
            return 0;
        }
    }

}
