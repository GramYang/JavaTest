package org.gram.algorithms.dynamicProgram;
//动态规划，这里的动态规划题出自《算法导论》
public class DP {
    //最长公共子序列
    public void dp1(){
        String s1="abcdbab";
        String s2="bdcaba";
        show(getSubstringMatrix(s1,s2));
    }

    private static int[][] getSubstringMatrix(String x, String y) {
        int xLen = x.length() + 1; // 加1是因为初始化第一个为0
        int yLen = y.length() + 1;

        int rLen = Math.max(xLen, yLen); // 大的串置为行
        int cLen = Math.min(xLen, yLen); // 小的串置为列
        int[][] c = new int[rLen][cLen]; // 矩阵c保存状态
        for (int i = 1; i < rLen; i++) {
            for (int j = 1; j < cLen; j++) {
                // 不相等，选取较大的
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    // 相等，由斜对角线+1
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
                }
            }
        }
        return c; // 长度矩阵
    }

    private static void show(int[][] b) {
        for (int[] ints : b) {
            for (int p = 0; p < ints.length; p++) {
                System.out.print(ints[p] + "\t");
                if (p == ints.length - 1) {
                    System.out.println();
                }
            }
        }
    }
}
