package com.zihao.activity;

import java.util.ArrayList;
import java.util.List;

import com.zihao.adapter.MyAdapter;
import com.zihao.utils.ScreenUtil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;

/**
 * 主界面
 * 
 * @author zihao
 * @details 因为有些手机是有虚拟按键的（在计算屏幕分辨率的时候，有些可以去除掉虚拟区域的区域->如三星，有些不行->如MX3），为了计算的准确性，
 *          各位可以设置Activity为Theme
 *          .NoTitleBar.Fullscreen填满屏幕(解决类似MX3这种在计算过程中把虚拟键盘算入屏幕高度的)。
 */
public class ListActivity extends Activity implements OnClickListener {

	private ListView listView;// List数据列表
	private Button toTopBtn;// 返回顶部的按钮
	private MyAdapter adapter;
	private boolean scrollFlag = false;// 标记是否滑动
	private int lastVisibleItemPosition = 0;// 标记上次滑动位置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		listView = (ListView) findViewById(R.id.my_listView);
		toTopBtn = (Button) findViewById(R.id.top_btn);

		adapter = new MyAdapter(this, getTitleDatas());
		listView.setAdapter(adapter);

		toTopBtn.setOnClickListener(this);
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
					scrollFlag = false;
					// 判断滚动到底部
					if (listView.getLastVisiblePosition() == (listView
							.getCount() - 1)) {
						toTopBtn.setVisibility(View.VISIBLE);
					}
					// 判断滚动到顶部
					if (listView.getFirstVisiblePosition() == 0) {
						toTopBtn.setVisibility(View.GONE);
					}

					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
					scrollFlag = true;
					break;
				case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
					scrollFlag = false;
					break;
				}
			}

			/**
			 * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
			 * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
				if (scrollFlag
						&& ScreenUtil.getScreenViewBottomHeight(listView) >= ScreenUtil
								.getScreenHeight(ListActivity.this)) {
					if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
						toTopBtn.setVisibility(View.VISIBLE);
					} else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
						toTopBtn.setVisibility(View.GONE);
					} else {
						return;
					}
					lastVisibleItemPosition = firstVisibleItem;
				}
			}
		});
	}

	/**
	 * 获取标题数据列表
	 * 
	 * @return
	 */
	private List<String> getTitleDatas() {
		List<String> titleArray = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			titleArray.add("这是第" + i + "个item");
		}
		return titleArray;
	}

	/**
	 * 滚动ListView到指定位置
	 * 
	 * @param pos
	 */
	private void setListViewPos(int pos) {
		if (android.os.Build.VERSION.SDK_INT >= 8) {
			listView.smoothScrollToPosition(pos);
		} else {
			listView.setSelection(pos);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_btn:// 点击按钮返回到ListView的第一项
			setListViewPos(0);
			break;
		}
	}

}