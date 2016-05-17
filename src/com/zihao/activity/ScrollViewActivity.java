package com.zihao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.zihao.utils.ScreenUtil;

/**
 * 主界面
 * 
 * @author zihao
 * @details 因为有些手机是有虚拟按键的（在计算屏幕分辨率的时候，有些可以去除掉虚拟区域的区域->如三星，有些不行->如MX3），为了计算的准确性，
 *          各位可以设置Activity为Theme
 *          .NoTitleBar.Fullscreen填满屏幕(解决类似MX3这种在计算过程中把虚拟键盘算入屏幕高度的)。
 */
public class ScrollViewActivity extends Activity implements OnClickListener {

	private ScrollView scrollView;// scrollView数据列表
	private Button toTopBtn;// 返回顶部的按钮


	private int scrollY = 0;// 标记上次滑动位置

	private View contentView;

	private final String TAG="test";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll);

		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		scrollView = (ScrollView) findViewById(R.id.my_scrollView);
		if (contentView == null) {
			contentView = scrollView.getChildAt(0);
		}

		toTopBtn = (Button) findViewById(R.id.top_btn);
		toTopBtn.setOnClickListener(this);

		//http://blog.csdn.net/jiangwei0910410003/article/details/17024287
		/******************** 监听ScrollView滑动停止 *****************************/
		scrollView.setOnTouchListener(new OnTouchListener() {
			private int lastY = 0;
			private int touchEventId = -9983761;
			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					View scroller = (View) msg.obj;
					if (msg.what == touchEventId) {
						if (lastY == scroller.getScrollY()) {
							handleStop(scroller);
						} else {
							handler.sendMessageDelayed(handler.obtainMessage(
									touchEventId, scroller), 5);
							lastY = scroller.getScrollY();
						}
					}
				}
			};

			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					handler.sendMessageDelayed(
							handler.obtainMessage(touchEventId, v), 5);
				}
				return false;
			}

			/**
			 * ScrollView 停止
			 * 
			 * @param view
			 */
			private void handleStop(Object view) {
			
			    Log.i(TAG,"handleStop");
				ScrollView scroller = (ScrollView) view;
				scrollY = scroller.getScrollY();

				doOnBorderListener();
			}
		});
		/***********************************************************/

	}

	/**
	 * ScrollView 的顶部，底部判断：
	 * http://www.trinea.cn/android/on-bottom-load-more-scrollview-impl/
	 * 
	 * 其中getChildAt表示得到ScrollView的child View， 因为ScrollView只允许一个child
	 * view，所以contentView.getMeasuredHeight()表示得到子View的高度,
	 * getScrollY()表示得到y轴的滚动距离，getHeight()为scrollView的高度。
	 * 当getScrollY()达到最大时加上scrollView的高度就的就等于它内容的高度了啊~
	 * 
	 * @param pos
	 */
	private void doOnBorderListener() {
		Log.i(TAG,ScreenUtil.getScreenViewBottomHeight(scrollView) + "  "
				+ scrollView.getScrollY()+" "+ ScreenUtil
				.getScreenHeight(ScrollViewActivity.this));
		
		
		// 底部判断
		if (contentView != null
				&& contentView.getMeasuredHeight() <= scrollView.getScrollY()
						+ scrollView.getHeight()) {
			toTopBtn.setVisibility(View.VISIBLE);
			Log.i(TAG,"bottom");
		}
		// 顶部判断
		else if (scrollView.getScrollY() == 0) {
			
			Log.i(TAG,"top");
		}

		else if (scrollView.getScrollY() > 30) {
			toTopBtn.setVisibility(View.VISIBLE);
			Log.i(TAG,"test");
		}

	}

	/**
	 * 下面我们看一下这个函数: scrollView.fullScroll(ScrollView.FOCUS_DOWN);滚动到底部
	 * scrollView.fullScroll(ScrollView.FOCUS_UP);滚动到顶部
	 * 
	 * 
	 * 需要注意的是，该方法不能直接被调用 因为Android很多函数都是基于消息队列来同步，所以需要一部操作，
	 * addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快， 但是如果立即调用fullScroll，
	 * view可能还没有显示出来，所以会失败 应该通过handler在新线程中更新
	 * 
	 * http://blog.csdn.net/t12x3456/article/details/12799825
	 * http://www.tuicool.com/articles/zayIjq
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.top_btn :
				scrollView.post(new Runnable() {
					@Override
					public void run() {
						scrollView.fullScroll(ScrollView.FOCUS_UP);
					}
				});
				toTopBtn.setVisibility(View.GONE);
				break;
		}
	}

}