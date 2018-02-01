package com.hnshituo.icore_map.view.treeview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangke on 2017-1-14.
 */
public class Node implements Parcelable {

    /**
     * 设置开启 关闭的图片
     */
    public int iconExpand = -1, iconNoExpand = -1;

    private String ID;
    /**
     * 根节点pId为0
     */
    private String PID;

    private String NAME;

    /**
     * 当前的级别
     */
    private int level;

    /**
     * 是否展开
     */
    private boolean isExpand = false;

    private int icon = -1;

    /**
     * 下一级的子Node
     */
    private List<Node> children = new ArrayList<>();

    /**
     * 父Node
     */
    private Node parent;
    /**
     * 是否被checked选中
     */
    private boolean isChecked;
    /**
     * 是否为新添加的
     */
    public boolean isNewAdd = true;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Node() {}

    public Node(String id, String pId, String name) {
        super();
        this.ID = id;
        this.PID = pId;
        this.NAME = name;
    }


    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String id)
    {
        this.ID = id;
    }

    public String getPID()
    {
        return PID;
    }

    public void setPID(String pId)
    {
        this.PID = pId;
    }

    public String getNAME()
    {
        return NAME;
    }

    public void setNAME(String name)
    {
        this.NAME = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 是否为跟节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel() {

        return parent == null ? 0 : parent.getLevel() + 1;
    }

    /**
     * 设置展开
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {

            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.PID);
        dest.writeString(this.NAME);
        dest.writeInt(this.level);
        dest.writeByte(this.isExpand ? (byte) 1 : (byte) 0);
        dest.writeList(this.children);
        dest.writeParcelable(this.parent, flags);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNewAdd ? (byte) 1 : (byte) 0);
    }

    protected Node(Parcel in) {
        this.ID = in.readString();
        this.PID = in.readString();
        this.NAME = in.readString();
        this.level = in.readInt();
        this.isExpand = in.readByte() != 0;
        this.children = new ArrayList<>();
        in.readList(this.children, Node.class.getClassLoader());
        this.parent = in.readParcelable(Node.class.getClassLoader());
        this.isChecked = in.readByte() != 0;
        this.isNewAdd = in.readByte() != 0;
    }

    public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel source) {
            return new Node(source);
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };
}
