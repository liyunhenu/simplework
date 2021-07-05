package com.juejin.bloomfilter.redis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class Test {


    public static void main(String[] args) {
        BloomFilter<String> bloomFilter=BloomFilter.create(new Funnel<String>() {
            @Override
            public void funnel(String from, PrimitiveSink into) {
                into.putString(from, Charsets.UTF_8);
            }
        }, 100_0000, 0.000_0001);
        String testElement1=new String("test1");
        String testElement2=new String("test2");
        String testElement3=new String("test3");
        bloomFilter.put(testElement1);
        bloomFilter.put(testElement2);
        bloomFilter.put(testElement3);
        System.out.println(bloomFilter.mightContain(testElement1));
        System.out.println(bloomFilter.mightContain(testElement2));
        System.out.println(bloomFilter.mightContain(testElement3));
        System.out.println(bloomFilter.mightContain("test"));
    }
}
