package com.gofishfarm.htkj.module;

import android.app.Activity;

import com.gofishfarm.htkj.module.scope.FragmentScope;
import com.gofishfarm.htkj.ui.login.BindingFragment;
import com.gofishfarm.htkj.ui.login.LoginFragment;
import com.gofishfarm.htkj.ui.login.VerificationCodeFragment;
import com.gofishfarm.htkj.ui.main.AttentionFragment;
import com.gofishfarm.htkj.ui.main.FisherPondFragment;
import com.gofishfarm.htkj.ui.main.MyInfoFragment;
import com.gofishfarm.htkj.ui.main.RankingFragment;
import com.gofishfarm.htkj.ui.main.RankingLeftFragment;
import com.gofishfarm.htkj.ui.main.RankingRightFragment;
import com.gofishfarm.htkj.ui.main.attention.AttentionLeftFragment;
import com.gofishfarm.htkj.ui.main.attention.AttentionRightFragment;
import com.gofishfarm.htkj.ui.myinfo.FishCoinExchangeFragment;
import com.gofishfarm.htkj.ui.myinfo.FishingCoinFragment;
import com.gofishfarm.htkj.ui.myinfo.RedemptionRecordFragment;
import com.gofishfarm.htkj.ui.myinfo.SetUpFragment;

import dagger.Component;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/4/27 19:30
 * 描述:FragmentComponent
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(LoginFragment loginFragment);

    void inject(VerificationCodeFragment verificationCodeFragment);

    void inject(FisherPondFragment fisherPondFragment);

    void inject(AttentionFragment attentionFragment);

    void inject(MyInfoFragment myInfoFragment);

    void inject(RankingFragment rankingFragment);

    void inject(BindingFragment bindingFragment);


    void inject(SetUpFragment setUpFragment);

    void inject(FishingCoinFragment fishingCoinFragment);

    void inject(RedemptionRecordFragment redemptionRecordFragment);

    void inject(RankingLeftFragment rankingLeftFragment);

    void inject(RankingRightFragment rankingRightFragment);

    void inject(AttentionLeftFragment attentionLeftFragment);

    void inject(AttentionRightFragment attentionRightFragment);

    void inject(FishCoinExchangeFragment fishCoinExchangeFragment);
}
