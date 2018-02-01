package com.hnshituo.icore_map.base.listview.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.listview.bean.GroupSelectBean;
import com.hnshituo.icore_map.base.listview.search.CharacterParser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/1/19.
 */

public class GroupSelectAdapter<T> extends BaseAdapter {

    protected List<GroupSelectBean<T>> list;
    protected Context context;

    private String mMainFiledName = "";

    private String mSubFiledName = "";

    private String mExtraFiledName = "";

    private String image = "";

    private String imageUrl = "";

    private int normalImage = -1;

    public GroupSelectAdapter(Context context) {

        this.context = context;

    }

    public void setData(List<GroupSelectBean<T>> data) {
        this.list = new ArrayList<>();
        list.addAll(data);
    }

    public void setMainFiledName(String mainFiledName) {
        mMainFiledName = mainFiledName;
    }

    public void setExtraFiledName(String extraFiledName) {
        mExtraFiledName = extraFiledName;
    }

    public void setSubFiledName(String subFiledName) {
        mSubFiledName = subFiledName;
    }

    public void setShowFiledName(String mainFiledName, String subFiledName, String extraFiledName) {
        mMainFiledName = mainFiledName;
        mExtraFiledName = extraFiledName;
        mSubFiledName = subFiledName;


    }

    public void setShowImage(String img) {
        this.image = img;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNormalImage(int normalImage) {
        this.normalImage = normalImage;
    }

    public List<GroupSelectBean<T>> processData(List<T> data) {
        List<GroupSelectBean<T>> groupSelectBeen = new ArrayList<>();
        for (int i = 0 ; i<data.size();i++) {
            GroupSelectBean<T> bean = new GroupSelectBean<>();
            CharacterParser characterParser = new CharacterParser();
            if ((String) getFieldValueByName(mMainFiledName,data.get(i)) !=null) {
                String pinyin = characterParser.getSelling((String) getFieldValueByName(mMainFiledName,data.get(i)));
                if (pinyin != null&& pinyin.length() > 0) {
                    String sortString = pinyin.substring(0, 1).toUpperCase(
                            Locale.CHINESE);
                    if (sortString.matches("[A-Z]")) {
                        bean.sortLetters = sortString.toUpperCase(Locale.CHINESE);
                    } else {
                        bean.sortLetters = "#";
                    }
                } else {
                    bean.sortLetters = "#";
                }
            }else {
                bean.sortLetters = "#";
            }

            bean.data = data.get(i);
            groupSelectBeen.add(bean);
        }
        return groupSelectBeen;
    }

    private List<T> processData() {
        List<T> datas = new ArrayList<>();
        for (GroupSelectBean<T> item : list) {
            datas.add(item.data);
        }
        return datas;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GroupSelectBean<T> getItem(int position) {
        return list.get(position);
    }

    public GroupSelectBean<T> getItem(Object position) {
        int i;
        try {
            i = Integer.parseInt(String.valueOf(position));
        } catch (Exception e) {
            i = 0;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 添加数据
     *
     * @param list
     */
    public void addData(List<GroupSelectBean<T>> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 展现对话框
     **/
    public void showPhoneDialog(DialogInterface.OnClickListener click, String title, String[] str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(str, click);
        builder.create().show();
    }

    /**
     * 刷新列表数据
     *
     * @param list
     */
    public void replaceData(List<GroupSelectBean<T>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 获取列表数据
     */
    public List<T> getData() {
        return processData();
    }

    /**
     * 清除界面数据
     */
    public void clearData() {
        this.list.clear();
        notifyDataSetChanged();
    }


    private boolean selectMode;
    private GroupSelectAdapter.OnContactsSelect onContactsSelect;
    private int currentSelectPostion = -1;

    public interface OnContactsSelect<T> {
        void onIsSelectData(T data);

        void onNotIsSelectData(T data);
    }

    public void setOnContactsSelectListener(GroupSelectAdapter.OnContactsSelect onContactsSelect) {
        this.onContactsSelect = onContactsSelect;
    }

    public GroupSelectAdapter(Context context, boolean selectMode) {
        this(context);
        this.selectMode = selectMode;
    }

    @Override
    public void notifyDataSetChanged() {
        currentSelectPostion = -1;
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int finalPostion = position;
        GroupSelectAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_person_list, null);
            viewHolder = new GroupSelectAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupSelectAdapter.ViewHolder) convertView.getTag();
        }
        final GroupSelectBean info = getItem(position);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.catalog_ll.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(info.sortLetters);
        } else {
            viewHolder.catalog_ll.setVisibility(View.GONE);
        }
        //设置最右边显示的项
//        viewHolder.department.setText(mContent.departmentName);
        //设置分割线的显示与隐藏
        if ((position + 1) < getCount()) {
            int section1 = getSectionForPosition((position + 1));
            if ((position + 1) == getPositionForSection(section1)) {
                if (getPositionForSection(section1) > 0) {
                    viewHolder.itemline.setVisibility(View.GONE);
                } else {
                    viewHolder.itemline.setVisibility(View.VISIBLE);
                }
            } else {
                viewHolder.itemline.setVisibility(View.VISIBLE);
            }
        }
        if (position == getCount() - 1) {
            viewHolder.itemline.setVisibility(View.GONE);
        }


//        if (!selectMode) {
        viewHolder.cb_checked.setVisibility(View.VISIBLE);
        viewHolder.cb_checked.setChecked(info.isSelect);
        //如果是单选且有选中的
        if (selectMode && info.isSelect) {
            currentSelectPostion = finalPostion;
        }


        //设置数据
        viewHolder.tvTitle.setText((String) getFieldValueByName(mMainFiledName, (T) info.data));
        viewHolder.department.setText((String) getFieldValueByName(mExtraFiledName, (T) info.data));
        viewHolder.position.setText((String) getFieldValueByName(mSubFiledName, (T) info.data));
        if (!TextUtils.isEmpty(image) &&  getFieldValueByName(image, (T) info.data) !=null) {

            //Bitmap bitmap = BitmapBase64.stringtoBitmap((String) getFieldValueByName(image, (T) info.data));
//            Glide.with(context)..into(viewHolder.friend_icon);
            //viewHolder.friend_icon.setImageBitmap(bitmap);
        } else if (!TextUtils.isEmpty(imageUrl) && getFieldValueByName(imageUrl, (T) info.data) != null) {
            Glide.with(context).load(Constant.BASE_URL+getFieldValueByName(imageUrl, (T) info.data)).into(viewHolder.friend_icon);
        } else if (normalImage != -1) {
            Glide.with(context).load(normalImage).into(viewHolder.friend_icon);
        }
//        } else {
//            viewHolder.cb_checked.setVisibility(View.GONE);
//        }
//		viewHolder.friend_icon.setImageResource(R.drawable.set_icon);
//        viewHolder.tvTitle.setText(this.list.get(position).userTrueName);

        //设置显示的项


//        if (!TextUtils.isEmpty(this.list.get(position).position)) {
//            viewHolder.position.setVisibility(View.VISIBLE);
//            viewHolder.position .setText(this.list.get(position).position);
//        }else {
//            viewHolder.position.setVisibility(View.GONE);
//        }
//        if (TextUtils.isEmpty(mContent.userImg)) {
//            viewHolder.friend_icon.setImageResource(R.drawable.person_avatar);
//        } else {
//            /*imageLoader.displayImage(list.get(position).userImg,
//                    viewHolder.friend_icon, options);*/
//            viewHolder.friend_icon.setImageBitmap(BitmapBase64.stringtoBitmap(mContent.userImg));
//        }
//        if (!selectMode) {
//            return convertView;
//        }
        //convertView被选中,checkbox设置为被选中

        if (selectMode) { //单选
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb_checked = (CheckBox) v.findViewById(R.id.cb_checked);
                    if (currentSelectPostion == position) { //如果自己是选中的
                        cb_checked.setChecked(false);
                        info.isSelect = false;
                        currentSelectPostion = -1;
                        if (onContactsSelect != null) {
                            onContactsSelect.onNotIsSelectData(list.get(finalPostion).data);
//                            onContactsSelect.onNotIsSelectPerson(list.get(finalPostion));
                        }
                    } else if (currentSelectPostion == -1) { //如果没有选中的
                        cb_checked.setChecked(true);
                        info.isSelect = true;
                        currentSelectPostion = finalPostion;
                        if (onContactsSelect != null) {
                            onContactsSelect.onIsSelectData(list.get(finalPostion).data);
                        }
                    } else { //如果选中的是其他的
                        cb_checked.setChecked(true);
                        info.isSelect = true;
                        getItem(currentSelectPostion).isSelect = false;
                        if (onContactsSelect != null) {
                            onContactsSelect.onIsSelectData(list.get(finalPostion).data);
                        }
                        notifyDataSetChanged();
                        currentSelectPostion = finalPostion;
                    }
                }
            });
        } else {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb_checked = (CheckBox) v.findViewById(R.id.cb_checked);
                    //checkBox设置
                    cb_checked.setChecked(!info.isSelect);
                    info.isSelect = !info.isSelect;
                    if (onContactsSelect != null) {
                        if (info.isSelect) {
                            onContactsSelect.onIsSelectData(list.get(finalPostion).data);
                        } else {
                            onContactsSelect.onNotIsSelectData(list.get(finalPostion).data);
                        }

                    }
                }


            });
        }
        return convertView;
    }

    public void refreshData(List<GroupSelectBean<T>> datas) {
        list.clear();
        list.addAll(datas);
        notifyDataSetChanged();

    }

    final static class ViewHolder {
        ImageView friend_icon;
        TextView tvLetter;
        TextView position;
        TextView tvTitle;
        TextView department;
        CheckBox cb_checked;
        View itemline;
        LinearLayout catalog_ll;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvLetter = (TextView) view.findViewById(R.id.catalog);
            friend_icon = (ImageView) view.findViewById(R.id.friend_icon);
            cb_checked = (CheckBox) view.findViewById(R.id.cb_checked);
            itemline = view.findViewById(R.id.item_line);
            catalog_ll = (LinearLayout) view.findViewById(R.id.catalog_ll);
            position = (TextView) view.findViewById(R.id.position);
            department = (TextView) view.findViewById(R.id.department);
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).sortLetters.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @SuppressLint("DefaultLocale")
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase(Locale.CHINESE).charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取选中的对象
     *
     * @return
     */
    public ArrayList<GroupSelectBean<T>> getSelectUser() {
        ArrayList<GroupSelectBean<T>> PersonInfos = new ArrayList<GroupSelectBean<T>>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelect) {
                PersonInfos.add(list.get(i));
            }
        }
        return PersonInfos;
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

}
