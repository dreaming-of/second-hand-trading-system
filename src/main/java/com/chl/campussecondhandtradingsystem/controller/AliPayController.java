package com.chl.campussecondhandtradingsystem.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Controller
public class AliPayController {
    private final String APP_ID = "2021000117660795";
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXF1dNEKns3b9m4EcfrYwXhZ3Jf9JPRSVg+OeEVmqzTrO6NNvCBbexlkXWL1QzLTrKQgQcrWDNdRRYJI4mLioXn5WRWBvS0c6GD8YMzVRR9SaOon/OU/yG3Y3PgZBWHdwpW0MKJxhTN7jXl8ruA3d/O7R2xUnepjvaY4rWsU8ERgAdz5m+XbQtTPrHvVqYXjY0HevWJg/TmcLjEsA4VIS/2JeJD8ys6MdySJE37ttHfLMc71BxCWYSQ7PtA0K/TdQiVhVjv794vOiT4KPpYzD72ZKlJqo5eA9R6JEQLYiXnmkqbWkTB+uDPKlD7jIWQ5iWTlDaMhNBm/IEY5rNEQC7AgMBAAECggEBAIgBuNJkd7IDArKmnR+E56Cc5KZn63bz9WvPYwibib0S0XKC9nc3El5HuWHOhOr0ggFjX7Q41fTcuY2GincQA0/0lXyaZaFmv5OqS0tm4cpM9YJcANRIgf8dHPR32ufKRU7tGfHfs2PluvxHZaLPeBgONKrdeVt1JFe4V1hfj5N11X4gLflqrSx4Fl6J3ltzV4mKQhsWzdG/ubzu/3e7GWfKixiBOdTlCYHm3uCRTlJsHVZ9eO1nsHPDgtTYDNSMuEys6v9iV9XUuEKW8KDSuBwQzDVImSr6qaiuwNQMEN7l1t7bd6kVb/5Wo4MDVGujmh0sDH3GHHJoCLxqfU1mCrECgYEAzCeNWgRSG8ouE+e7I2Y2RD7rnmDwORO9MPpwo0dVwPzQKDx4k1n2kXDB3ILPg7efpWydsr/opXt93EYFB1HWAmH8hIviKssBpYMXEofcXShr6APQ7N9R8VhgsQe/Y36U00uvCBfvWfXLk9K+MS55zT1BUXmRJPM8Z7EFDjJMgA8CgYEAvXYMLeS5nL9n9ydh6dOyXlXe/u846ECWmDlcV5BYVYuXujXgdF/W0ZrfAoHUYzx3bxJMq+vhjbUNsSbCYCL0EhuPLSyMRaO9Xtu2BJFS7IOJIA3/0S0tA5XPGsSVdMP0orzgmOeq/lEah758gpJn2I2/fzWANe4xLoBhlorWCJUCgYBz4PNjp9YvPeg/liXrpuWggRJeWGmiDCkz9sMeeqwbGaTRYdmS0dqZnSX8kydUfVKZo/gy8KkN3PWIpRnPmMzfl1yso3Whko8BslHTS9hx7DKrhBb/jJbHZcGkWmBxCfVRpcDY2DUcm96Mrm8UHWw3adB8fYM8NPARbzIOZ9ByUwKBgC2W7FNWyHs5a7EDXwI/GJqumxxJQC5S7Gnw05GWgEsFbhs2v64BKdYCKEuYJh1C/ZJSmvR7rCjHOmY4Z2luv8BjtU4vfAl93yGmSORMe+HBvQtjkv0om75THCTazQPt6z4FqR3iETllFrc3VQquZCKt6TiZDSLtEBSae+EXw20JAoGBAJ1OHP8A/fondaSL+soDB/pbzXzxIGtCQWLZAy93lmLqWPEFAM+c9m/qWEUvnfhMeA2d96Yb34SRcJ+f63fQSKeA3rl3dIFvFVYoQbzIhxthGgP8S6xI3vZhs/logJX4syWdSwOL1C9LrlGGx/LQD0/hN6Y16cb3nqX71gKJrQIE";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAojx7TQ6EurP38zkqBjibOaEYXKHPJqeyThqxOAXtldwYJkaI9YL1jT8e2B1hthuFvXlh2QJBpKXwNfTLJ386sdSDUNsbVesZVqm+pkX0W6rQv+pKDt2euI92emfIPPoj0HSaflaeN3JWLje33disPuqJNzBl6uhKV1LkhDSF19QfW2mHdDihf22PSlA8WDyh1Fn1+AWpzNgwHfkbk3C8p4yRE6PJ55sK0FDYV0YkfX/db7QFUk9Drd/u3JERKoldRNa89Jlt6wWC6567OriUX9jVapPxN9DS/fgO9w1BSzOtsskVXoL9iwIpMWw8mlcNeBVv89LftbP1qqoWzxDjvwIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://127.0.0.1:8080/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址 /order/update/+${orderVo.order.order_id}+/1
    private  String RETURN_URL = "http://127.0.0.1:8080/order/update/";

    @RequestMapping("/alipay/{order_id}/{total}")
    public void alipay(HttpServletResponse httpResponse, @PathVariable("order_id") String order_id,
                       @PathVariable("total") String total) throws IOException {

        Random r = new Random();
        //实例化客户端,填入所需参数
        RETURN_URL += order_id + "/1";
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order_id;
        //付款金额，必填
        String total_amount = total;
        //订单名称，必填
        String subject = "1";
        //商品描述，可空
        String body = "";
        request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
}