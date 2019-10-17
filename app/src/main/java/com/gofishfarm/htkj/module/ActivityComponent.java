package com.gofishfarm.htkj.module;

import android.app.Activity;

import com.gofishfarm.htkj.module.scope.ActivityScope;
import com.gofishfarm.htkj.ui.feedback.FeedBackActivity;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.main.MainActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.FishGuideActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.FishGuideFinishActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.OnLookActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.RechargeActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.RechargeRecordActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishNewCommerActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishingActivity;
import com.gofishfarm.htkj.ui.myinfo.FishingCoinActivity;
import com.gofishfarm.htkj.ui.myinfo.HomepageActivity;
import com.gofishfarm.htkj.ui.myinfo.InviteFriendsActivity;
import com.gofishfarm.htkj.ui.myinfo.SetUpActivity;
import com.gofishfarm.htkj.ui.myinfo.SignInActivity;
import com.gofishfarm.htkj.ui.myinfo.UserInformationActivity;
import com.gofishfarm.htkj.ui.start.SplashActivity;
import com.gofishfarm.htkj.ui.webPage.WebActivity;

import dagger.Component;

@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
@ActivityScope
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(UserFishingActivity userFishingActivity);

    void inject(OnLookActivity onLookActivity);

    void inject(RechargeActivity rechargeActivity);

    void inject(SplashActivity splashActivity);

    void inject(UserInformationActivity userInformationActivity);

    void inject(SetUpActivity setUpActivity);

    void inject(FishingCoinActivity fishingCoinActivity);

    void inject(InviteFriendsActivity inviteFriendsActivity);

    void inject(RechargeRecordActivity rechargeRecordActivity);

    void inject(WebActivity webActivity);

    void inject(SignInActivity signInActivity);

    void inject(HomepageActivity homepageActivity);

    void inject(FishGuideActivity fishGuideActivity);

    void inject(FishGuideFinishActivity fishGuideFinishActivity);

    void inject(UserFishNewCommerActivity userFishNewCommerActivity);

    void inject(FeedBackActivity feedBackActivity);
}
