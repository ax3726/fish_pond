package com.gofishfarm.htkj.api;

/**
 * MrLiu253@163.com
 *
 * @time 2018/7/6
 */

public interface ConfigApi {

    //域名
    String INFO = "http://148.70.13.176/";//测试
//        String INFO = "http://www.gofishfarm.com/";

    String SOCKETURL = "ws://148.70.13.176:9501?";//url标识测试
//            String SOCKETURL = "ws://212.64.81.173:9501?";//url标识

    String HEARTBEAT_SRT = "heartbeat";//心跳的内容

    //首页信息列表
    public String INDEX_G = "index";

    //发送短信
    public String SEND_SMS_P = "send_sms";

    //注册设备ID
    public String REGISTER_P = "register";

    //版本内容
    //获取版本号
    public String VERSION_APP_TYPE_G = "version/{app_type}";
    //刷新accessToken
    public String REFRESH_REFRESH_TOKEN_APIKEY_G = "refresh/{refresh_token}/{apikey}";

    //榜单
    //获取累计钓鱼数榜单
    public String RANKS = "ranks";
    //获取等时长钓鱼数榜单
    public String RANK_AVE = "rank/ave";

    //用户信息
    //用户登录
    public String LOGIN = "user/login";
    public String BINDING = "binding/phone";
    //个人主页
    public String USERS_FISHER_ID_G = "users/{fisher_id}";
    //个人中心
    public String USERS_G = "users";
    //个人信息
    public String USERS_FISHER_ID_EDIT_G = "users/{fisher_id}/edit";
    //修改个人信息
    public String USERS_FISHER_ID_P = "users/{fisher_id}";
    public String BINFING_WECHAT = "binding/wechat";
    public String COS = "cos";
    //邀请页
    public String INVITATIONS_FISHERID = "invitations/{fisher_id}";
    //用户登出
    public String USER_LOGOUT_P = "user/logout";
    //签到
    public String CLOCKS = "clocks";
    //我的渔币
    //渔币兑换
    public String INTEGRATIONS_P = "integrations";
    //我的渔币
    public String INTEGRATIONS_FISHER_ID_G = "integrations/{fisher_id}";
    //渔币兑换记录
    public String INTEGRATIONS_G = "integrations";

    //关注
    // 关注钓手
    public String FOCUSS_P = "focuss";
    //查看是否已关注该钓手 围观页面需要调取一次，查看是否已经关注垂钓中钓手
    public String FOCUSS_ID_G = "focuss/{id}";
    //取关钓手
    public String FOCUSS_FOCUS_ID_D = "focuss/{focus_id}";
    //关注列表
    public String FOCUSS_G = "focuss";

    //时长
    //我的时长
    public String TIMES_G = "times";

    public String TIMES_FISHER_ID_G = "times/{fisher_id}";

    //订单
    //创建订单
    public String ORDERS_P = "orders";
    // 充值记录
    public String ORDERS_G = "orders";

    //钓鱼
    //进入钓鱼 包括快速开始钓鱼，选择渔场钓鱼，选择钓台钓鱼
    public String FISHING_P = "fishing";

    //任务相关
    //获取任务列表 new
    public String MISSIONS_G = "missions";
    //领取任务奖励 new
    public String MISSIONS_M_ID_P = "missions/{m_id}";

    //绑定
    //绑定手机号 new
    public String BINDING_PHONE_P = "binding/phone";

    //围观
    //进入围观 包括前去围观，选择渔场围观(即直接通过渔场开始围观)，搜索钓手号或者手机号围观（需要完整的手机号）
    public String ONLOOKS_P = "onlooks";

    //粉丝列表
    public String FANS_G = "fans";

    //变现商城
    public String BIANXIANMAO_G = "bianxianmao";
    //变现商城
    public String SHARE_FISHING_G = "share/fishing";

    //新手指南步骤时长
    public String NOVICES_G = "novices";
    //完成新手指南
    public String NOVICES_P = "novices";

    //获取故障类型
    public String NOTICES_G = "notices";
    //用户报障
    public String NOTICES_P = "notices";

    //钓鱼帮助
    public String FISHING_HELP_P = "fishing_help";



    //保存的key
    String USER_INFO = "user_info";
    String AUTHORIZATION = "authorization";
    String FISHDEVCICEBEAN = "FishDevciceBean";
    String ISFISHING = "isFishing";

    String WX_APPID = "wx147a98b0ec2caa53";
    String WX_PARTNERID = "1521749791";
    String WEB_TITLE = "web_title";
    String WEB_URL = "web_url";
    String PAY_URL = "http://www.gofishfarm.com/pay_contract.html";
    String HELP_URL = "http://www.gofishfarm.com/help.html";
    String LOGIN_URL = "http://www.gofishfarm.com/login_contract.html";
    String SIGN_URL = "http://www.gofishfarm.com/sign_contract.html";
    String ABOUT_URL = "http://www.gofishfarm.com/about_us.html";
    String XSZN_URL = "http://www.gofishfarm.com/xszn.html";
    String FOCUSID = "focus_id";
    String FISHERY_ID = "fishery_id";
    String FP_ID = "fp_id";
    String CANUSEMOBILENET = "CanUseMobileNet";

    String FISHGUIDE_REWARD = "reward";

    int timstr_shanger=20;
    int timstr_douer=3;
    int timstr_shuaigan=2;
    int timstr_tiyu=10;
    int timstr_chaoyu=8;
    int timstr_zhaiyu=10;
    int timstr_paogan=8;
    int timstr_chongti=20;


}
