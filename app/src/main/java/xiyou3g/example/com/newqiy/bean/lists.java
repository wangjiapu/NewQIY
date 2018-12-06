package xiyou3g.example.com.newqiy.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒲家旺 on 2017/6/17.
 */

public class lists {
    //轮播图数据
    public static List<RollData> rList = new ArrayList<>();

    //推荐页数据
    public static List<ClassifyData> cList = new ArrayList<>();

    //电影页数据
    public static List<MovieData> MVlist = new ArrayList<>();

    //电视剧页数据
    public static List<TVData> TVlist = new ArrayList<>();

    //动漫页数据
    public static List<DongmanData> Dmlist = new ArrayList<>();

    //综艺页数据
    public static List<ZhongyiData> ZYlist = new ArrayList<>();

    //纪录片页数据
    public static List<DocumentaryData> DDlist = new ArrayList<>();

    //纪录片页获取数据接口
    public static DocumentaryData getDDlist(int x){
        for (int i = 0; i < DDlist.size() ; i++) {
            if (DDlist.get(i).getID().equals(Integer.toString(x))) {
                return DDlist.get(i);
            }
        }
        return null; }

    //综艺页获取数据接口
    public static ZhongyiData getZYlist(int x){
        for (int i = 0; i < ZYlist.size() ; i++) {
            if (ZYlist.get(i).getID().equals(Integer.toString(x))) {
                return ZYlist.get(i);
            }
        }
        return null; }

    //获取已经得到数据的选项页
    public static ClassifyData getCData(int x){
        for (int i = 0; i < cList.size() ; i++) {
            if (cList.get(i).getID().equals(Integer.toString(x))) {
                return cList.get(i);
            }
        }
        return null; }

    //电影页获取数据接口
    public static MovieData getMVlist(int x){
        for (int i = 0; i < MVlist.size() ; i++) {
            if (MVlist.get(i).getID().equals(Integer.toString(x))) {
                return MVlist.get(i);
            }
        }
        return null; }

    //动漫页获取数据接口
    public static DongmanData getDmlist(int x){
        for (int i = 0; i < Dmlist.size() ; i++) {
            if (Dmlist.get(i).getID().equals(Integer.toString(x))) {
                return Dmlist.get(i);
            }
        }
        return null; }

    //电视剧页获取数据接口
    public static TVData getTVlist(int x){
        for (int i = 0; i < TVlist.size() ; i++) {
            if (TVlist.get(i).getID().equals(Integer.toString(x))) {
                return TVlist.get(i);
            }
        }
        return null; }

    //轮播图获取数据接口
    public static List<RollData> getmList() {
        return rList;
    }
}
