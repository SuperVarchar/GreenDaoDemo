package com.hnshituo.icore_map.base.listview.search;

import java.lang.reflect.Method;
import java.util.Comparator;

import static com.hnshituo.icore_map.base.listview.search.DataProcessUtil.ASC;
import static com.hnshituo.icore_map.base.listview.search.DataProcessUtil.DESC;


/**
 * Created by Administrator on 2017/1/19.
 */

public class ClassComparator<T> implements Comparator<T> {
    private String sortFildName;
    private int orderBy;
    CharacterParser characterParser = CharacterParser.getInstance();

    public ClassComparator(String sortFildName, int orderBy) {
        this.sortFildName = sortFildName;
        this.orderBy = orderBy;
    }

    private Object getFieldValueByName(String fieldName, Object o) {
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

    @Override
    public int compare(T o1, T o2) {
        switch (orderBy) {
            case ASC:
                if ((String) getFieldValueByName(sortFildName, o1) != null && (String) getFieldValueByName(sortFildName, o2) != null) {
                    return characterParser.getSelling((String) getFieldValueByName(sortFildName, o1)).compareTo(characterParser.getSelling((String) getFieldValueByName(sortFildName, o2)));
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

