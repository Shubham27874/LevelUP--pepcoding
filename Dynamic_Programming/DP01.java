import java.util.*;

public class DP01 {
    public static void print1D(int[] arr){
        for(int ele : arr){
            System.out.print(ele + " ");
        }
        System.out.println();
    }

    public static void print2D(int[][] arr){
        for(int[] row : arr){
            print1D(row);
        }
    }

    public static int fib_normal(int n){
        if(n <= 1) return n;
        int ans = fib_normal(n - 1) + fib_normal(n - 2);
        return ans;
    }

    public static int fib_memo(int n, int[] dp){
        if(n <= 1){
            return dp[n] = n;
        }

        if(dp[n] != -1)
            return dp[n];

        int ans = fib_memo(n - 1, dp) + fib_memo(n - 2, dp);
        return dp[n] = ans;
    }

    public static int fib_DP_tabulation(int n, int[] dp){
        for(int i = 0; i < N; i++){
            if(i <= 1){
                dp[i] = i;
                continue;
            }

            int ans = dp[i - 1] + dp[i - 2];

            dp[i] = ans;
        }

        return dp[n];
    }

    public static int fib_twoPointer(int n){
        int a = 0, b = 1;
        for(int i = 0; i < n; i++){
            //System.err.println(a + " ");

            int sum = a + b;
            a = b;
            b = sum;
        }

        return a;
    }

    public static void fibonacci(){
        int n = 9;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        System.out.println(fib_memo(n, dp));
        System.out.println(fib_DP_tabulation(n, dp));
        System.out.println(fib_twoPointer(n));

        print1D(dp);
    }

    public static int mazePath_normal(int sr, int sc, int er, int ec){
        if(sr == er && sc == ec){
            return 1;
        }

        int count = 0;

        if(sc + 1 <= ec)
            count += mazePath_normal(sr, sc + 1, er, ec);
        if(sr + 1 <= er)   
            count += mazePath_normal(sr + 1, sc, er, ec);
        if(sr + 1 <= er && sc + 1 <= ec)
            count += mazePath_normal(sr + 1, sc + 1, er, ec);

        return count;
    }

    public static int mazePath_memo(int sr, int sc, int er, int ec, int[][] dp){
        if(sr == er && sc == ec){
            return dp[sr][sc] = 1;
        }

        if(dp[sr][sc] != 0){
            return dp[sr][sc];
        }

        int count = 0;
        if(sc + 1 <= ec)
            count += mazePath_memo(sr, sc + 1, er, ec, dp);
        if(sr + 1 <= er)   
            count += mazePath_memo(sr + 1, sc, er, ec, dp);
        if(sr + 1 <= er && sc + 1 <= ec)
            count += mazePath_memo(sr + 1, sc + 1, er, ec, dp);

        return dp[sr][sc] = count;
    }

    public static int mazePath_DP_tabulation(int SR, int SC, int er, int ec, int[][] dp){
        for(int sr = er; sr >= 0; sr--){
            for(int sc = ec; sc >= 0; sc--){

                if(sr == er && sc == ec){
                    dp[sr][sc] = 1;
                    continue;
                }
        
                int count = 0;
                if(sc + 1 <= ec)
                    count += dp[sr][sc + 1]; //mazePath_memo(sr, sc + 1, er, ec);
                if(sr + 1 <= er)   
                    count += dp[sr + 1][sc]; //mazePath_memo(sr + 1, sc, er, ec);
                if(sr + 1 <= er && sc + 1 <= ec)
                    count += dp[sr + 1][sc + 1]; //mazePath_memo(sr + 1, sc + 1, er, ec);
        
                dp[sr][sc] = count;
            }
        }
        
        return dp[SR][SC];
    }

    public static int mazePathInfi_memo(int sr, int sc, int er, int ec, int[][] dp) {
        if (sr == er && sc == ec) {
            return dp[sr][sc] = 1;
        }

        if (dp[sr][sc] != 0)
            return dp[sr][sc];

        int count = 0;
        for (int jump = 1; sc + jump <= ec; jump++)
            count += mazePathInfi_memo(sr, sc + jump, er, ec, dp);
        for (int jump = 1; sr + jump <= er; jump++)
            count += mazePathInfi_memo(sr + jump, sc, er, ec, dp);
        for (int jump = 1; sc + jump <= ec && sr + jump <= er; jump++)
            count += mazePathInfi_memo(sr + jump, sc + jump, er, ec, dp);

        return dp[sr][sc] = count;
    }

    public static int mazePathInfi_DP_tabulation(int SR, int SC, int er, int ec, int[][] dp) {

        for (int sr = er; sr >= SR; sr--) {
            for (int sc = ec; sc >= SC; sc--) {

                if (sr == er && sc == ec) {
                    dp[sr][sc] = 1;
                    continue;
                }

                int count = 0;
                for (int jump = 1; sc + jump <= ec; jump++)
                    count += dp[sr][sc + jump]; // mazePathInfi_memo(sr, sc + jump, er, ec, dp);
                for (int jump = 1; sr + jump <= er; jump++)
                    count += dp[sr + jump][sc]; // mazePathInfi_memo(sr + jump, sc, er, ec, dp);
                for (int jump = 1; sc + jump <= ec && sr + jump <= er; jump++)
                    count += dp[sr + jump][sc + jump]; // mazePathInfi_memo(sr + jump, sc + jump, er, ec, dp);

                dp[sr][sc] = count;
            }
        }

        return dp[SR][SC];
    }

    public static void mazePath() {
        int n = 5;
        int m = 5;
        int[][] dp = new int[n][m];
        // System.out.println(mazePath_memo(0, 0, n - 1, m - 1, dp));
        // System.out.println(mazePathInfi_memo(0, 0, n - 1, m - 1, dp));
        System.out.println(mazePathInfi_DP_tabulation(0, 0, n - 1, m - 1, dp));

        print2D(dp);
    }

    public static int boardPath_memo(int sp, int ep, int[] dp) {
        if (sp == ep) {
            return dp[sp] = 1;
        }

        if (dp[sp] != 0)
            return dp[sp];

        int count = 0;
        for (int dice = 1; dice <= 6 && sp + dice <= ep; dice++) {
            count += boardPath_memo(sp + dice, ep, dp);
        }

        return dp[sp] = count;
    }

    public static int boardPath_DP(int SP, int ep, int[] dp) {
        for (int sp = ep; sp >= SP; sp--) {
            if (sp == ep) {
                dp[sp] = 1;
                continue;
            }

            int count = 0;
            for (int dice = 1; dice <= 6 && sp + dice <= ep; dice++) {
                count += dp[sp + dice];// boardPath_memo(sp + dice, ep, dp);
            }

            dp[sp] = count;
        }

        return dp[SP];
    }

    public static int boardPath_opti(int sp, int ep) {
        LinkedList<Integer> list = new LinkedList<>();

        for (sp = ep; sp >= 0; sp--) {
            if (sp >= ep - 1)
                list.addFirst(1);
            else {
                if (list.size() <= 6)
                    list.addFirst(list.getFirst() * 2);
                else
                    list.addFirst(list.getFirst() * 2 - list.removeLast());
            }
        }

        return list.getFirst();

    }

    public static void boardPath() {
        int n = 10;
        int[] dp = new int[n + 1];
        // System.out.println(boardPath_memo(0, n, dp));
        System.out.println(boardPath_DP(0, n, dp));
        System.out.println(boardPath_opti(0, n));

        print1D(dp);
    }


    public static void main(String[] args){
        fibonacci();
    }
}
