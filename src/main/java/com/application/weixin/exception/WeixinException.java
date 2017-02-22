package com.application.weixin.exception;

import com.application.weixin.exception.JWeixinException;

import java.util.HashMap;

public class WeixinException extends JWeixinException {


    private static final HashMap<Integer, String> errorMap = new HashMap<>();


    public WeixinException(Integer errcode) {
        super(errcode + ":" + errorMap.get(errcode));
        setErrcode(errcode);
        String errmsgTemp = errorMap.get(errcode);
        if (errmsgTemp != null) {
            setErrmsg(errmsgTemp);
        } else {
            //@TODO 记录日志到相应的日志文件中作为日后扩展的参考
        }

    }


    static {

        errorMap.put(-1, "系统繁忙，此时请开发者稍候再试");
        errorMap.put(40001, "获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口");
        errorMap.put(40002, "不合法的凭证类型");
        errorMap.put(40003, "不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID");
        errorMap.put(40004, "不合法的媒体文件类型");
        errorMap.put(40005, "不合法的文件类型");
        errorMap.put(40006, "不合法的文件大小");
        errorMap.put(40007, "不合法的媒体文件id");
        errorMap.put(40008, "不合法的消息类型");
        errorMap.put(40009, "不合法的图片文件大小");
        errorMap.put(40010, "不合法的语音文件大小");
        errorMap.put(40011, "不合法的视频文件大小");
        errorMap.put(40012, "不合法的缩略图文件大小");
        errorMap.put(40013, "不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写");
        errorMap.put(40014, "不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口");
        errorMap.put(40015, "不合法的菜单类型");
        errorMap.put(40016, "不合法的按钮个数");
        errorMap.put(40017, "不合法的按钮个数");
        errorMap.put(40018, "不合法的按钮名字长度");
        errorMap.put(40019, "不合法的按钮KEY长度");
        errorMap.put(40020, "不合法的按钮URL长度");
        errorMap.put(40021, "不合法的菜单版本号");
        errorMap.put(40022, "不合法的子菜单级数");
        errorMap.put(40023, "不合法的子菜单按钮个数");
        errorMap.put(40024, "不合法的子菜单按钮类型");
        errorMap.put(40025, "不合法的子菜单按钮名字长度");
        errorMap.put(40026, "不合法的子菜单按钮KEY长度");
        errorMap.put(40027, "不合法的子菜单按钮URL长度");
        errorMap.put(40028, "不合法的自定义菜单使用用户");
        errorMap.put(40029, "不合法的oauth_code");
        errorMap.put(40030, "不合法的refresh_token");
        errorMap.put(40031, "不合法的openid列表");
        errorMap.put(40032, "不合法的openid列表长度");
        errorMap.put(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符");
        errorMap.put(40035, "不合法的参数");
        errorMap.put(40038, "不合法的请求格式");
        errorMap.put(40039, "不合法的URL长度");
        errorMap.put(40050, "不合法的分组id");
        errorMap.put(40051, "分组名字不合法");
        errorMap.put(40117, "分组名字不合法");
        errorMap.put(40118, "media_id大小不合法");
        errorMap.put(40119, "button类型错误");
        errorMap.put(40120, "button类型错误");
        errorMap.put(40121, "不合法的media_id类型");
        errorMap.put(40132, "微信号不合法");
        errorMap.put(40132, "微信号不合法");
        errorMap.put(40137, "不支持的图片格式");
        errorMap.put(41001, "缺少access_token参数");
        errorMap.put(41002, "缺少appid参数");
        errorMap.put(41003, "缺少refresh_token参数");
        errorMap.put(41004, "缺少secret参数");
        errorMap.put(41005, "缺少多媒体文件数据");
        errorMap.put(41006, "缺少media_id参数");
        errorMap.put(41007, "缺少子菜单数据");
        errorMap.put(41008, "缺少oauth code");
        errorMap.put(41009, "缺少openid");
        errorMap.put(42001, "access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明");
        errorMap.put(42002, "refresh_token超时");
        errorMap.put(42003, "oauth_code超时");
        errorMap.put(42007, "用户修改微信密码，accesstoken和refreshtoken失效，需要重新授权");
        errorMap.put(43001, "需要GET请求");
        errorMap.put(43002, "需要POST请求");
        errorMap.put(43003, "需要HTTPS请求");
        errorMap.put(43004, "需要接收者关注");
        errorMap.put(43005, "需要好友关系");
        errorMap.put(44001, "多媒体文件为空");
        errorMap.put(44002, "POST的数据包为空");
        errorMap.put(44003, "图文消息内容为空");
        errorMap.put(44004, "文本消息内容为空");
        errorMap.put(45001, "多媒体文件大小超过限制");
        errorMap.put(45002, "消息内容超过限制");
        errorMap.put(45003, "标题字段超过限制");
        errorMap.put(45004, "描述字段超过限制");
        errorMap.put(45005, "链接字段超过限制");
        errorMap.put(45006, "图片链接字段超过限制");
        errorMap.put(45007, "语音播放时间超过限制");
        errorMap.put(45008, "图文消息超过限制");
        errorMap.put(45009, "接口调用超过限制");
        errorMap.put(45010, "创建菜单个数超过限制");
        errorMap.put(45015, "回复时间超过限制");
        errorMap.put(45016, "系统分组，不允许修改");
        errorMap.put(45017, "分组名字过长");
        errorMap.put(45018, "分组数量超过上限");
        errorMap.put(45047, "客服接口下行条数超过上限");
        errorMap.put(46001, "不存在媒体数据");
        errorMap.put(46002, "不存在的菜单版本");
        errorMap.put(46003, "不存在的菜单数据");
        errorMap.put(46004, "不存在的用户");
        errorMap.put(47001, "解析JSON/XML内容错误");
        errorMap.put(48001, "api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限");
        errorMap.put(48004, "api接口被封禁，请登录mp.weixin.qq.com查看详情");
        errorMap.put(50001, "用户未授权该api");
        errorMap.put(50002, "用户受限，可能是违规后接口被封禁");
        errorMap.put(61451, "参数错误(invalid parameter)");
        errorMap.put(61452, "无效客服账号(invalid kf_account)");
        errorMap.put(61453, "客服帐号已存在(kf_account exsited)");
        errorMap.put(61454, "客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalid kf_acount length)");
        errorMap.put(61455, "客服帐号名包含非法字符(仅允许英文+数字)(illegal character in kf_account)");
        errorMap.put(61456, "客服帐号个数超过限制(10个客服账号)(kf_account count exceeded)");
        errorMap.put(61457, "无效头像文件类型(invalid file type)");
        errorMap.put(61450, "系统错误(system error)");
        errorMap.put(61500, "日期格式错误");
        errorMap.put(65301, "不存在此menuid对应的个性化菜单");
        errorMap.put(65302, "没有相应的用户");
        errorMap.put(65303, "没有默认菜单，不能创建个性化菜单");
        errorMap.put(65304, "MatchRule信息为空");
        errorMap.put(65305, "个性化菜单数量受限");
        errorMap.put(65306, "不支持个性化菜单的帐号");
        errorMap.put(65307, "个性化菜单信息为空");
        errorMap.put(65308, "包含没有响应类型的button");
        errorMap.put(65309, "个性化菜单开关处于关闭状态");
        errorMap.put(65310, "填写了省份或城市信息，国家信息不能为空");
        errorMap.put(65311, "填写了城市信息，省份信息不能为空");
        errorMap.put(65312, "不合法的国家信息");
        errorMap.put(65313, "不合法的省份信息");
        errorMap.put(65314, "不合法的城市信息");
        errorMap.put(65316, "该公众号的菜单设置了过多的域名外跳（最多跳转到3个域名的链接）");
        errorMap.put(65317, "不合法的URL");
        errorMap.put(65317, "不合法的URL");
        errorMap.put(9001001, "POST数据参数不合法");
        errorMap.put(9001002, "远端服务不可用");
        errorMap.put(9001003, "Ticket不合法");
        errorMap.put(9001004, "获取摇周边用户信息失败");
        errorMap.put(9001005, "获取商户信息失败");
        errorMap.put(9001006, "获取OpenID失败");
        errorMap.put(9001007, "上传文件缺失");
        errorMap.put(9001008, "上传素材的文件类型不合法");
        errorMap.put(9001009, "上传素材的文件尺寸不合法");
        errorMap.put(9001010, "上传失败");
        errorMap.put(9001020, "帐号不合法");
        errorMap.put(9001021, "已有设备激活率低于50%，不能新增设备");
        errorMap.put(9001022, "设备申请数不合法，必须为大于0的数字");
        errorMap.put(9001023, "已存在审核中的设备ID申请");
        errorMap.put(9001024, "一次查询设备ID数量不能超过50");
        errorMap.put(9001025, "设备ID不合法");
        errorMap.put(9001026, "页面ID不合法");
        errorMap.put(9001027, "页面参数不合法");
        errorMap.put(9001028, "一次删除页面ID数量不能超过10");
        errorMap.put(9001029, "页面已应用在设备中，请先解除应用关系再删除");
        errorMap.put(9001030, "一次查询页面ID数量不能超过50");
        errorMap.put(9001031, "时间区间不合法");
        errorMap.put(9001032, "保存设备与页面的绑定关系参数错误");
        errorMap.put(9001033, "门店ID不合法");
        errorMap.put(9001034, "设备备注信息过长");
        errorMap.put(9001035, "设备申请参数不合法");
        errorMap.put(9001036, "查询起始值begin不合法");

    }


}
