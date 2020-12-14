/**
 * Created by FelixAn on 2016/8/3.
 */
var baseInfoQuery = {
    createDroplist: function () {
        var topOrgDroplist = droplist({
            el: "#wechat-baseQuery-top-droplist",
            // data: ["移动互联应用服务管理平台测试服务器", "昆明市住房公积金管理中心", "深圳市住房公积金管理中心", "哈尔滨住房公积金管理中心农垦分中心", "济南住房公积金管理中心", "威海市住房公积金管理中心"]
            data: [{"00000000","移动互联应用服务管理平台测试服务器"},{ "000870100","昆明市住房公积金管理中心"}]
        });
        return topOrgDroplist;
    },
    createTable: function (data) {
        //wechat-baseQuery-table
        var cols = [
            { title:'城市中心', name:'regionId' ,width:180, align: 'center' },
            { title:'微信帐号', name:'weixinId' ,width:88, align: 'center'},
            { title:'开发者ID', name:'appId' ,width:108, align: 'center'},
            { title:'开发者密钥', name:'appScret' ,width:146, align: 'center'},
            { title:'中心Token', name:'msgToken' ,width:224, align: 'center'},
            { title:'链接地址', name:'msgUrl' ,width:280, align: 'center'}
        ];
        data = {"errcode":0,"errmsg":"","rows":[{"storetype":"channel","weixinId":"gh_a49a7c631bc8","regionId":"00025000","appId":"wx0047d1d3b986df5b","appScret":"953aa070b5a0d60e48e1d40d5faa458d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_eb23d1e67612","regionId":"00031800","appId":"wx4c4606ce40339029","appScret":"d4624c36b6795d1d99dcf0547af5443d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.10/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_69be4ba1c92","regionId":"00045101","appId":"wxaf722c5f3b3d102c","appScret":"9bb5ffe6bc97ca76f333e617c7b8fca1","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.202.206.82/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ad121e41bde6","regionId":"00053100","appId":"wx19d9946ac0643952","appScret":"316afdabdfe88a9d7e1790818397f66d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.7/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_9860dc409841","regionId":"00057400","appId":"wx1087ee5929104279","appScret":"d4624c36b6795d1d99dcf0547af5443d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://myvpn.xicp.cn/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_f9f66d752d3e","regionId":"00063100","appId":"wxc2c772a66352c6df","appScret":"d5c25586eb83e50919fada815b4ef167","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.10/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"testå¾®ä¿¡è´¦å·","regionId":"00075500","appId":"testZH","appScret":"testMY","msgToken":"testToken","msgUrl":"http://testé“¾æŽ¥åœ°å€","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ee9416d578067","regionId":"00076000","appId":"wx50302d635cbb0d14","appScret":"35cbe0b0a2b522f4e9ef66701aa65a37","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.205.146.106/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_7c7aabd2799b","regionId":"00083800","appId":"wx5da71e91f64d4ff3","appScret":"ef32ae88a3e35543ba0b635697dd5809","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://myvpn.xicp.cn/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ad121e41bde6","regionId":"00087100","appId":"wx19d9946ac0643952","appScret":"316afdabdfe88a9d7e1790818397f66d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ee9416d57806","regionId":"00087500","appId":"wx50302d635cbb0d14","appScret":"35cbe0b0a2b522f4e9ef66701aa65a37","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_69be4ba1c924","regionId":"00091200","appId":"wxaf722c5f3b3d102c","appScret":"9bb5ffe6bc97ca76f333e617c7b8fca1","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.202.206.82/weixin/entrance","buf":"","all":0}]};
        var wechatTable = $('#wechat-baseQuery-table').mmGrid({
            multiSelect: false,
            indexCol: true,
            checkCol: false,
            height: '360px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        this.bottomBtnsClick(wechatTable);
    },
    bottomBtnsClick: function (table) {
        var table = table;
        $("#wechat-btn-add").on("click", function () {
            var createHTML = $(".wechat-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-btn-edit").on("click", function () {
            var editHTML = $(".wechat-edit").html();
            parent.Common.popupShow(editHTML);
        });
    }
};
var fnConfig = {
    createTable: function (data) {
        //wechat-fnConfig-table
        var cols = [
            { title:'包名', name:'regionId' ,width:166, align: 'center' },
            { title:'类名', name:'funcName' ,width:214, align: 'center'},
            { title:'路径', name:'className' ,width:264, align: 'center'},
            { title:'KEY', name:'keyname' ,width:190, align: 'center'},
            { title:'描述', name:'nickname' ,width:190, align: 'center'}
        ];
        data = {
            "errcode": 0,
            "errmsg": "",
            "rows": [
                {
                    "storetype": "function",
                    "regionId": "d00010000",
                    "className": "function.d00010000.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00010000",
                    "className": "function.d00010000.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00010000",
                    "className": "function.d00010000.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00010000",
                    "className": "function.d00010000.PaperNews",
                    "funcName": "PaperNews",
                    "keyname": "paper_news",
                    "nickname": "ceshi"
                },
                {
                    "storetype": "function",
                    "regionId": "d00010000",
                    "className": "function.d00010000.Partner",
                    "funcName": "Partner",
                    "keyname": "partner",
                    "nickname": "åˆä½œä¼™ä¼´"
                },
                {
                    "storetype": "function",
                    "regionId": "d00010000",
                    "className": "function.d00010000.RelativeWe",
                    "funcName": "RelativeWe",
                    "keyname": "relative_we",
                    "nickname": "è”ç³»æˆ‘ä»¬"
                },
                {
                    "storetype": "function",
                    "regionId": "d00010000",
                    "className": "function.d00010000.ShowContent",
                    "funcName": "ShowContent",
                    "keyname": "show_content",
                    "nickname": "è§£å†³æ–¹æ¡ˆ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.AgreementSearch",
                    "funcName": "AgreementSearch",
                    "keyname": "agreement_search",
                    "nickname": "åè®®æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "ccrf_accountSearch",
                    "nickname": "è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.ChannelSet",
                    "funcName": "ChannelSet",
                    "keyname": "channel_set",
                    "nickname": "æ¸ é“è®¾ç½®"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_accountSearch",
                    "nickname": "è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "å¯¹è´¦å•"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "ç»“æ¯å¯¹è´¦å•ç»“æžœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extrac_bill",
                    "nickname": "æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.CrfHouseSearch",
                    "funcName": "CrfHouseSearch",
                    "keyname": "crf_house_search",
                    "nickname": "æµŽå—ä½æˆ¿è¡¥è´´"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»èµ„è®¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.LoanLoanClearSearch",
                    "funcName": "LoanLoanClearSearch",
                    "keyname": "loan_loan_clear_search",
                    "nickname": "ç»“æ¸…è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.MyMessage",
                    "funcName": "MyMessage",
                    "keyname": "my_message",
                    "nickname": "åœ¨çº¿ç•™è¨€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unSubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.PayPlan",
                    "funcName": "PayPlan",
                    "keyname": "pay_plan",
                    "nickname": "è¿˜æ¬¾è®¡åˆ’"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00025000",
                    "className": "function.d00025000.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.CheckPwd",
                    "funcName": "CheckPwd",
                    "keyname": "check_pwd",
                    "nickname": "è¡¡æ°´å¯†ç éªŒè¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "è¡¡æ°´å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "è¡¡æ°´ç»“æ¯å¯¹è´¦å•"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "è¡¡æ°´å…¬ç§¯é‡‘å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "è¡¡æ°´æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "è¡¡æ°´æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "è¡¡æ°´æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "è¡¡æ°´è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "è¡¡æ°´æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "è¡¡æ°´è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "è¡¡æ°´è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è¡¡æ°´è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "è¡¡æ°´æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "è¡¡æ°´ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "è¡¡æ°´å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "è¡¡æ°´å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "è¡¡æ°´å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "è¡¡æ°´ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "è¡¡æ°´è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "è¡¡æ°´ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "è¡¡æ°´ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "è¡¡æ°´ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "è¡¡æ°´åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "è¡¡æ°´ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031800",
                    "className": "function.d00031800.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "è¡¡æ°´å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.CheckPwd",
                    "funcName": "CheckPwd",
                    "keyname": "check_pwd",
                    "nickname": "å¯†ç éªŒè¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "ç»“æ¯å¯¹è´¦å•"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "å…¬ç§¯é‡‘å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.LeaveMessageOnline",
                    "funcName": "LeaveMessageOnline",
                    "keyname": "leave_message_online",
                    "nickname": "åœ¨çº¿ç•™è¨€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "SerAccountBind"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00031900",
                    "className": "function.d00031900.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.CheckPwd",
                    "funcName": "CheckPwd",
                    "keyname": "check_pwd",
                    "nickname": "ç§¦çš‡å²›å¯†ç éªŒè¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "ç§¦çš‡å²›å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "ç§¦çš‡å²›ç»“æ¯å¯¹è´¦å•"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "ç§¦çš‡å²›å…¬ç§¯é‡‘å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "ç§¦çš‡å²›æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "ç§¦çš‡å²›æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "ç§¦çš‡å²›æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "ç§¦çš‡å²›è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "ç§¦çš‡å²›æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "ç§¦çš‡å²›è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "ç§¦çš‡å²›è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "ç§¦çš‡å²›è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "ç§¦çš‡å²›æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "ç§¦çš‡å²›ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "ç§¦çš‡å²›å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "ç§¦çš‡å²›å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "ç§¦çš‡å²›å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "ç§¦çš‡å²›ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "ç§¦çš‡å²›è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "ç§¦çš‡å²›ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ç§¦çš‡å²›ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "ç§¦çš‡å²›ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "ç§¦çš‡å²›åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "ç§¦çš‡å²›ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00033500",
                    "className": "function.d00033500.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "ç§¦çš‡å²›å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.CheckPwd",
                    "funcName": "CheckPwd",
                    "keyname": "check_pwd",
                    "nickname": "å“ˆå°”æ»¨å¯†ç éªŒè¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å“ˆå°”æ»¨å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "å“ˆå°”æ»¨ç»“æ¯å¯¹è´¦å•"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "å“ˆå°”æ»¨å…¬ç§¯é‡‘æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extrac_bill",
                    "nickname": "å“ˆå°”æ»¨æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "å“ˆå°”æ»¨æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "å“ˆå°”æ»¨æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.ExtractStepSearch",
                    "funcName": "ExtractStepSearch",
                    "keyname": "extract_step_search",
                    "nickname": "å“ˆå°”æ»¨æå–è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "å“ˆå°”æ»¨è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "å“ˆå°”æ»¨æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "å“ˆå°”æ»¨è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "å“ˆå°”æ»¨è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "å“ˆå°”æ»¨è´·æ¬¾è¿›åº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "å“ˆå°”æ»¨æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "å“ˆå°”æ»¨ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "å“ˆå°”æ»¨å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å“ˆå°”æ»¨å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unSubscribe",
                    "nickname": "å“ˆå°”æ»¨å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.Recommend",
                    "funcName": "Recommend",
                    "keyname": "recommend",
                    "nickname": "æ„è§å»ºè®®"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "å“ˆå°”æ»¨ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "å“ˆå°”æ»¨è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "å“ˆå°”æ»¨ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "å“ˆå°”æ»¨ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "å“ˆå°”æ»¨åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_pecial_service",
                    "nickname": "å“ˆå°”æ»¨ç‰¹æ–¯æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "å“ˆå°”æ»¨ç‰¹æ–¯æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "å“ˆå°”æ»¨å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00045101",
                    "className": "function.d00045101.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "SerAutoReplay",
                    "nickname": "å“ˆå°”æ»¨è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CitySerAccountBind",
                    "funcName": "CitySerAccountBind",
                    "keyname": "city_account_bind",
                    "nickname": "æµŽå—åŸŽå¸‚æœåŠ¡è´¦æˆ·ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CityCrfAccountSearch",
                    "funcName": "CityCrfAccountSearch",
                    "keyname": "city_account_search",
                    "nickname": "åŸŽå¸‚æœåŠ¡å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CityLoanLoanSearch",
                    "funcName": "CityLoanLoanSearch",
                    "keyname": "city_loan_search",
                    "nickname": "æµŽå—åŸŽå¸‚æœåŠ¡è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CityService",
                    "funcName": "CityService",
                    "keyname": "city_service",
                    "nickname": "æµŽå—åŸŽå¸‚æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CitySpecialService",
                    "funcName": "CitySpecialService",
                    "keyname": "city_special_service",
                    "nickname": "æµŽå—åŸŽå¸‚æœåŠ¡ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "è§£æžå¯¹è´¦å•"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "ç»“æ¯å¯¹è´¦ç»“æžœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»èµ„è®¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.ExtBizHand",
                    "funcName": "ExtBizHand",
                    "keyname": "ext_biz_hand",
                    "nickname": "æµŽå—æå–ä¸šåŠ¡åŠžç†"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultaion",
                    "nickname": "æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LoanAmountCalculate",
                    "funcName": "LoanAmountCalculate",
                    "keyname": "loan_amount_calculate",
                    "nickname": "æµŽå—è´·æ¬¾é¢åº¦è®¡ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LoanRepayPlanSearch",
                    "funcName": "LoanRepayPlanSearch",
                    "keyname": "loan_repay_plan",
                    "nickname": " æµŽå—è¿˜æ¬¾è®¡åˆ’æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unSubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.AccountSearch",
                    "funcName": "AccountSearch",
                    "keyname": "per_account_search",
                    "nickname": "ä¸ªäººè´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.PersonAuth",
                    "funcName": "PersonAuth",
                    "keyname": "ser_auth_bind",
                    "nickname": "æµŽå—ä¸ªäººè®¤è¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00053100",
                    "className": "function.d00053100.SmsSign",
                    "funcName": "SmsSign",
                    "keyname": "sms_sign",
                    "nickname": "æµŽå—çŸ­ä¿¡ç­¾çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CenterIntroduction",
                    "funcName": "CenterIntroduction",
                    "keyname": "center_introduction",
                    "nickname": "ä¸­å¿ƒç®€ä»‹"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CompanyLxpz",
                    "funcName": "CompanyLxpz",
                    "keyname": "company_lxpz",
                    "nickname": "å•ä½åˆ©æ¯å‡­è¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CompanySearch",
                    "funcName": "CompanySearch",
                    "keyname": "company_search",
                    "nickname": "å•ä½æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CompanyZhSearch",
                    "funcName": "CompanyZhSearch",
                    "keyname": "company_searchgjjzh",
                    "nickname": "å®æ³¢å•ä½è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CompanyZhdz",
                    "funcName": "CompanyZhdz",
                    "keyname": "company_zhdz",
                    "nickname": "å•ä½è´¦æˆ·å¯¹è´¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "CrfBill"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "CrfConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "CrfExtracBill"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "ExtractConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.FinalMessage",
                    "funcName": "FinalMessage",
                    "keyname": "final_message",
                    "nickname": "å¸¸ç”¨ä¿¡æ¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.FinalQuestion",
                    "funcName": "FinalQuestion",
                    "keyname": "final_question",
                    "nickname": "é€šçŸ¥å…¬å‘Š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.LeaveMessageOnline",
                    "funcName": "LeaveMessageOnline",
                    "keyname": "leave_message_online",
                    "nickname": "åœ¨çº¿ç•™è¨€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "LoanConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "LoanHouseSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "LoanLoanCalculate"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "LoanLoanSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "LocationInfo"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "SubscribeScan"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "Subscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "UnSubscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalCrfConsultation",
                    "funcName": "PersonalCrfConsultation",
                    "keyname": "personal_crfconsultation",
                    "nickname": "å•ä½ç¼´å­˜"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalExtractConsultation",
                    "funcName": "PersonalExtractConsultation",
                    "keyname": "personal_extractconsultation",
                    "nickname": "ä¸ªäººæå–"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalLoanConsultation",
                    "funcName": "PersonalLoanConsultation",
                    "keyname": "personal_loanconsultation",
                    "nickname": "ä¸ªäººè´·æ¬¾"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalNdys",
                    "funcName": "PersonalNdys",
                    "keyname": "personal_nianduys",
                    "nickname": "å¹´åº¦éªŒå®¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalSearch",
                    "funcName": "PersonalSearch",
                    "keyname": "personal_search",
                    "nickname": "ä¸ªäººæŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalZhSearch",
                    "funcName": "PersonalZhSearch",
                    "keyname": "personal_searchgjjzh",
                    "nickname": "ä¸ªäººè´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalXdsp",
                    "funcName": "PersonalXdsp",
                    "keyname": "personal_xdsp",
                    "nickname": "ä¸ªäººè´·æ¬¾å®¡æ‰¹"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.PersonalZhdz",
                    "funcName": "PersonalZhdz",
                    "keyname": "personal_zhdz",
                    "nickname": "ä¸ªäººè´¦æˆ·å¯¹è´¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "SerAccountBind"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "SerAutoReplay"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "SerBusinessConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "SerNodeSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerNotice",
                    "funcName": "SerNotice",
                    "keyname": "ser_notice",
                    "nickname": "é€šçŸ¥å…¬å‘Š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "SerOnlineReserve"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "SerPpecialService"
                },
                {
                    "storetype": "function",
                    "regionId": "d00057400",
                    "className": "function.d00057400.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "SerWeikefu"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "CrfBill"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "CrfConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "CrfExtracBill"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "ExtractConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LeaveMessageOnline",
                    "funcName": "LeaveMessageOnline",
                    "keyname": "leave_message_online",
                    "nickname": "åœ¨çº¿ç•™è¨€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LoanAgreement",
                    "funcName": "LoanAgreement",
                    "keyname": "loan_agreement",
                    "nickname": "å¨æµ·å†²è´·åè®®"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "LoanConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "LoanHouseSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "LoanLoanCalculate"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "LoanLoanSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.MySubcribe",
                    "funcName": "MySubcribe",
                    "keyname": "mysubcribe",
                    "nickname": "æˆ‘çš„è®¢é˜…"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "LocationInfo"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "SubscribeScan"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "Subscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "UnSubscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.PaperNews",
                    "funcName": "PaperNews",
                    "keyname": "paper_news",
                    "nickname": "å…¬ç§¯é‡‘æŠ¥æ–°é—»æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "SerAccountBind"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "SerAutoReplay"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "SerBusinessConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "SerNodeSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "SerOnlineReserve"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "SerPpecialService"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "SerWeikefu"
                },
                {
                    "storetype": "function",
                    "regionId": "d00063100",
                    "className": "function.d00063100.SubcribeSet",
                    "funcName": "SubcribeSet",
                    "keyname": "subcribeset",
                    "nickname": "è®¢é˜…è®¾ç½®"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "CrfBill"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "CrfConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "CrfExtracBill"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "ExtractConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.LeaveMessageOnline",
                    "funcName": "LeaveMessageOnline",
                    "keyname": "leave_message_online",
                    "nickname": "åœ¨çº¿ç•™è¨€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "LoanConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "LoanHouseSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "LoanLoanCalculate"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "LoanLoanSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "LocationInfo"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "SubscribeScan"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "Subscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "UnSubscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "SerAccountBind"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "SerAutoReplay"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "SerBusinessConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "SerNodeSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "SerOnlineReserve"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "SerPpecialService"
                },
                {
                    "storetype": "function",
                    "regionId": "d00075800",
                    "className": "function.d00075800.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "SerWeikefu"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "ç»“æ¯å¯¹è´¦å•"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "å…¬ç§¯é‡‘å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extrac_bill",
                    "nickname": "æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "æå–é‡‘é¢æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LeaveMessageOnline",
                    "funcName": "LeaveMessageOnline",
                    "keyname": "leave_message_online",
                    "nickname": "åœ¨çº¿ç•™è¨€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LoanAmountCalculate",
                    "funcName": "LoanAmountCalculate",
                    "keyname": "loan_amount_calculate",
                    "nickname": "è´·æ¬¾é¢åº¦è®¡ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "æµŽå—æ‰«æäº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "SerAccountBind"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00076000",
                    "className": "function.d00076000.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.MySubcribe",
                    "funcName": "MySubcribe",
                    "keyname": "my_subcribe",
                    "nickname": "æˆ‘çš„è®¢é˜…"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.PaperNews",
                    "funcName": "PaperNews",
                    "keyname": "paper_news",
                    "nickname": "å…¬ç§¯é‡‘æŠ¥æ–°é—»æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.Recommend",
                    "funcName": "Recommend",
                    "keyname": "recommend",
                    "nickname": "æŠ•è¯‰å»ºè®®"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.RelativeWe",
                    "funcName": "RelativeWe",
                    "keyname": "relative_we",
                    "nickname": "è”ç³»æˆ‘ä»¬"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.SubcribeSet",
                    "funcName": "SubcribeSet",
                    "keyname": "subcribe_set",
                    "nickname": "è®¢é˜…è®¾ç½®"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.TodayPaperNews",
                    "funcName": "TodayPaperNews",
                    "keyname": "today_papernews",
                    "nickname": "ä»Šæ—¥æ–°é—»"
                },
                {
                    "storetype": "function",
                    "regionId": "d00077390",
                    "className": "function.d00077390.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "å…¬ç§¯é‡‘å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "è‚‡åº†ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "å…³æ³¨åŽæ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "ç»‘å®šäº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "è‡ªåŠ¨é—®ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00083800",
                    "className": "function.d00083800.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.AboutWe",
                    "funcName": "AboutWe",
                    "keyname": "about_we",
                    "nickname": "æ˜†æ˜Žå…³äºŽæˆ‘ä»¬"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.AbouteWe",
                    "funcName": "AbouteWe",
                    "keyname": "aboute_we",
                    "nickname": "æ˜†æ˜Žå…³äºŽæˆ‘ä»¬"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.CenterInformation",
                    "funcName": "CenterInformation",
                    "keyname": "center_inform",
                    "nickname": "æ˜†æ˜Žä¸­å¿ƒèµ„è®¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.CenterNotice",
                    "funcName": "CenterNotice",
                    "keyname": "center_notice",
                    "nickname": "æ˜†æ˜Žä¸­å¿ƒå…¬å‘Š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "æ˜†æ˜Žå…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "æ˜†æ˜Žå…¬ç§¯é‡‘å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æ˜†æ˜Žæå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.LoanArreaSearch",
                    "funcName": "LoanArreaSearch",
                    "keyname": "loan_arrears_search",
                    "nickname": "æ˜†æ˜Žæ¬ æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "æ˜†æ˜Žè´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "æ˜†æ˜Žæ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "æ˜†æ˜Žè´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "æ˜†æ˜Žè´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.LoanRepayPlanSearch",
                    "funcName": "LoanRepayPlanSearch",
                    "keyname": "loan_repay_plan",
                    "nickname": "æ˜†æ˜Žè¿˜æ¬¾è®¡åˆ’æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ˜†æ˜Žæ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "æ˜†æ˜Žå…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "æ˜†æ˜Žå–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.PolicyLaw",
                    "funcName": "PolicyLaw",
                    "keyname": "policy_law",
                    "nickname": "æ˜†æ˜Žæ”¿ç­–æ³•è§„"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "æ˜†æ˜Žç»‘å®šäº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "æ˜†æ˜Žè‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "æ˜†æ˜Žç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "æ˜†æ˜ŽåŠžäº‹æŒ‡å—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.ServiceChannel",
                    "funcName": "ServiceChannel",
                    "keyname": "service_channel",
                    "nickname": "æ˜†æ˜ŽæœåŠ¡æ¸ é“"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.WifiPasswordSearch",
                    "funcName": "WifiPasswordSearch",
                    "keyname": "wifi_password",
                    "nickname": "wifiå¯†ç æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087100",
                    "className": "function.d00087100.WorkDynamic",
                    "funcName": "WorkDynamic",
                    "keyname": "work_dynamic",
                    "nickname": "æ˜†æ˜Žå·¥ä½œåŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "city_account_search",
                    "nickname": "ä¿å±±æµ‹è¯•è´¦æˆ·ä½™é¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "city_message_center",
                    "nickname": "ä¿å±±æµ‹è¯•æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "city_news",
                    "nickname": "ä¿å±±æµ‹è¯•æ–°é—»"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.CityService",
                    "funcName": "CityService",
                    "keyname": "city_service",
                    "nickname": "æµ‹è¯•ä¸­å±±ç”¨åŸŽå¸‚æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.Consultation",
                    "funcName": "Consultation",
                    "keyname": "consultation",
                    "nickname": "ä¿å±±ä¸šåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "ä¿å±±å…¬ç§¯é‡‘ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.EmployInfo",
                    "funcName": "EmployInfo",
                    "keyname": "employ_info",
                    "nickname": "åº”è˜æŽ¨å¹¿"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "æ¥¼ç›˜æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "ä¿å±±è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.OtherConsultation",
                    "funcName": "OtherConsultation",
                    "keyname": "other_consultation",
                    "nickname": "ä¿å±±å…¶ä»–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "ä¿å±±ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "ä¿å±±å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "ä¿å±±å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unSubscribe",
                    "nickname": "ä¿å±±å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.RepresentSearch",
                    "funcName": "RepresentSearch",
                    "keyname": "represent_search",
                    "nickname": "æµ‹è¯•æˆ‘ä¸ºå…¬ç§¯é‡‘ä»£è¨€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "ä¿å±±ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerAuthflagBind",
                    "funcName": "SerAuthflagBind",
                    "keyname": "ser_auth_bind",
                    "nickname": "ä¿å±±èº«ä»½è®¤è¯"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¿å±±ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "ä¿å±±ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "ä¿å±±åœ¨çº¿é¢„çº¦"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "ä¿å±±ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "ä¿å±±å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00087500",
                    "className": "function.d00087500.WuYeFee",
                    "funcName": "WuYeFee",
                    "keyname": "wuye_fee",
                    "nickname": "ä¿å±±ç‰©ä¸šè´¹æå–"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.Consultation",
                    "funcName": "Consultation",
                    "keyname": "consultation",
                    "nickname": "æ¦†æž—ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "æ¦†æž—è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "æ¦†æž—å…¬ç§¯é‡‘ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.DynamicNews",
                    "funcName": "DynamicNews",
                    "keyname": "dynamic_news",
                    "nickname": "æ¦†æž—æ–°é—»åŠ¨æ€"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "æ¦†æž—æå–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "æ¦†æž—è´·æ¬¾ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "æ¦†æž—è´·æ¬¾è¯•ç®—"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "æ¦†æž—è´·æ¬¾æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "æ¦†æž—è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¦†æž—æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.OtherConsultation",
                    "funcName": "OtherConsultation",
                    "keyname": "other_consultation",
                    "nickname": "æ¦†æž—å…¶ä»–ä¸šåŠ¡å’¨è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "æ¦†æž—ä½ç½®äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "æ¦†æž—å…³æ³¨æ‰«ç "
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "æ¦†æž—å…³æ³¨äº‹ä»¶"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unSubscribe",
                    "nickname": "æ¦†æž—å–æ¶ˆå…³æ³¨"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "æ¦†æž—ç»‘å®š"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "æ¦†æž—è‡ªåŠ¨åº”ç­”"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "æ¦†æž—ä¿å±±ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "æ¦†æž—ç½‘ç‚¹æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "æ¦†æž—ç‰¹è‰²æœåŠ¡"
                },
                {
                    "storetype": "function",
                    "regionId": "d00091200",
                    "className": "function.d00091200.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "æ¦†æž—å¾®å®¢æœ"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.CrfAccountSearch",
                    "funcName": "CrfAccountSearch",
                    "keyname": "crf_account_search",
                    "nickname": "å…¬ç§¯é‡‘è´¦æˆ·æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.CrfBill",
                    "funcName": "CrfBill",
                    "keyname": "crf_bill",
                    "nickname": "CrfBill"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.CrfConsultation",
                    "funcName": "CrfConsultation",
                    "keyname": "crf_consultation",
                    "nickname": "CrfConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.CrfExtracBill",
                    "funcName": "CrfExtracBill",
                    "keyname": "crf_extract_bill",
                    "nickname": "CrfExtracBill"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.ExtractConsultation",
                    "funcName": "ExtractConsultation",
                    "keyname": "extract_consultation",
                    "nickname": "ExtractConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.LoanConsultation",
                    "funcName": "LoanConsultation",
                    "keyname": "loan_consultation",
                    "nickname": "LoanConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.LoanHouseSearch",
                    "funcName": "LoanHouseSearch",
                    "keyname": "loan_house_search",
                    "nickname": "LoanHouseSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.LoanLoanCalculate",
                    "funcName": "LoanLoanCalculate",
                    "keyname": "loan_loan_calculate",
                    "nickname": "LoanLoanCalculate"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.LoanLoanSearch",
                    "funcName": "LoanLoanSearch",
                    "keyname": "loan_loan_search",
                    "nickname": "LoanLoanSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.LoanloanStepSearch",
                    "funcName": "LoanloanStepSearch",
                    "keyname": "loan_loan_step_search",
                    "nickname": "è´·æ¬¾è¿›åº¦æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.MessageCenter",
                    "funcName": "MessageCenter",
                    "keyname": "message_center",
                    "nickname": "æ¶ˆæ¯ä¸­å¿ƒ"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.LocationInfo",
                    "funcName": "LocationInfo",
                    "keyname": "other_location",
                    "nickname": "LocationInfo"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SubscribeScan",
                    "funcName": "SubscribeScan",
                    "keyname": "other_scan",
                    "nickname": "SubscribeScan"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.Subscribe",
                    "funcName": "Subscribe",
                    "keyname": "other_subscribe",
                    "nickname": "Subscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.UnSubscribe",
                    "funcName": "UnSubscribe",
                    "keyname": "other_unsubscribe",
                    "nickname": "UnSubscribe"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.PaperNews",
                    "funcName": "PaperNews",
                    "keyname": "paper_news",
                    "nickname": "å…¬ç§¯é‡‘æŠ¥æ–°é—»æŸ¥è¯¢"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerAccountBind",
                    "funcName": "SerAccountBind",
                    "keyname": "ser_account_bind",
                    "nickname": "SerAccountBind"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerAutoReplay",
                    "funcName": "SerAutoReplay",
                    "keyname": "ser_auto_replay",
                    "nickname": "SerAutoReplay"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerBusinessConsultation",
                    "funcName": "SerBusinessConsultation",
                    "keyname": "ser_business_consultation",
                    "nickname": "SerBusinessConsultation"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerDownload",
                    "funcName": "SerDownload",
                    "keyname": "ser_download",
                    "nickname": "ä¸‹è½½"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerNodeSearch",
                    "funcName": "SerNodeSearch",
                    "keyname": "ser_node_search",
                    "nickname": "SerNodeSearch"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerOnlineReserve",
                    "funcName": "SerOnlineReserve",
                    "keyname": "ser_online_reserve",
                    "nickname": "SerOnlineReserve"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerPpecialService",
                    "funcName": "SerPpecialService",
                    "keyname": "ser_special_service",
                    "nickname": "SerPpecialService"
                },
                {
                    "storetype": "function",
                    "regionId": "developer",
                    "className": "function.developer.SerWeikefu",
                    "funcName": "SerWeikefu",
                    "keyname": "ser_weikefu",
                    "nickname": "SerWeikefu"
                }
            ]
        };
        var wechatTable = $('#wechat-fnConfig-table').mmGrid({
            multiSelect: false,
            indexCol: true,
            checkCol: false,
            height: '420px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        this.btnsClick(wechatTable);
    },
    btnsClick: function (table) {
        var table = table;
        $("#wechat-btn-add").on("click", function () {
            var createHTML = $(".wechat-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-btn-edit").on("click", function () {
            var editHTML = $(".wechat-edit").html();
            parent.Common.popupShow(editHTML);
        });
    }
};
var menuConfig = {
    createDroplist: function () {
        var topOrgDroplist = droplist({
            el: "#wechat-menuConfig-top-droplist",
            data: ["移动互联应用服务管理平台测试服务器", "昆明市住房公积金管理中心", "深圳市住房公积金管理中心", "哈尔滨住房公积金管理中心农垦分中心", "济南住房公积金管理中心", "威海市住房公积金管理中心"]
        });
        return topOrgDroplist;
    },
    createTree: function () {
        var self = this;
        var zTreeObj;
        var setting = {
            callback: {
                onClick: function () {
                    // do something
                }
            }
        };

        var zNodes =[
            { name:"中心资讯", open:true,
                children: [
                    { name:"中心资讯",
                        children: [
                            { name:"中心资讯1"},
                            { name:"中心资讯2"},
                            { name:"中心资讯3"},
                            { name:"中心资讯4"}
                        ]},
                    { name:"办事指南",
                        children: [
                            { name:"常见问题"},
                            { name:"个人提取"},
                            { name:"个人贷款"},
                            { name:"单位存缴"}
                        ]},
                    { name:"账户查询", isParent:true}
                ]},
            { name:"办事指南",
                children: [
                    { name:"办事指南", open:true,
                        children: [
                            { name:"常见问题"},
                            { name:"个人提取"},
                            { name:"个人贷款"},
                            { name:"单位存缴"}
                        ]},
                    { name:"办事指南",
                        children: [
                            { name:"常见问题"},
                            { name:"个人提取"},
                            { name:"个人贷款"},
                            { name:"单位存缴"}
                        ]},
                    { name:"办事指南",
                        children: [
                            { name:"常见问题"},
                            { name:"个人提取"},
                            { name:"个人贷款"},
                            { name:"单位存缴"}
                        ]}
                ]},
            { name:"个人提取", isParent:true}

        ];
        zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
    },
    createAddDroplist: function (data) {
        data = ["链接事件", "点击事件"];
        var addDroplist = droplist({
            el: "#wechat-menuConfig-menuType",
            data: data
        });
        return addDroplist;
    },
    btnClick: function () {
        $("#wechat-btn-add").on("click", function () {
            var createHTML = $(".wechat-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    // post data
                }
            });
        });
    }
};
var policyConfig = {
    createCityList: function () {
        var topOrgDroplist = droplist({
            el: "#wechat-policyConfig-top-droplist",
            data: ["移动互联应用服务管理平台测试服务器", "昆明市住房公积金管理中心", "深圳市住房公积金管理中心", "哈尔滨住房公积金管理中心农垦分中心", "济南住房公积金管理中心", "威海市住房公积金管理中心"]
        });
        return topOrgDroplist;
    },
    createTable: function (data) {
        //wechat-fnConfig-table
        var cols = [
            { title:'KEY', name:'weixinId' ,width:182, align: 'center' },
            { title:'功能描述', name:'msgToken' ,width:386, align: 'center'},
            { title:'包名', name:'msgToken' ,width:198, align: 'center'},
            { title:'是否可用', name:'regionId' ,width:234, align: 'center'}
        ];
        data = {"errcode":0,"errmsg":"","rows":[{"storetype":"channel","weixinId":"gh_a49a7c631bc8","regionId":"00025000","appId":"wx0047d1d3b986df5b","appScret":"953aa070b5a0d60e48e1d40d5faa458d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_eb23d1e67612","regionId":"00031800","appId":"wx4c4606ce40339029","appScret":"d4624c36b6795d1d99dcf0547af5443d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.10/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_69be4ba1c92","regionId":"00045101","appId":"wxaf722c5f3b3d102c","appScret":"9bb5ffe6bc97ca76f333e617c7b8fca1","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.202.206.82/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ad121e41bde6","regionId":"00053100","appId":"wx19d9946ac0643952","appScret":"316afdabdfe88a9d7e1790818397f66d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.7/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_9860dc409841","regionId":"00057400","appId":"wx1087ee5929104279","appScret":"d4624c36b6795d1d99dcf0547af5443d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://myvpn.xicp.cn/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_f9f66d752d3e","regionId":"00063100","appId":"wxc2c772a66352c6df","appScret":"d5c25586eb83e50919fada815b4ef167","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.10/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"testå¾®ä¿¡è´¦å·","regionId":"00075500","appId":"testZH","appScret":"testMY","msgToken":"testToken","msgUrl":"http://testé“¾æŽ¥åœ°å€","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ee9416d578067","regionId":"00076000","appId":"wx50302d635cbb0d14","appScret":"35cbe0b0a2b522f4e9ef66701aa65a37","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.205.146.106/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_7c7aabd2799b","regionId":"00083800","appId":"wx5da71e91f64d4ff3","appScret":"ef32ae88a3e35543ba0b635697dd5809","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://myvpn.xicp.cn/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ad121e41bde6","regionId":"00087100","appId":"wx19d9946ac0643952","appScret":"316afdabdfe88a9d7e1790818397f66d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ee9416d57806","regionId":"00087500","appId":"wx50302d635cbb0d14","appScret":"35cbe0b0a2b522f4e9ef66701aa65a37","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_69be4ba1c924","regionId":"00091200","appId":"wxaf722c5f3b3d102c","appScret":"9bb5ffe6bc97ca76f333e617c7b8fca1","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.202.206.82/weixin/entrance","buf":"","all":0}]};
        var wechatTable = $('#wechat-policyConfig-table').mmGrid({
            checkCol: true,
            multiSelect: true,
            indexCol: true,
            height: '300px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        this.bottomBtnClikc(wechatTable);
    },
    topBtnClick: function () {
        $("#wechat-orgInfo-add").on("click", function () {
            var createHTML = $(".wechat-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-orgInfo-edit").on("click", function () {
            var editHTML = $(".wechat-edit").html();
            parent.Common.popupShow(editHTML);
        });
    },
    bottomBtnClikc: function () {
        $("#wechat-policy-add").on("click", function () {
            var createHTML = $(".wechat-policy-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-policy-edit").on("click", function () {
            var editHTML = $(".wechat-policy-edit").html();
            parent.Common.popupShow(editHTML);
        });
    }
};
var focusInfo = {
    createCityList: function () {
        var topOrgDroplist = droplist({
            el: "#wechat-focusInfo-top-droplist",
            data: ["移动互联应用服务管理平台测试服务器", "昆明市住房公积金管理中心", "深圳市住房公积金管理中心", "哈尔滨住房公积金管理中心农垦分中心", "济南住房公积金管理中心", "威海市住房公积金管理中心"]
        });
        return topOrgDroplist;
    },
    createTable: function (data) {
        //wechat-fnConfig-table
        var cols = [
            { title:'功能名称', name:'storetype' ,width:514, align: 'center' },
            { title:'功能键值', name:'appScret' ,width:526, align: 'center'}
        ];
        data = {"errcode":0,"errmsg":"","rows":[{"storetype":"channel","weixinId":"gh_a49a7c631bc8","regionId":"00025000","appId":"wx0047d1d3b986df5b","appScret":"953aa070b5a0d60e48e1d40d5faa458d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_eb23d1e67612","regionId":"00031800","appId":"wx4c4606ce40339029","appScret":"d4624c36b6795d1d99dcf0547af5443d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.10/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_69be4ba1c92","regionId":"00045101","appId":"wxaf722c5f3b3d102c","appScret":"9bb5ffe6bc97ca76f333e617c7b8fca1","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.202.206.82/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ad121e41bde6","regionId":"00053100","appId":"wx19d9946ac0643952","appScret":"316afdabdfe88a9d7e1790818397f66d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.7/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_9860dc409841","regionId":"00057400","appId":"wx1087ee5929104279","appScret":"d4624c36b6795d1d99dcf0547af5443d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://myvpn.xicp.cn/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_f9f66d752d3e","regionId":"00063100","appId":"wxc2c772a66352c6df","appScret":"d5c25586eb83e50919fada815b4ef167","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.10/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"testå¾®ä¿¡è´¦å·","regionId":"00075500","appId":"testZH","appScret":"testMY","msgToken":"testToken","msgUrl":"http://testé“¾æŽ¥åœ°å€","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ee9416d578067","regionId":"00076000","appId":"wx50302d635cbb0d14","appScret":"35cbe0b0a2b522f4e9ef66701aa65a37","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.205.146.106/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_7c7aabd2799b","regionId":"00083800","appId":"wx5da71e91f64d4ff3","appScret":"ef32ae88a3e35543ba0b635697dd5809","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://myvpn.xicp.cn/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ad121e41bde6","regionId":"00087100","appId":"wx19d9946ac0643952","appScret":"316afdabdfe88a9d7e1790818397f66d","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_ee9416d57806","regionId":"00087500","appId":"wx50302d635cbb0d14","appScret":"35cbe0b0a2b522f4e9ef66701aa65a37","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.207.115.4/weixin/entrance","buf":"","all":0},{"storetype":"channel","weixinId":"gh_69be4ba1c924","regionId":"00091200","appId":"wxaf722c5f3b3d102c","appScret":"9bb5ffe6bc97ca76f333e617c7b8fca1","msgToken":"52a9268562d911e3b135ydpa1zxm2o3a","msgUrl":"http://124.202.206.82/weixin/entrance","buf":"","all":0}]};
        var wechatTable = $('#wechat-focusInfo-table').mmGrid({
            checkCol: true,
            multiSelect: true,
            indexCol: true,
            height: '300px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        this.btnClick(wechatTable);
    },
    btnClick: function (table) {
        var table = table;
        $("#wechat-btn-add").on("click", function  () {
            var createHTML = $(".wechat-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-btn-edit").on("click", function  () {
            var editHTML = $(".wechat-edit").html();
            parent.Common.popupShow(editHTML);
        });
        $("#wechat-btn-del").on("click", function  () {
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    // post data
                }
            });
        });
        $("#wechat-add-sub").on("click", function  () {
            var createHTML = $(".wechat-add-sub").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-view-sub").on("click", function  () {
            var viewHTML = $(".wechat-view-sub").html();
            parent.Common.popupShow(viewHTML);
        });
    }
};
var focusInfoConfig = {
    createVue: function () {
        var focusInfoVue = new Vue({
            el: "#wechat-focusInfoConfig-Vue",
            data: {
                title: "",
                welcomeWord: "",
                startIndex: 1,
                items: [
                    {
                        isShow: false,
                        value: "业务咨询"
                    },
                    {
                        isShow: false,
                        value: "网点查询"
                    },
                    {
                        isShow: false,
                        value: "楼盘查询"
                    },
                    {
                        isShow: false,
                        value: "贷款试算"
                    },
                    {
                        isShow: false,
                        value: "新闻资讯"
                    },
                    {
                        isShow: false,
                        value: "APP下载"
                    },
                    {
                        isShow: false,
                        value: "常见问题"
                    },
                    {
                        isShow: false,
                        value: "微信与APP使用指南"
                    }
                ]
            },
            computed: {
                checked: function () {
                    var itemsArr = this.items,
                        temp = [];
                    for(var i = 0; i<itemsArr.length; i++) {
                        if(itemsArr[i].isShow) {
                            temp.push(itemsArr[i]);
                        }
                    }
                    return temp;
                }
            }
        });
        return focusInfoVue;
    }
};
$(document).ready(function(){
    if($("#wechat-baseQuery-top-droplist").length > 0) {
        // base info query
        baseInfoQuery.createDroplist();
        baseInfoQuery.createTable();
    } else if ($("#wechat-fnConfig-table").length > 0){
        // function configuration
        fnConfig.createTable();
    } else if ($("#wechat-menuConfig-top-droplist").length > 0) {
        menuConfig.createDroplist();
        menuConfig.createTree();
        menuConfig.createAddDroplist();
        menuConfig.btnClick();
    } else if ($("#wechat-policyConfig-top-droplist").length > 0) {
        policyConfig.createCityList();
        policyConfig.topBtnClick();
        $(".wechat-policyConfig-top a").on("click", function () {
            $(".wechat-policyConfig-box").show();
            policyConfig.createTable();
        });
    } else if ($("#wechat-focusInfo-top-droplist").length > 0) {
        focusInfo.createCityList();
        focusInfo.createTable();
    } else if($("#wechat-focusInfoConfig-Vue").length > 0) {
        focusInfoConfig.createVue();
    }
    getCenter();
});

function getCenter(){
    $.ajax({
        type:'POST',
        url:'./webappcomCenterId.json',
        data:{},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            centeridData = data;
            if(data.recode == '000000'){
                var centerInfo = '<option value="">请选择</option>';
                for(var i = 0;i<data.mi001list.length;i++){
                    if(user.centerid != '00000000'){
                        if(user.centerid == data.mi001list[i].centerid){
                            centerInfo = '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>'
                        }
                    } else {
                        centerInfo += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                    }
                    centeridObj.push(data.mi001list[i]);
                }
                $('#center').html(centerInfo);
                
            }
            
        },
        error:function(){
            errMsg();
        }
    });
}