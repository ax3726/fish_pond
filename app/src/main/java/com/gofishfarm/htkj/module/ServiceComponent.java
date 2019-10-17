package com.gofishfarm.htkj.module;

import android.app.Service;

import com.gofishfarm.htkj.module.scope.ServiceScope;
import com.gofishfarm.htkj.service.NetworkService;

import dagger.Component;

@Component(dependencies = {AppComponent.class}, modules = {ServiceModule.class})
@ServiceScope
public interface ServiceComponent {
    Service getService();

    void inject(NetworkService backService2);


}
