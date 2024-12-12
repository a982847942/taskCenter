package edu.nuaa.loadBalanceAlgorithm;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/5 19:20
 */
public class WeightRR {
    private static List<Node> serverList;

    WeightRR(List<Node> serverList) {
        WeightRR.serverList = serverList;
    }

    /**
     * 计算 totalWeight
     * 开始时计算全部节点的 currentWeight = currentWeight + weight
     * 选中 currentWeight 最大的节点，并设置 currentWeight = currentWeight - totalWeight
     * https://www.cnblogs.com/handsometaoa/p/17919889.html
     * @return
     */
    private String select() {
        if (CollectionUtils.isEmpty(serverList)) {
            throw new RuntimeException("service node is empty");
        }
        int totalWeight = 0;
        for (Node node : serverList) {
            totalWeight = totalWeight + node.getWeight();
            node.setCurrentWeight(node.getCurrentWeight() + node.getWeight());
        }
        System.out.println(Arrays.toString(serverList.toArray()));
        Node currentWeightMaxNode = Collections.max(serverList);
        currentWeightMaxNode.setCurrentWeight(currentWeightMaxNode.getCurrentWeight() - totalWeight);
        return currentWeightMaxNode.getIp();
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        Node node1 = new Node("192.168.0.1", 4);
        Node node2 = new Node("192.168.0.2", 2);
        Node node3 = new Node("192.168.0.3", 1);
        List<Node> serverList = Arrays.asList(node1, node2, node3);
        WeightRR weightedRoundRobin = new WeightRR(serverList);
        for (int i = 0; i < 14; i++) {
            String select = weightedRoundRobin.select();
            map.put(select, map.getOrDefault(select, 0) + 1);
        }
        System.out.println(map);
    }
}
class Node implements Comparable<Node> {
    // 服务器IP
    private String ip;
    // 固定权重
    private int weight;
    // 当前权重
    private int currentWeight;

    public Node(String ip, int weight) {
        this.ip = ip;
        this.weight = weight;
        this.currentWeight = 0;

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    @Override
    public int compareTo(Node node) {
        return this.getCurrentWeight() - node.getCurrentWeight();
    }
}
