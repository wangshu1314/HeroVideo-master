package com.github.bigexcalibur.herovideo.model.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.bigexcalibur.herovideo.R;
import com.github.bigexcalibur.herovideo.base.RxBaseActivity;
import com.github.bigexcalibur.herovideo.model.home.HomePageFragment;
import com.github.bigexcalibur.herovideo.ui.widget.CircleImageView;
import com.github.bigexcalibur.herovideo.ui.widget.ThemePickDialog;
import com.github.bigexcalibur.herovideo.util.ConstantUtil;
import com.github.bigexcalibur.herovideo.util.LogUtil;
import com.github.bigexcalibur.herovideo.util.PreferenceUtil;
import com.github.bigexcalibur.herovideo.util.ToastUtil;

import butterknife.BindView;


public class MainActivity extends RxBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private Fragment[] fragments;
    private HomePageFragment mHomePageFragment;

    private int currentTabIndex;

    private int index;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //初始化Fragment
        initFragments();
        //初始化侧滑菜单
        initNavigationView();
    }

    @Override
    public void initToolbar() {

    }

    /**
     * 初始化Fragments
     */
    private void initFragments()
    {

        mHomePageFragment = HomePageFragment.newInstance();
//        IFavoritesFragment mFavoritesFragment = IFavoritesFragment.newInstance();
//        HistoryFragment mHistoryFragment = HistoryFragment.newInstance();
//        AttentionPeopleFragment mAttentionPeopleFragment = AttentionPeopleFragment.newInstance();
//        ConsumeHistoryFragment mConsumeHistoryFragment = ConsumeHistoryFragment.newInstance();
//        SettingFragment mSettingFragment = SettingFragment.newInstance();

//        fragments = new Fragment[]{
//                mHomePageFragment,
//                mFavoritesFragment,
//                mHistoryFragment,
//                mAttentionPeopleFragment,
//                mConsumeHistoryFragment,
//                mSettingFragment
//        };
        fragments = new Fragment[]{mHomePageFragment};

        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomePageFragment)
                .show(mHomePageFragment).commit();
    }



    /**
     * 初始化NavigationView
     */
    private void initNavigationView()
    {

        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);
        //设置头像
        mUserAvatarView.setImageResource(R.drawable.ic_launcher);
        //设置用户名 签名
        mUserName.setText(getResources().getText(R.string.test_username));
        mUserSign.setText(getResources().getText(R.string.test_username));
        //设置日夜间模式切换
        mSwitchMode.setOnClickListener(v -> switchNightMode());


        boolean flag = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (flag)
        {
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
        } else
        {
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
        }
    }


    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer()
    {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }


    /**
     * 日夜间模式切换
     */
    private void switchNightMode()
    {

        boolean isNight = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (isNight)
        {
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        } else
        {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, true);
        }
        LogUtil.d(TAG," isNight = " +isNight);
        recreate();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {

        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
//            case R.id.item_home:
//                // 主页
////                changeFragmentIndex(item, 0);
//                return true;
//
//            case R.id.item_download:
//                // 离线缓存
////                startActivity(new Intent(MainActivity.this, OffLineDownloadActivity.class));
//                return true;
//
//            case R.id.item_vip:
//                //大会员
////                startActivity(new Intent(MainActivity.this, VipActivity.class));
//                return true;
//
//            case R.id.item_favourite:
//                // 我的收藏
////                changeFragmentIndex(item, 1);
//                return true;
//
//            case R.id.item_history:
//                // 历史记录
////                changeFragmentIndex(item, 2);
//                return true;
//
//            case R.id.item_group:
//                // 关注的人
////                changeFragmentIndex(item, 3);
//                return true;
//
//            case R.id.item_tracker:
//                // 我的钱包
////                changeFragmentIndex(item, 4);
//                return true;
//
            case R.id.item_theme:
                // 主题选择
                ThemePickDialog dialog = new ThemePickDialog();
                dialog.setClickListener(this);
                dialog.show(getSupportFragmentManager(), ThemePickDialog.TAG);
                return true;
//
//            case R.id.item_app:
//                // 应用推荐
//
//                return true;
//
//            case R.id.item_settings:
//                // 设置中心
////                changeFragmentIndex(item, 5);
//                return true;
        }

        return false;
    }


    /**
     * 监听back键处理DrawerLayout和SearchView
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1)))
            {
                mDrawerLayout.closeDrawers();
            } else
            {
                if (mHomePageFragment != null)
                {
                    if (mHomePageFragment.isOpenSearchView())
                    {
                        mHomePageFragment.closeSearchView();
                    } else
                    {
                        exitApp();
                    }
                } else
                {
                    exitApp();
                }
            }
        }

        return true;
    }


    /**
     * 双击退出App
     */
    private long exitTime;

    private void exitApp()
    {

        if (System.currentTimeMillis() - exitTime > 2000)
        {
            ToastUtil.ShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else
        {
//            PreferenceUtil.remove(ConstantUtil.SWITCH_MODE_KEY);
            finish();
        }
    }
}
