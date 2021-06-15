import java.util.*;

public class question {

    //Leetcode 215
    public int findKthLargest(int[] nums, int k) { //MinPQ
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for(int ele : nums){
            pq.add(ele);

            if(pq.size() > k)
                pq.remove();
        }

        return pq.peek();
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void downHeapify(int[] arr, int pi, int li){
        int maxIdx = pi;
        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;
        
        if(lci <= li && arr[lci] > arr[maxIdx])
            maxIdx = lci;
        if(rci <= li && arr[rci] > arr[maxIdx])
            maxIdx = rci;

        if(maxIdx != pi){
            swap(arr, pi, maxIdx);
            downHeapify(arr, maxIdx, li);
        }
    }

    public int findKthLargest_opti(int[] nums, int k) { //MinPQ
        int li = nums.length - 1;
        for(int i = li; i >= 0; i--){
            downHeapify(nums, i, li);
        }

        while(k-- > 1){
            swap(nums, 0, li--);
            downHeapify(nums, 0, li);
        }

        return nums[0];
    }

    //GFG
    public int findKthSmallest(int[] nums, int k) { //MaxPQ
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            return b - a;
        });
        
        for(int ele : nums){
            pq.add(ele);

            if(pq.size() > k)
                pq.remove();
        }

        return pq.peek();
    }

    //Leetcode 703
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int K = 0;
    public void KthLargest(int k, int[] nums) {
        this.K = k;
        for(int ele : nums){
            this.pq.add(ele);
            if(this.pq.size() > this.K)
                this.pq.remove();
        }
    }
    
    public int add(int val) {
        this.pq.add(val);
        if(this.pq.size() > this.K)
            this.pq.remove();

        return this.pq.peek();
    }

    //Leetcode 349
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set = new HashSet<>();
        for(int ele : nums1)
            set.add(ele);

        ArrayList<Integer> ans = new ArrayList<>();
        for(int ele : nums2){
            if(set.contains(ele)){
                ans.add(ele);
                set.remove(ele);
            }
        }

        int[] arr = new int[ans.size()];
        int i = 0;
        for(Integer key : ans){
            arr[i++] = key;
        }

        return arr;
    }

    //Leetcode 350
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int ele : nums1)
            map.put(ele, map.getOrDefault(ele, 0) + 1);

        ArrayList<Integer> ans = new ArrayList<>();
        for(int ele : nums2){
            if(map.containsKey(ele)){
                ans.add(ele);
                map.put(ele, map.get(ele) - 1);
                if(map.get(ele) == 0)
                    map.remove(ele);
            }
        }

        int[] arr = new int[ans.size()];
        int i = 0;
        for(Integer key : ans){
            arr[i++] = key;
        }

        return arr;
    }

    //Leetcode 128
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        for(int ele : nums)
            set.add(ele);

        int len = 0;
        for(int ele : nums){
            if(!set.contains(ele))
                continue;
            
            int ple = ele - 1;
            int pre = ele + 1;

            while(set.contains(ple))
                set.remove(ple--);
            
            while(set.contains(pre))
                set.remove(pre++);
            
            len = Math.max(len, pre - ple - 1);
        }

        return len;
    }

    //Leetcode 347
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int ele : nums)
            map.put(ele, map.getOrDefault(ele, 0) + 1);
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[1] - b[1];
        });
        
        for(Integer key : map.keySet()){
            pq.add(new int[] {key, map.get(key)});
            if(pq.size() > k)
                pq.remove();
        }
        
        int[] ans = new int[pq.size()];
        int i = 0;
        while(pq.size() != 0){
            int[] p = pq.remove();
            int val = p[0];
            int freq = p[1];
            
            ans[i++] = val;
        }
        
        return ans;
    }

    //Leetcode 937
    public int[][] kClosest(int[][] points, int k) {
        //{x, y}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            int d1 = a[0] * a[0] + a[1] * a[1];  //x1^2 + y1^2
            int d2 = b[0] * b[0] + b[1] * b[1];  //x2^2 + y2^2

            return d2 - d1;
        });

        for(int[] p : points){
            pq.add(new int[]{p[0], p[1]});
            if(pq.size() > k){
                pq.remove();
            }
        }

        int[][] ans = new int[k][];
        int i = 0;
        while(pq.size() != 0){
            int[] p = pq.remove();

            ans[i++] = p;
        }

        return ans;
    }

    //Leetcode 378
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int m = matrix[0].length;

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            int r1 = a / m, c1 = a % m;
            int r2 = b / m, c2 = b % m;

            return matrix[r1][c1] - matrix[r2][c2];
        });

        for(int i = 0; i < n; i++){
            pq.add(i * m + 0);
        }

        int ans = 0;
        while(k-- > 0){
            int idx = pq.remove();
            int r = idx / m;
            int c = idx % m;

            ans = matrix[r][c];

            c++;
            if(c < m){
                pq.add(r * m + c);
            }
        }

        return ans;
    }

    //Leetcode 380
    class RandomizedSet {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        Random rand = new Random();

        /** Initialize your data structure here. */
        public RandomizedSet() {
            
        }
        
        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(map.containsKey(val))
                return false;

            list.add(val);
            map.put(val, list.size() - 1);

            return true;
        }
        
        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(!map.containsKey(val))
                return false;

            int idx = map.get(val);
            int lidx = list.size() - 1;
            int lval = list.get(lidx);

            list.set(idx, lval);
            map.put(lval, idx);

            list.remove(lidx);
            map.remove(val);

            return true;
        }
        
        /** Get a random element from the set. */
        public int getRandom() {
            int idx = rand.nextInt(list.size());
            return list.get(idx);            
        }
    }

    //Leetcode 895
    class FreqStack {
        HashMap<Integer, Integer> freq;
        ArrayList<Stack<Integer>> freqMap;
        int maxFreq;

        public FreqStack() {
            freq = new HashMap<>();
            freqMap = new ArrayList<>();
            maxFreq = 0;
            
            freqMap.add(new Stack<>());  //Dummy
        }
        
        public void push(int val) {
            freq.put(val, freq.getOrDefault(val, 0) + 1);
            maxFreq = Math.max(maxFreq, freq.get(val));

            if(freqMap.size() == maxFreq)
                freqMap.add(new Stack<>());
            
            freqMap.get(freq.get(val)).add(val);
        }
        
        public int pop() {
            int rv = freqMap.get(maxFreq).pop();
            if(freqMap.get(maxFreq).size() == 0){
                freqMap.remove(maxFreq);
                maxFreq--;
            }

            freq.put(rv, freq.get(rv) - 1);
            if(freq.get(rv) == 0)
                freq.remove(rv);

            return rv;
        }
    }

    //Leetcode 895 - PQ solution
    class FreqStack0 {
        private class pair implements Comparable<pair> {
            int val = 0;
            int freq = 0;
            int idx = 0;

            pair(int val, int freq, int idx) {
                this.val = val;
                this.freq = freq;
                this.idx = idx;
            }

            public int compareTo(pair o) {
                if (this.freq == o.freq)
                    return o.idx - this.idx; // other - this, for max PQ

                return o.freq - this.freq;
            }
        }

        private HashMap<Integer, Integer> freqMap;
        private PriorityQueue<pair> pq;
        private int idx = 0;

        public void FreqStack() {
            this.freqMap = new HashMap<>();
            this.pq = new PriorityQueue<>();
            this.idx = 0;
        }

        public void push(int val) { // Log(n)
            freqMap.put(val, freqMap.getOrDefault(val, 0) + 1);
            pq.add(new pair(val, freqMap.get(val), idx++));
        }

        public int pop() { // Log(n)
            pair p = pq.remove();
            freqMap.put(p.val, freqMap.get(p.val) - 1);
            if (freqMap.get(p.val) == 0)
                freqMap.remove(p.val);

            return p.val;
        }
    }

    //Leetcode 407
    public int trapRainWater(int[][] heightMap) {
        int n = heightMap.length, m = heightMap[0].length;
        int minB = 0, totalWater = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            int i1 = a / m, j1 = a % m;
            int i2 = b / m, j2 = b % m;

            return heightMap[i1][j1] - heightMap[i2][j2];
        });

        boolean[][] vis = new boolean[n][m];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                    vis[i][j] = true;
                    pq.add(i * m + j);
                }
            }
        }

        int[][] dir = {{1,0}, {0,1}, {-1,0}, {0,-1}}; 
        
        while(pq.size() != 0){
            int idx = pq.remove();
            int i = idx / m;
            int j = idx % m;

            minB = Math.max(minB, heightMap[i][j]);
            totalWater += minB - heightMap[i][j];

            for(int d = 0; d < 4; d++){
                int r = i + dir[d][0];
                int c = j + dir[d][1];

                if(r >= 0 && c >= 0 && r < n && c < m && !vis[r][c]){
                    vis[r][c] = true;
                    pq.add(r * m + c);
                }
            }
        }

        return totalWater;
    }

    //Letcode778
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            int i1 = a / n, j1 = a % n;
            int i2 = b / n, j2 = b % n;

            return grid[i1][j1] - grid[i2][j2];
        });

        boolean[][] vis = new boolean[n][n];
        int minH = 0, time = 0;
        int[][] dir = {{1,0}, {0,1}, {-1,0}, {0,-1}}; 

        pq.add(0);
        vis[0][0] = true;

        while(pq.size() != 0){
            int idx = pq.remove();
            int i = idx / n;
            int j = idx % n;
            int height = grid[i][j];

            time += Math.max(0, height - minH);  //**dimag laga tha**

            if(i == n - 1 && j == n - 1)
                break;

            minH = Math.max(minH, height);

            for(int d = 0; d < dir.length; d++){
                int r = i + dir[d][0];
                int c = j + dir[d][1];

                if(r >= 0 && c >= 0 && r < n && c < n && !vis[r][c]){
                    vis[r][c] = true;
                    pq.add(r * n + c);
                }
            }
        }

        return time;
    }
    
}
