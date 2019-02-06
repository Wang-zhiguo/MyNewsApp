package cn.wangzg.mynewsapp.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.wangzg.mynewsapp.Title;

/**
 * Time: 2019/2/2
 * Author: wangzhiguo
 * Description: Json字符串的解析
 */
public class JsonUtil {
    /**
     * {"status":"0",
     * "msg":"ok",
     * "result":
     * {"channel":"头条","num":"10",
     * "list":[
     * {"title":"“星际时差”和性格冲突是人类定居火星的最大障碍",
     * "time":"2019-02-02 08:23:00",
     * "src":"新浪科技","category":"tech",
     * "pic":"http:\/\/n.sinaimg.cn\/tech\/transform\/667\/w400h267\/20190202\/b24c-hsmkfyn5772318.jpg",
     * "content":"<p class=\"art_t\">新浪科技讯 北京时间2月2日消息，据国外媒体报道，目前专家表示，个性化室内设计、星际旅行
     * “时差反应”以及性格冲突，是阻碍人类未来定居火星的重要因素。<\/p>\n<p class=\"art_t\">近日，专家在英国伦敦召开研讨会指出，
     * 人类抵达火星的技术挑战很大，并且要安全地将宇航员返送至地球。同时，还要考虑到社会因素和心理障碍。<\/p>\n<p class=\"art_t\">
     *     研讨会上提出的实例包括：一个偏远基地的油漆颜色引发宇航员们的争吵，以及一半以上的宇航员在太空任务中无法“和睦相处”。<\/p>\n
     *     <p class=\"art_t\">这项研究活动是由穆罕默德·本·拉希德（Mohammed bin Rashid）全球空间挑战组织提供的资金，研讨会旨在
     *     如何实现人类在地外星球上定居生活。<\/p>\n<div class=\"img_wrapper\"><img class=\"j_art_lazy\"
     *     src=\"https:\/\/n.sinaimg.cn\/tech\/transform\/283\/w630h453\/20190202\/BgRJ-hsmkfyn5768675.jpg\"
     *     alt=\"抵达火星并将宇航员返送地球所面临的技术挑战是巨大的，但专家在伦敦召开的研讨会表示，还需要考虑到社会因素和心理障碍。\"
     *     data-link=\"\"><span class=\"img_descr\">　　抵达火星并将宇航员返送地球所面临的技术挑战是巨大的，但专家在伦敦召开的研
     *     讨会表示，还需要考虑到社会因素和心理障碍。<\/span><\/div>\n<p class=\"art_t\">会议组织者、埃克塞特大学的弗德里科·卡普
     *     罗蒂（Federico Caprotti）博士称，在火星上定居的最大障碍不是技术，而是心理因素。远程任务提出了当前空间科学知识无法回答的心理
     *     问题。例如：国际空间站能够让宇航员快速返回地球，因此能够让宇航员在心理感觉上更接近地球。火星很难实现人类定居，这将带来巨大的风险，
     *     同时，宇航员将面临着“星际时差”的困扰。<\/p>\n<p class=\"art_t\">火星旅行大概需要400天时间，尽管实验等离子发动机可以加速这
     *     一过程。长途太空旅行可能造成巨大的心理影响，由于与地球缺乏实时通信，如此长的太空旅程，信号传输需要4-24分钟。<\/p>\n
     *     <p class=\"art_t\">任务前的心理测试用于挑选机组人员，但这并非总能有效地决定个人是否可以很好地协同工作。研讨会上一位专家曾涉
     *     及多样性太空任务，他表示尽管宇航员在任务前期进行过测试，但是40-50%的宇航员一旦进入太空环境就彼此无法相处。<\/p>\n
     *     <div class=\"img_wrapper\"><img class=\"j_art_lazy\"   src=\"https:\/\/n.sinaimg.cn\/tech\/transform\/302\/w630h472\/20190202\/DxdG-hsmkfyn5769403.jpg\"   alt=\"目前在月球轨道上执行太空任务的细节已被公开，这将成为2030年火星登陆时间表一部分。\" data-link=\"\"><span class=\"img_descr\">目前在月球轨道上执行太空任务的细节已被公开，这将成为2030年火星登陆时间表一部分。<\/span><\/div>\n<p class=\"art_t\">埃克塞特大学史蒂文·帕尔默（Steven Palmer）博士称，这将是400天返程火星之旅和火星任务所面临的一个重要问题。据悉，帕尔默曾参与太空研究项目和南极洲试验。<\/p>\n<p class=\"art_t\">他说：“我们还听说科学家在地球一处偏远地区进行了特殊任务，有人用其他人不喜欢的颜色粉刷了墙壁，这引起了研究成员之间的怨恨，破坏了团队凝聚力。很多人认为火星任务应当由‘自然领导者’来完成，但是英国南极科考队组织发现，事实上更需要能够妥协的人。”这次研究会汇聚了来自天体物理学、地理学、创新和伦理
     */

    public static ArrayList<Title> getNewsList(String strJson){
        ArrayList<Title> list = null;
        Title title = null;
        try {
            JSONObject object = new JSONObject(strJson);
            String msg = object.optString("msg");
            if("ok".equals(msg)){
                list = new ArrayList<>();
                JSONArray array = object.optJSONObject("result").optJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.optJSONObject(i);
                    title = new Title(obj.optString("title"),obj.optString("src"),
                            obj.optString("pic"),obj.optString("url"));
                    list.add(title);
                    title = null;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println(list);
        return list;
    }

    /**
     * 使用GSON进行解析
     * @param strJson
     * @return
     */
    public static ArrayList<NewsBean.ResultBean.ListBean> getNewsListByGson(String strJson) {
        ArrayList<Title> list = null;
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(strJson,NewsBean.class);
        if(newsBean.getMsg().equals("ok")){
            /**
             * 其实，在这里直接返回newsBean.getResult().getList()即可，因为这里面包含最全的信息
             * 把返回值的类型修改了即可。
             */
            return (ArrayList<NewsBean.ResultBean.ListBean>) newsBean.getResult().getList();
//            list = new ArrayList<>();
//            for(NewsBean.ResultBean.ListBean bean:newsBean.getResult().getList()){
//                Title title = new Title(bean.getTitle(),bean.getSrc(),bean.getPic(),bean.getUrl());
//                list.add(title);
//            }
        }
        return null;
    }
}
