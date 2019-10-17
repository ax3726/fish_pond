package com.gofishfarm.htkj.ui.main;

import android.os.Bundle;
//import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.presenter.main.AttentionPresenter;
import com.gofishfarm.htkj.ui.main.attention.AtentionPagerFragmentAdapter;
import com.gofishfarm.htkj.view.main.AttentionView;

import java.lang.reflect.Field;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public class AttentionFragment extends BaseFragment<AttentionView, AttentionPresenter> implements AttentionView {

    private TabLayout mTab;
    private ViewPager mViewPager;
    private View view_left;
    private View view_right;
    @Inject
    AttentionPresenter mAttentionPresenter;

    public static AttentionFragment newInstance() {
        return new AttentionFragment();
    }

    @Override
    public AttentionPresenter createPresenter() {
        return this.mAttentionPresenter;
    }

    @Override
    public AttentionView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attentation;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initLayout(view);
        intAdapter();
        initListener();
    }

    private void initListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setIndicator(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        for (int i = 0; i < mTab.getTabCount(); i++) {
            TabLayout.Tab tab = mTab.getTabAt(i);
            if (tab == null) return;
            //这里使用到反射，拿到Tab对象后获取Class
            Class c = tab.getClass();
            try {
                //Filed “字段、属性”的意思,c.getDeclaredField 获取私有属性。
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                //如果不这样会报如下错误
                // java.lang.IllegalAccessException:
                //Class com.test.accessible.Main
                //can not access
                //a member of class com.test.accessible.AccessibleTest
                //with modifiers "private"
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        //这里就可以根据业务需求处理点击事件了。
                        setIndicator(position);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setIndicator(int i) {
        if (i == 0) {
            view_left.setVisibility(View.VISIBLE);
            view_right.setVisibility(View.INVISIBLE);
        } else if (i == 1) {
            view_left.setVisibility(View.INVISIBLE);
            view_right.setVisibility(View.VISIBLE);
        }
    }

    private void intAdapter() {
        mViewPager.setAdapter(new AtentionPagerFragmentAdapter(getChildFragmentManager(), getString(R.string.follow), getString(R.string.fans)));
        mTab.setupWithViewPager(mViewPager);

    }

    private void initLayout(View view) {
        mToolbar.setVisibility(View.GONE);
        mImageButton.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(getString(R.string.billBoard));
        view_left = (View) view.findViewById(R.id.view_left);
        view_right = (View) view.findViewById(R.id.view_right);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        view_left.setVisibility(View.VISIBLE);
        view_right.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }
}
