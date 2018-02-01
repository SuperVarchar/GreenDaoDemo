package com.hnshituo.icore_map.view.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.adapter.CustomBaseAdapter;
import com.hnshituo.icore_map.base.bean.MenuEvent;

import java.util.List;



/**

 */
public class Menu {
    private PopupWindow FilterPopWindow;
    private View contentView;
    Context mcontext;
    /**
     * -----------------------------------------------------------------------------------------------------------
     *    我的界面弹出pop
     * @param
     *
     */
    public Menu(Context context, List<MenuEvent>items, final onItemClick listener) {

        mcontext=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.item_meun, null);
        ListView filterlv = (ListView) contentView.findViewById(R.id.meun_item_lv);

        MeunAdapter adapter = new MeunAdapter(context);
        adapter.setData(items);
        filterlv.setAdapter(adapter);
        filterlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilterPopWindow.dismiss();
                listener.onItemClick(parent,view,position,id);
            }
        });
    }

    public void showFilterPopWindow(View anchor) {
        if (FilterPopWindow != null && FilterPopWindow.isShowing()) {
            FilterPopWindow.dismiss();
        }else {
            int width= (int) mcontext.getResources().getDimension(R.dimen.base200dp);
            FilterPopWindow = new PopupWindow(contentView,width,ActionBar.LayoutParams.WRAP_CONTENT);
            FilterPopWindow.setOutsideTouchable(false);
            FilterPopWindow.setTouchable(true);
            FilterPopWindow.setFocusable(true);
            FilterPopWindow.setBackgroundDrawable(new BitmapDrawable());
        }

        if (FilterPopWindow != null) {
           /* int screen_pos[] = new int[2];
            //Get Location of anchor view on screen
            anchor.getLocationOnScreen(screen_pos);

            //Call view measure to calculate how big your view should be.
            contentView.measure(ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT);
            int xPx= SizeUtils.convertDp2Px(mcontext,(int) mcontext.getResources().getDimension(R.dimen.base13dp));
            FilterPopWindow.showAtLocation(anchor,Gravity.RIGHT|Gravity.TOP,xPx/2,screen_pos[1]+anchor.getMeasuredHeight());*/

            FilterPopWindow.showAsDropDown(anchor);
        }
    }

    class MeunAdapter extends CustomBaseAdapter<MenuEvent> {

        public MeunAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_meun_title, parent, false);
                holder = new MenuHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder =(MenuHolder) convertView.getTag();
            }
            holder.meun_title.setText(getItemData(position).title);
//            holder.meun_title.setTextColor(getItemData(position).color);
            holder.meun_image.setImageResource(getItemData(position).drawable);
            return convertView;
        }
    }
    public interface onItemClick {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    class MenuHolder {
        ImageView meun_image;
        TextView meun_title;

        MenuHolder(View view) {
            meun_image = (ImageView)view.findViewById(R.id.meun_image);
            meun_title = (TextView)view.findViewById(R.id.meun_title);
        }
    }
}


