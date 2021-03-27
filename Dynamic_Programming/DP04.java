public class DP04 {
    //Leetcode 516
    public int longestPalindromeSubseq(String s,int i, int j, int[][] dp) {
        if(i >= j)
            return dp[i][j] = (i == j) ? 1 : 0;
        
        if(dp[i][j] != -1)
            return dp[i][j];
        
        if(s.charAt(i) == s.charAt(j))
            return dp[i][j] = longestPalindromeSubseq(s, i + 1, j - 1, dp) + 2;
        
        int x = longestPalindromeSubseq(s, i + 1, j, dp);
        int y = longestPalindromeSubseq(s, i, j - 1, dp);
        
        return dp[i][j] = Math.max(x, y);
    }
    
    public int longestPalindromeSubseq_DP(String s,int I, int J, int[][] dp) {
        
        int n = s.length();
        for(int gap = 0; gap < n ; gap++){
            for(int i = 0, j = gap; j < n; i++, j++){
                if(i == j){
                    dp[i][j] = 1;
                    continue;
                }
                
                if(s.charAt(i) == s.charAt(j))
                    dp[i][j] = dp[i+1][j-1] + 2;
                else
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        
        return dp[I][J];
    }
    
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        //for (int[] d : dp) Arrays.fill(d, -1);
        return longestPalindromeSubseq_DP(s, 0, n - 1, dp);
    }

    //Leetcode 115
    public int numDistinct_memo(String s, String t, int n, int m, int[][] dp) {
        if (m == 0) {
            return dp[n][m] = 1;
        }

        if (n < m) {
            return dp[n][m] = 0;
        }

        if (dp[n][m] != -1)
            return dp[n][m];

        if (s.charAt(n - 1) == t.charAt(m - 1)) {
            dp[n][m] = numDistinct_memo(s, t, n - 1, m - 1, dp) + numDistinct_memo(s, t, n - 1, m, dp);
        } else {
            dp[n][m] = numDistinct_memo(s, t, n - 1, m, dp);
        }

        return dp[n][m];
    }

    public int numDistinct_DP(String s, String t, int N, int M, int[][] dp) {

        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (m == 0) {
                    dp[n][m] = 1;
                    continue;
                }

                // if (n < m) {
                // return dp[n][m] = 0;
                // }

                if (s.charAt(n - 1) == t.charAt(m - 1)) {
                    dp[n][m] = dp[n - 1][m - 1] + dp[n - 1][m];
                } else {
                    dp[n][m] = dp[n - 1][m];
                }

                return dp[n][m];
            }
        }

        return dp[N][M];
    }

    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();

        int[][] dp = new int[n + 1][m + 1];
        // for (int[] d : dp)
        // Arrays.fill(d, -1);

        int ans = numDistinct_memo(s, t, n, m, dp);
        for (int[] d : dp) {
            for (int e : d) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
        return ans;
    }
    
}
