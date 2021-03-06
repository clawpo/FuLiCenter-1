package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.CartFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.controller.fragment.PersonalCenterFragment;
import cn.ucai.fulicenter.view.MFGT;

import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity {
    int mIndex, mCurrentIndex;
    RadioButton[] mrbS;
    Fragment[] mFragments;

    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    CartFragment mCartFragment;
    PersonalCenterFragment mPersonalCenterFragment;

    @BindView(R.id.layout_new_good)
    RadioButton mLayoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton mLayoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton mLayoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton mLayoutCart;
    @BindView(R.id.layout_personal_center)
    RadioButton mLayoutPersonalCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mrbS = new RadioButton[5];
        mFragments = new Fragment[5];
        initView();
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mCartFragment = new CartFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[3] = mCartFragment;
        mFragments[4] = mPersonalCenterFragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, mNewGoodsFragment).commit();
    }

    private void initView() {
        mrbS[0] = mLayoutNewGood;
        mrbS[1] = mLayoutBoutique;
        mrbS[2] = mLayoutCategory;
        mrbS[3] = mLayoutCart;
        mrbS[4] = mLayoutPersonalCenter;

    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                mIndex = 0;
                break;
            case R.id.layout_boutique:
                mIndex = 1;
                break;
            case R.id.layout_category:
                mIndex = 2;
                break;
            case R.id.layout_cart:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this, I.REQUEST_CODE_LOGIN_FROM_CART);
                } else {
                    mIndex = 3;
                }
                break;
            case R.id.layout_personal_center:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    mIndex = 4;
                }
                break;
        }

        if (mIndex != mCurrentIndex) {
            setFragment();
            setRadioStatus();
        }
    }

    private void setFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(mFragments[mCurrentIndex]);
        if (!mFragments[mIndex].isAdded()) {
            ft.add(R.id.fragment_container, mFragments[mIndex]);
        }
        ft.show(mFragments[mIndex]).commitAllowingStateLoss();
    }

    private void setRadioStatus() {
        for (int i = 0; i < mrbS.length; i++) {
            if (mIndex != i) {
                mrbS[i].setChecked(false);
            } else {
                mrbS[i].setChecked(true);
            }
        }
        mCurrentIndex = mIndex;
    }

    @Override
    protected void onResume() {
        Log.i("main", "onResume,currentIndex=" + mCurrentIndex + ",index" + mIndex + ",user=" + FuLiCenterApplication.getUser());
        super.onResume();
        if (mIndex == 4 && FuLiCenterApplication.getUser() == null) {
            mIndex = 0;
        }
        setFragment();
        setRadioStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == I.REQUEST_CODE_LOGIN) {
                mIndex = 4;
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART) {
                mIndex = 3;
            }
            setFragment();
            setRadioStatus();
        }
    }
}
