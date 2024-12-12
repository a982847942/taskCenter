package edu.nuaa.loadBalanceAlgorithm;

import java.util.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/5 19:27
 */
public class WeightRandom {
    static class ServerManager {
        public volatile static Map<String, Integer> serverMap = new TreeMap<>();

        static {
            serverMap.put("192.168.1.1", 1);
            serverMap.put("192.168.1.2", 2);
            serverMap.put("192.168.1.3", 3);
            serverMap.put("192.168.1.4", 4);
        }
    }

    public static String getServer() {
        ArrayList<String> serverList = new ArrayList<>();
        Set<String> serverSet = ServerManager.serverMap.keySet();
        Iterator<String> iterator = serverSet.iterator();

        Integer weightSum = 0;
        while(iterator.hasNext()){
            String server = iterator.next();
            Integer weight = ServerManager.serverMap.get(server);
            weightSum += weight;
            for (int i = 0; i < weight; i++) {
                serverList.add(server);
            }
        }

        Random random = new Random();
        String server = serverList.get(random.nextInt(weightSum));
        return server;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String server = getServer();
            System.out.println(server);
        }
    }
}
