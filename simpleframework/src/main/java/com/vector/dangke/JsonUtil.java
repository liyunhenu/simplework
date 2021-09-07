package com.vector.dangke;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.*;

public class JsonUtil {

    //json转对象
    public static void main(String[] args){
        String path = JsonUtil.class.getClassLoader().getResource("randomList.json").getPath();
        String s = JsonUtil.readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        Random random=new Random();
        random.nextInt();

        //System.out.println("data"+jobj.get("data"));
        JSONArray data=jobj.getJSONArray("data");
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("{");
        String[] options={"A","B","C","D"};
        for (int i = 0; i < data.size(); i++) {
            JSONObject dataElement=(JSONObject) data.get(i);
            JSONObject answerData=dataElement.getJSONObject("answerData");
            JSONArray optn=answerData.getJSONArray("optn");
            for (int j = 0; j < optn.size(); j++) {
                JSONObject scoreData=(JSONObject) optn.get(j);
                /*System.out.println(scoreData.get("score"));
                System.out.println("1".equals(ObjectUtils.getDisplayString(scoreData.get("score"))));*/
                if("1".equals(ObjectUtils.getDisplayString(scoreData.get("score")))){
                    stringBuilder.append((i+1)+"、"+options[j]+"，   ");
                }

            }
            if((i+1)%10==0){
                stringBuilder.append("}\n\n{");
            }
        }
        System.out.println("answer:\n"+stringBuilder.toString());

        /*JSONObject address1 = jobj.getJSONObject("address");
        String street = (String) address1.get("street");
        String city = (String) address1.get("city");
        String country = (String) address1.get("country");

        System.out.println("street :" + street);
        System.out.println("city :" + city);
        System.out.println("country :" + country);

        JSONArray links = jobj.getJSONArray("links");

        for (int i = 0 ; i < links.size();i++){
            JSONObject key1 = (JSONObject)links.get(i);
            String name = (String)key1.get("name");
            String url = (String)key1.get("url");
            System.out.println(name);
            System.out.println(url);
        }*/
    }

    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
