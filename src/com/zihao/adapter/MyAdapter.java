package com.zihao.adapter;

import java.util.List;

import com.zihao.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 自定义适配器
 * 
 * @author zihao
 * 
 */
public class MyAdapter extends BaseAdapter {

	private List<String> mTitleArray;// 标题数组
	private LayoutInflater inflater;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            // 上下文对象
	 * @param titleArray
	 *            // 标题数组
	 */
	public MyAdapter(Context context, List<String> titleArray) {
		this.mTitleArray = titleArray;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 获取Item总数
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mTitleArray != null) {
			return mTitleArray.size();
		} else {
			return 0;
		}
	}

	/**
	 * 获取一个Item对象
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (mTitleArray != null) {
			return mTitleArray.get(position);
		} else {
			return null;
		}
	}

	/**
	 * 获取指定item的ID
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_layout, null);
			holder.mTitleTv = (TextView) convertView
					.findViewById(R.id.title_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mTitleTv.setText(mTitleArray.get(position));
		return convertView;
	}
	
	private class ViewHolder {
		private TextView mTitleTv;
	}

}