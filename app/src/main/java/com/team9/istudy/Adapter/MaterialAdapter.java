package com.team9.istudy.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team9.istudy.Model.Material;
import com.team9.istudy.R;


public class MaterialAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<String> mGroupStrings = new ArrayList<>();
    private List<List<Material>> mData = null;

    public MaterialAdapter(Context ctx, List<List<Material>> list) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //mGroupStrings = mContext.getResources().getStringArray(R.array.groups);
        mData = list;
    }
    public List<String> getmGroupStrings() {
        return mGroupStrings;
    }

    public void setmGroupStrings(List<String> mGroupStrings) {
        this.mGroupStrings = mGroupStrings;
    }
    public void setData(List<List<Material>> list) {
        mData = list;
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return mData.get(groupPosition).size();
    }

    @Override
    public List<Material> getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return mData.get(groupPosition);
    }

    @Override
    public Material getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return mData.get(groupPosition).get(childPosition);//获得子结点的位置
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_material_search, null);
        }
        GroupViewHolder holder = new GroupViewHolder();
        holder.mGroupIcon=(ImageView)convertView.findViewById(R.id.course_image);
        holder.mGroupName = (TextView) convertView
                .findViewById(R.id.group_name);
        holder.mGroupName.setText(mGroupStrings.get(groupPosition));
        holder.mGroupCount = (TextView) convertView
                .findViewById(R.id.group_count);
        holder.mGroupCount.setText("(" + mData.get(groupPosition).size() + ")");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.materialsearch_cardview, null);
        }
        ChildViewHolder holder = new ChildViewHolder();
        holder.mIcon = (ImageView) convertView.findViewById(R.id.material_img);
        /*holder.mIcon.setBackgroundDrawable(getRoundCornerDrawable(
                getChild(groupPosition, childPosition).getImageId(), 10));*/
        holder.mMaterialName = (TextView) convertView.findViewById(R.id.material_name);
        holder.mMaterialName.setText(getChild(groupPosition, childPosition)
                .getmName());
        holder.mTearcher = (TextView) convertView.findViewById(R.id.material_teacher);
        holder.mTearcher.setText(getChild(groupPosition, childPosition)
                .getmTeacher());
        holder.mStartTime=(TextView)convertView.findViewById(R.id.material_starttime);
        holder.mStartTime.setText(getChild(groupPosition,childPosition).getmStartTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        /* 很重要：实现ChildView点击事件，必须返回true */
        return true;
    }

    private  Drawable getRoundCornerDrawable(int resId, float roundPX /* 圆角的半径 */) {
        Drawable drawable = mContext.getResources().getDrawable(resId);
        int w = mContext.getResources().getDimensionPixelSize(R.dimen.image_width);
        int h = w;

        Bitmap bitmap = Bitmap
                .createBitmap(w,h,
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap retBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas can = new Canvas(retBmp);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        paint.setColor(color);
        paint.setAntiAlias(true);
        can.drawARGB(0, 0, 0, 0);
        can.drawRoundRect(rectF, roundPX, roundPX, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        can.drawBitmap(bitmap, rect, rect, paint);
        return new BitmapDrawable(retBmp);
    }

    private class GroupViewHolder {
        TextView mGroupName;
        TextView mGroupCount;
        ImageView mGroupIcon;
    }

    private class ChildViewHolder {
        ImageView mIcon;
        TextView mMaterialName;
        TextView mTearcher;
        TextView mStartTime;

    }


}
