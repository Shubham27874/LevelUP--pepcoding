package Tree;

import java.util.*;

public class questionsBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) { 
            this.val = val; 
        }
    }

    public int size(TreeNode node){
        return node == null ? 0 : size(node.left) + size(node.right) + 1;
    }

    public int height(TreeNode node){
        return node == null ? -1 : Math.max(height(node.left), height(node.right)) + 1;
    }

    public int maximum(TreeNode node){
        if(node == null)
            return -(int)1e9;

        int lmv = maximum(node.left);  //left maximum value
        int rmv = maximum(node.right);  //right maximum value

        return Math.max(Math.max(lmv, rmv), node.val);
    }

    public boolean find(TreeNode node, int data){
        if(node == null)
            return false;
        if(node.val == data)
            return true;

        return find(node.left, data) || find(node.right, data);
    }

    public boolean rootToNodePath(TreeNode node, TreeNode data, ArrayList<TreeNode> ans){
        if(node == null)
            return false;

        if(node == data){
            ans.add(node);
            return true;
        }
        
        boolean res = rootToNodePath(node.left, data, ans) || rootToNodePath(node.right, data, ans);

        if(res)
            ans.add(node);

        return res;
    }

    public ArrayList<TreeNode> rootToNodePath(TreeNode node, int data){
        if(node == null)
            return new ArrayList<>();

        if(node.val == data) {
            ArrayList<TreeNode> base = new ArrayList<>();
            base.add(node);
            return base;
        }

        ArrayList<TreeNode> left = rootToNodePath(node.left, data);
        if(left.size() > 0){
            left.add(node);
            return left;
        }
        
        ArrayList<TreeNode> right = rootToNodePath(node.right, data);
        if(right.size() > 0){
            right.add(node);
            return right;
        }

        return new ArrayList<>();
    }

    //Leetcode 236
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null)
            return null;

        ArrayList<TreeNode> listOne = new ArrayList<>();
        ArrayList<TreeNode> listTwo = new ArrayList<>(); 

        rootToNodePath(root, p, listOne);
        rootToNodePath(root, q, listTwo);

        int i = listOne.size() - 1;
        int j = listTwo.size() - 1;

        TreeNode LCA = null;

        while(i >= 0 && j >= 0){
            if(listOne.get(i) != listTwo.get(j))
                break;
            
            LCA = listOne.get(i);
            i--;
            j--;
        }

        return LCA;
    }

    //Leetcode 863
    public void printKdown(TreeNode node, int depth, TreeNode block, List<Integer> ans){
        if(node == null || depth < 0 || node == block)
            return;

        if(depth == 0){
            ans.add(node.val);
            return;
        }

        printKdown(node.left, depth - 1, block, ans);
        printKdown(node.right, depth - 1, block, ans);
    }

    public List<Integer> distanceK_(TreeNode root, TreeNode target, int k) {
        ArrayList<TreeNode> list = new ArrayList<>();
        rootToNodePath(root, target, list);

        List<Integer> ans = new ArrayList<>();

        TreeNode blockNode = null;
        for(int i = 0; i < list.size(); i++){
            printKdown(list.get(i), k - i, blockNode, ans);
            blockNode = list.get(i);
        }

        return ans;
    }

    //Better Solution
    public int distanceK2(TreeNode node, TreeNode target, int k, List<Integer> ans){
        if(node == null)
            return -1;

        if(node == target){
            printKdown(node, k, null, ans);
            return 1;
        }

        int lans = distanceK2(node.left, target, k, ans);
        if(lans != -1){
            printKdown(node, k - lans, node.left, ans);
            return lans + 1;
        }
        
        int rans = distanceK2(node.right, target, k, ans);
        if(rans != -1){
            printKdown(node, k - rans, node.right, ans);
            return rans + 1;
        }

        return -1;
    } 

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        distanceK2(root, target, k, ans);
        return ans;
    }
    
    public int rootToNodeDistance(TreeNode node, TreeNode data){
        if(node == null)
            return -1;
            
        if(node == data)
            return 0;

        int lans = rootToNodeDistance(node.left, data);
        if(lans != -1){
            return lans + 1;
        }

        int rans = rootToNodeDistance(node.right, data);
        if(rans != -1){
            return rans + 1;
        }

        return -1;
    }

    //=======================================================================

    //Leetcode 543
    public int diameterOfBinaryTree_01(TreeNode root) { 
        if(root == null)
            return -1;

        int leftTreeDia = diameterOfBinaryTree(root.left);
        int rightTreeDia = diameterOfBinaryTree(root.right);

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        return Math.max(Math.max(leftTreeDia, rightTreeDia), leftHeight + rightHeight + 2);
    }

    // {dia, height}
    public int[] diameterOfBinaryTree_02(TreeNode root){
        if(root == null)    
            return new int[]{-1, -1};

        int[] leftAns = diameterOfBinaryTree_02(root.left);
        int[] rightAns = diameterOfBinaryTree_02(root.right);

        int[] ans = new int[2];
        ans[0] = Math.max(Math.max(leftAns[0], rightAns[0]), leftAns[1] + rightAns[1] + 2);
        ans[1] = Math.max(leftAns[1], rightAns[1]) + 1;

        return ans;
    }

    int maxDia = 0;
    public int diameterOfBinaryTree_03(TreeNode root){
        if(root == null)    
            return -1;

        int leftHeight = diameterOfBinaryTree_03(root.left);
        int rightHeight = diameterOfBinaryTree_03(root.right);

        maxDia = Math.max(maxDia, leftHeight + rightHeight + 2);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    //If the use of global variable is restricted
    public int diameterOfBinaryTree_04(TreeNode root, int[] ans){
        if(root == null)    
            return -1;

        int leftHeight = diameterOfBinaryTree_04(root.left, ans);
        int rightHeight = diameterOfBinaryTree_04(root.right, ans);

        ans[0] = Math.max(ans[0], leftHeight + rightHeight + 2);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int diameterOfBinaryTree(TreeNode root){
        if(root == null)
            return 0;
        // return diameterOfBinaryTree_01(root);
        // return diameterOfBinaryTree_02(root)[0];

        int[] ans = new int[1];
        diameterOfBinaryTree_04(root, ans);
        return ans[0];
    }

    //Leetcode 112
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null)
            return false;
        
        if(root.left == null && root.right == null)
            return targetSum - root.val == 0;
        
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    //Leetcode 113
    public void pathSum(TreeNode root, int targetSum, List<List<Integer>> res, List<Integer> smallAns) {
        if(root == null)
            return;
        
        if(root.left == null && root.right == null){
            if(targetSum - root.val == 0){
                ArrayList<Integer> base = new ArrayList<>(smallAns);
                base.add(root.val);
                res.add(base);
            }
            
            return;
        }
            
        smallAns.add(root.val);
        
        pathSum(root.left, targetSum - root.val, res, smallAns);
        pathSum(root.right, targetSum - root.val, res, smallAns);
        
        smallAns.remove(smallAns.size() - 1);
        
    }
    
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> smallAns = new ArrayList<>();
        
        pathSum(root, targetSum, res, smallAns);
        
        return res;
    }

    //Maximum Path Sum between 2 Leaf Nodes 
    class Node{
        int data;
        Node left, right;

        Node(int item){
            data = item;
            left = right = null;
        }
    } 

    int maxLeafToLeaf = -(int)1e9;
    int maxPathSum_(Node root){ 
        if(root == null)
            return -(int)1e9;
            
        if(root.left == null && root.right == null)
            return root.data;
            
        int LNTLMS = maxPathSum_(root.left);
        int RNTLMS = maxPathSum_(root.right);
    
        if(root.left != null && root.right != null)        
            maxLeafToLeaf = Math.max(maxLeafToLeaf, LNTLMS + RNTLMS + root.data);
            
        return Math.max(LNTLMS, RNTLMS) + root.data;
    }
    
    int maxPathSum(Node root){
        // if(root == null)
        //     return Integer.MIN_VALUE;
            
        maxPathSum_(root);
        return maxLeafToLeaf;
    }

    //Leetcode 124
    int maxNTN = -(int)1e9;
    public int maxPathSum_(TreeNode root) {
        if(root == null)
            return 0;

        int leftMaxPathSum = maxPathSum_(root.left);
        int rightMaxPathSum = maxPathSum_(root.right);

        int maxSumTillRoot = Math.max(leftMaxPathSum, rightMaxPathSum) + root.val;
        maxNTN = Math.max(Math.max(maxNTN, maxSumTillRoot), Math.max(root.val, leftMaxPathSum + root.val + rightMaxPathSum));

        return Math.max(maxSumTillRoot, root.val);
    }

    public int maxPathSum(TreeNode root) {
        if(root == null)
            return 0;

        maxPathSum_(root);
        return maxNTN;
    }

    //Leetcode 98
    public class BSTpair{
        boolean isBST = true;
        long max = -(long)1e13;
        long min = (long)1e13;

        BSTpair(boolean isBST, long max, long min){
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }

        BSTpair() {

        }
    }

    public BSTpair isValidBST_(TreeNode root) {
        if(root == null)
            return new BSTpair();

        BSTpair lres = isValidBST_(root.left);
        BSTpair rres = isValidBST_(root.right);

        BSTpair myres = new BSTpair();
        myres.isBST = lres.isBST && rres.isBST && lres.max < root.val && root.val < rres.min;

        if(!myres.isBST)
            return myres;

        myres.max = Math.max(rres.max, root.val);
        myres.min = Math.min(lres.min, root.val);

        return myres;
    }

    public boolean isValidBST(TreeNode root){
        return isValidBST_(root).isBST;
    }

    //Leetcode 99
    TreeNode a = null, b = null, prev = null;
    public boolean recoverTree_(TreeNode root) {
        if(root == null)
            return true;

        if(!recoverTree_(root.left))
            return false;

        if(prev != null && prev.val > root.val){
            b = root;
            if(a == null)
                a = prev;
            else 
                return false;
        }

        prev = root;
        if(!recoverTree_(root.right))
            return false;

        return true;
    }

    public void recoverTree(TreeNode root) {
        recoverTree_(root);
        if(a != null){
            int temp = a.val;
            a.val = b.val;
            b.val = temp;
        }
    }

    public static class allSolPair{
        TreeNode prev = null;
        TreeNode pred = null;
        TreeNode succ = null;

        int ceil = (int)1e8;
        int floor = -(int)1e8;
    }

    public static void allSolution(TreeNode node, int data, allSolPair pair){
        if(node == null)
            return;

        if(node.val < data)
            pair.floor = Math.max(pair.floor, node.val);

        if(node.val > data)
            pair.ceil = Math.min(pair.ceil, node.val);

        allSolution(node.left, data, pair);

        if(node.val == data && pair.pred == null)
            pair.pred = pair.prev;
        if(pair.prev != null && pair.prev.val == data && pair.succ == null)
            pair.succ = node;


        pair.prev = node;
        allSolution(node.right, data, pair);
    }

    //Leetcode 173
    class BSTIterator {
        LinkedList<TreeNode> st = new LinkedList<>();
        public BSTIterator(TreeNode root) {
            addAllLeft(root);    
        }

        public void addAllLeft(TreeNode node){
            if(node == null)
                return;

            TreeNode curr = node;
            while(curr != null){
                st.addFirst(curr);
                curr = curr.left;
            }
        }

        public int next() {
            TreeNode rn = st.removeFirst();
            addAllLeft(rn.right);
            return rn.val;
        }
        
        public boolean hasNext() {
            return st.size() != 0;
        }
    }

    //Leetcode 510
    public Node indorderSuccessor(Node node){
        if(node.right != null){
            node = node.right;
            while(node.left != null)
                node = node.left;

            return node;
        } else {
            while(node != null){
                if(node.parent != null && node.parent.left == node){
                    return node.parent;
                }

                node = node.parent;
            }
            return null;
        }
    }

    //Leetcode 105
    // T -> avg : O(nlogn), worst : O(n^2).
    public TreeNode preInTree(int[] preorder, int psi, int pei, int[] inorder, int isi, int iei){
        if(psi > pei)
            return null;

        TreeNode node = new TreeNode(preorder[psi]);

        int idx = isi;
        while(inorder[idx] != preorder[psi]) 
            idx++;

        int tnoe = idx - isi;

        node.left = preInTree(preorder, psi + 1, psi + tnoe, inorder, isi, idx - 1);
        node.right = preInTree(preorder, psi + tnoe + 1, pei, inorder, idx + 1, iei);

        return node;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return preInTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    //Leetcode 106
    public TreeNode postInTree(int[] inorder, int isi, int iei, int[] postorder, int psi, int pei){
        if(psi > pei)
            return null;

        TreeNode node = new TreeNode(postorder[pei]);
        int idx = isi;
        while(inorder[idx] != postorder[pei])
            idx++;

        int tnoe = idx - isi;

        node.left = postInTree(inorder, isi, idx - 1, postorder, psi, psi + tnoe - 1);
        node.right = postInTree(inorder, idx + 1, iei, postorder, psi + tnoe, pei - 1);

        return node;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return postInTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }


    //Leetcode 889
    public TreeNode postPreTree(int[] post, int ppsi, int ppei, int[] pre, int psi, int pei) {
        if (psi > pei)
            return null;

        TreeNode node = new TreeNode(pre[psi]);

        if (psi == pei)
            return node;

        int idx = ppsi;
        while (post[idx] != pre[psi + 1])
            idx++;

        int tnel = idx - ppsi + 1;
        node.left = postPreTree(post, ppsi, idx, pre, psi + 1, psi + tnel);
        node.right = postPreTree(post, idx + 1, ppei - 1, pre, psi + tnel + 1, pei);

        return node;
    }

    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        int n = post.length;

        return postPreTree(post, 0, n - 1, pre, 0, n - 1);
    }

    //Leetcode 114
    //Time complexity : O(n^2)
    public TreeNode getTail(TreeNode node){
        if(node == null)
            return null;

        TreeNode curr = node;
        while(curr.right != null){
            curr = curr.right;
        }

        return curr;
    }

    public void flatten(TreeNode root) {
        if(root == null) 
            return;

        flatten(root.left);
        flatten(root.right);

        TreeNode tail = getTail(root.left);
        if(tail != null){
            tail.right = root.right;
            root.right = root.left;
            root.left = null;
        }
    }

    //Time complexity : O(n)
    public TreeNode flatten_(TreeNode root){
        if(root == null || (root.left == null && root.right == null))
            return root;

        TreeNode leftTail = flatten_(root.left);
        TreeNode rightTail = flatten_(root.right);

        if(leftTail != null){
            leftTail.right = root.right;
            root.right = root.left;
            root.left = null;
        }

        return rightTail != null ? rightTail : leftTail;
    }

    public void flatten(TreeNode root) {
        if(root == null) 
            return;

        flatten_(root);
    }

    //BinaryTree to DLL
    Node dummy = new Node(-1);
    Node previous = dummy;
    public void binarToDLL(Node root){
        if(root == null)
            return;

        binarToDLL(root.left);

        previous.right = root;
        root.left = previous;

        previous = root;

        binarToDLL(root.right);
    }

    Node bToDLL(Node root){
        if(root == null)
            return root;

        binarToDLL(root);
        Node head = dummy.right;
        head.left = null;
        dummy.right = null;

        // previous.right = head;    //FOR CDLL
        // head.left = previous;

        return head;
    }


    
}
