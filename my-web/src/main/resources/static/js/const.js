var PAYWAY_CASH=0,PAYWAY_WECHAT=1,PAYWAY_ALIPAY=2,PAYWAY_BANK=3,PAYWAY_SCAN=4,MEDICARE_CARD=5,PAYWAY_CARD=8,PAYWAY_BILL=9;
var PAYWAYS=[{"name":"现金","value":PAYWAY_CASH},{"name":"微信","value":PAYWAY_WECHAT},{"name":"支付宝","value":PAYWAY_ALIPAY},{"name":"银联卡","value":PAYWAY_BANK},{"name":"医保卡","value":MEDICARE_CARD},{"name":"储值卡","value":PAYWAY_CARD},{"name":"挂账","value":PAYWAY_BILL}];
var SCANPAYWAYS=[{"name":"现金","value":PAYWAY_CASH},{"name":"微信","value":PAYWAY_WECHAT},{"name":"支付宝","value":PAYWAY_ALIPAY},{"name":"银联卡","value":PAYWAY_BANK},{"name":"医保卡","value":MEDICARE_CARD},{"name":"储值卡","value":PAYWAY_CARD},{"name":"挂账","value":PAYWAY_BILL},{"name":"扫码支付","value":PAYWAY_SCAN}];

/* 调用状态码 */
var CONST_SUCCESS_OK = 1, CONST_SUCCESS_FAIL = 0;

/* 正则：手机号码 */
var RE_PHONE = /^[1][3,4,5,7,8,9][0-9]{9}$/;
/* 正则：电话 */
var RE_TEL = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;

