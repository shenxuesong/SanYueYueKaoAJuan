package com.example.shenxuesong.sanyueyuekaoajuan.FGT;

import java.util.List;

/**
 * Created by shenxuesong on 2017/9/20.
 */

public class JavaBean1 {

    /**
     * channel : [{"channel_me":"推荐","channel_more":"财经"},{"channel_me":"热点","channel_more":"大众"},{"channel_me":"汽车","channel_more":"房产"},{"channel_me":"理财","channel_more":"旅游"},{"channel_me":"去哪","channel_more":"出国"},{"channel_me":"大话","channel_more":"西游"},{"channel_me":"情感","channel_more":"美女"},{"channel_me":"城市","channel_more":"数码"},{"channel_me":"新闻","channel_more":"游戏"},{"channel_me":"体育","channel_more":"娱乐"}]
     * code : 200
     */

    private int code;
    private List<ChannelBean> channel;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ChannelBean> getChannel() {
        return channel;
    }

    public void setChannel(List<ChannelBean> channel) {
        this.channel = channel;
    }

    public static class ChannelBean {
        /**
         * channel_me : 推荐
         * channel_more : 财经
         */

        private String channel_me;
        private String channel_more;

        public String getChannel_me() {
            return channel_me;
        }

        public void setChannel_me(String channel_me) {
            this.channel_me = channel_me;
        }

        public String getChannel_more() {
            return channel_more;
        }

        public void setChannel_more(String channel_more) {
            this.channel_more = channel_more;
        }
    }
}
