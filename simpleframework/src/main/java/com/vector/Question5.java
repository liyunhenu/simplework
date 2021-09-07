package com.vector;




import java.util.Scanner;

/*
* 每行数据代表一条机构信息，依次是机构号、机构层级（0：总行，1：分行，2：支行）、上级机构号、机构名称、状态（0-正常，1-已撤并），机构撤并只存在平级撤并，即分行撤并到分行，支行撤并到支行，顶级机构不会被撤并，一级分行撤并，被另一个一级分行接收，支行撤并，被另一个支行接收，接收支行可以是同一个分行的支行，也有可能是不同分行的支行，层级最多3级，总行，分行，支行。分行撤并后，分行下的支行如果没有被撤并，则变为接收行的下级支行，名字前缀并为对应的分行，后缀不变，如果被撤并则并入相应的支行。给定机构层级信息，以及被撤并机构号，接收机构号，输出撤并后的机构层级信息。
输入信息第一行是数字a，接下来a行表示a个机构信息，紧接着1行是撤并机构号以及接收结构号，每次只撤并1个机构
最后以 -1 为一行结束
*
* 输入
* 10
0001 0 无 总行清算中心 0
1000 1 0001 一分行 0
1001 2 1000 一分行一支行 0
1002 2 1000 一分行二支行 0
2000 1 0001 二分行 0
2001 2 2000 二分行三支行 0
2002 2 2000 二分行四支行 0
3000 1 0001 三分行 0
3001 2 3000 三分行五支行 0
3002 2 3000 三分行六支行 0
2000 1000
-1
*
* 0001 0 无 总行清算中心 0
1000 1 0001 一分行 0
1001 2 1000 一分行一支行 0
1002 2 1000 一分行二支行 0
2000 1 0001 二分行 1
2001 2 1000 一分行三支行 0
2002 2 1000 一分行四支行 0
3000 1 0001 三分行 0
3001 2 3000 三分行五支行 0
3002 2 3000 三分行六支行 0
* */
public class Question5 {

    public static void main(String args[]) {
        Scanner cin = new Scanner(System.in);
        int num;
        num = Integer.parseInt(cin.nextLine().trim());//多少行
        String[] origins = new String[num];
        String destroyId;
        String detinationId;
        while (num-- > 0) {
            origins[num] = cin.nextLine().trim();
        }
        String[] tmp = cin.nextLine().trim().split(" ");
        destroyId = tmp[0];
        detinationId = tmp[1];
        String detroyString = null;
        String detinationString = null;
        for (String origin : origins) {
            String originId = origin.substring(0, 4);
            if (originId.equals(detinationId)) {
                //查找出目的
                detinationString = origin;
            } else if (originId.equals(destroyId)) {
                //查找合并的
                detroyString = origin;
            }
        }
        String[] tmp1 = detroyString.split(" ");
        String[] tmp2 = detinationString.split(" ");
        if ("1".equals(tmp1[1])) {
            String parentId = tmp2[0];//目的支行的机构号
            String parentName = tmp2[3];//目的支行的名字
            tmp1[4] = "1";//已合并
            for (int i = 0; i < origins.length; i++) {
                String originId = origins[i].substring(0, 4);
                String parent = origins[i].substring(7, 11);
                if (originId.equals(destroyId)) {
                    origins[i] = tmp1[0] + " " + tmp1[1] + " " + tmp1[2] + " " + tmp1[3] + " " + "1";
                }
                if (parent.equals(destroyId)) {
                    origins[i] = replaceParent(origins[i], parentId, parentName);
                }
            }
        } else {
            for (int i = 0; i < origins.length; i++) {
                String originId = origins[i].substring(0, 4);
                if (originId.equals(destroyId)) {
                    origins[i] = tmp1[0] + " " + tmp1[1] + " " + tmp1[2] + " " + tmp1[3] + " " + "1";
                }
            }
        }

        for (int i = origins.length - 1; i >= 0; i--) {
            System.out.println(origins[i]);
        }
    }

    private static String replaceParent(String origin, String parentId, String parentName) {
        String[] tmp = origin.split(" ");
        String Name = parentName + tmp[3].substring(3);
        return tmp[0] + " " + tmp[1] + " " + parentId + " " + Name + " " + tmp[4];
    }


}


