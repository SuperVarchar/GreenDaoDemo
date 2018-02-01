package com.hnshituo.greendaodemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hnshituo.icore_map.base.activity.ICoreBaseActivity;
import com.hnshituo.icore_map.okhttp.callback.BaseCallBack;
import com.hnshituo.icore_map.okhttp.callback.GsonCallback;
import com.hnshituo.icore_map.okhttp.callback.RequestCallFactory;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends ICoreBaseActivity implements View.OnClickListener {


    /**
     * 插入数据
     */
    private Button mInsert;
    /**
     * 插入集合数据
     */
    private Button mInserts;
    /**
     * Hello World!
     */
    private TextView mText;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<User> users = (List<User>) msg.obj;
            UserDao userDao = App.getInstances().getDaoSession().getUserDao();
            long startTime = new Date().getTime();
            Log.e("数据库操作....","开始"+startTime);
            userDao.deleteAll();
            long deleteTime = new Date().getTime();
            Log.e("数据库操作....","删除"+(deleteTime-startTime));
            userDao.insertInTx(users);
            long insertTime = new Date().getTime();
            Log.e("数据库操作....","插入"+(insertTime-deleteTime));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }
    String URL = "http://218.87.96.132:8080/xinsteel.oa/service/core/auth/user/userInfoApp";
    private void initData() {
        RequestCallFactory.getHttpPost(URL,null,null,this)
                .execute(new GsonCallback<List<User>>(this, BaseCallBack.DIALOG) {

                    @Override
                    public void onResponse(List<User> response) throws Exception {
                        if (response != null) {
                           Message message = new Message();
                           message.obj = response;
                            mHandler.sendMessage(message);
                        }

                    }
                });

    }

    private void initView() {
        mInsert = (Button) findViewById(R.id.insert);
        mInsert.setOnClickListener(this);
        mInserts = (Button) findViewById(R.id.inserts);
        mInserts.setOnClickListener(this);
        mText = (TextView) findViewById(R.id.text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                insert();
                break;
            case R.id.inserts:
                inserts();
                break;
        }
    }

    private void inserts() {
//        List<User> users = new ArrayList<>();
//
//        User user = new User();
//        user.setAge(20);
//        user.setName("li");
//        user.setSalary(10);
//        user.setSex("男");
//        user.setAa(10);
//
//        User user1 = new User();
//        user1.setAge(30);
//        user1.setName("王");
//        user1.setSalary(10);
//        user1.setSex("男");
//        user1.setAa(10);
//        users.add(user1);
//        users.add(user);
////        DBManager.getInstance(getApplication()).insertUserList(users);
//
//        UserDao userDao = App.getInstances().getDaoSession().getUserDao();
//        userDao.insertInTx(users);
        showInfo();
    }

    private void insert() {
//        User user = new User();
//        user.setAge(10);
//        user.setName("zhangsan");
//        user.setSalary(10);
//        user.setSex("男");
//        user.setAa(10);
//        UserDao userDao = App.getInstances().getDaoSession().getUserDao();
//        userDao.insertInTx(user);
        initData();
    }

    private void showInfo() {
//        List<User> users = DBManager.getInstance(getApplication()).queryUserList();
        QueryBuilder<User> userQueryBuilder = App.getInstances().getDaoSession().getUserDao().queryBuilder();
        List<User> users = userQueryBuilder.list();
        Log.e("数据库操作","查询完成..........");
        String  infos = "";
        for (User user : users) {
            infos += user.toString()+"/n";
        }
        mText.setText(infos);
    }

    @Override
    public void showSuccess(Call call) {

    }

    @Override
    public void reTry() {

    }
}
