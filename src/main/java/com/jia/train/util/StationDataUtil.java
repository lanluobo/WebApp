package com.jia.train.util;

import com.jia.train.po.StationNameId;
import org.jsoup.helper.DataUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiaxl on 2016/12/31.
 */
public class StationDataUtil {

    public static List<StationNameId>list=new ArrayList<StationNameId>();

    static {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void init() throws Exception {
        BufferedReader br= new BufferedReader(new InputStreamReader(StationDataUtil.class.getResourceAsStream("/stations.dat")));
        try {
            String str=br.readLine();
            Pattern p=Pattern.compile("@.*?\\d+");
            Matcher m=p.matcher(str);
            String []sub;
            StationNameId station;
            while (m.find()){
                sub=m.group().split("\\|");
                station=new StationNameId();
                station.setAbbr(sub[4]);
                station.setCnName(sub[1]);
                station.setId(sub[2]);
                station.setUsName(sub[3]);
                station.setOrder(Integer.parseInt(sub[5]));
                list.add(station);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("stations.dat文件未找到...");
        }
    }

    public static Vector<StationNameId>getStationByAbbrOrUsName(String abbr){

        List<StationNameId>data=new ArrayList<StationNameId>();
        List<StationNameId>data0=new ArrayList<StationNameId>();
        for (StationNameId s:list){
            if(s.getAbbr().startsWith(abbr)){
                data.add(s);
            }else if(s.getUsName().startsWith(abbr)){
                data0.add(s);
            }
        }
        data.addAll(data0);
        return new Vector<StationNameId>(data);
    }
    public static Vector<StationNameId>getStationByCnName(String cnName){

        List<StationNameId>data=new ArrayList<StationNameId>();
        for (StationNameId s:list){
            if(s.getCnName().startsWith(cnName)){
                data.add(s);
            }
        }
        return new Vector<StationNameId>(data);
    }
    public static void main(String[] args) throws Exception {
        System.out.println();
        List<StationNameId>list=getStationByAbbrOrUsName("");

        HashSet<String>set=new HashSet<String>();
        for (StationNameId s:list){
            set.add(s.getCnName());
        }
    }
}
