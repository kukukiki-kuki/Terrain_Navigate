package cn.shzu.sundry;

import java.util.*;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 测试
 * @date 2024/5/9 17:41:50
 */
public class test1 {

    public static void main(String[] args) {
        test1 test1 = new test1();
//        int [] new1 = {5,1,2,3,4};
//        int [] new2 = {4,4,1,5,1};
//     char [][] b   = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
//        String s= "A man, a plan, a canal: Panama";
//        String s ="au";
//        System.out.println(test1.canCompleteCircuit(new1,new2));
//        System.out.println(test1.isValidSudoku(b));
//        int [][] max = {{1,2,3},{4,5,6},{7,8,9}};
//        System.out.println(test1.spiralOrder(max));
//        int [][] zeros = {{0,1}};
//        test1.setZeroes(zeros);
//          int [][] z = {{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
//          test1.gameOfLife(z);
//        String a = "abba";
//        String c = "dog cat cat dog";
//        System.out.println(test1.wordPattern(a, c));
//      String[] sts =   {"eat","tea","tan","ate","nat","bat"};
//       System.out.println(test1.groupAnagrams(sts));
//        int n = 2;
//        System.out.println(test1.isHappy(n));
//        int [] n = {0,2,3,4,6,8,9};
//        System.out.println(test1.summaryRanges(n));
//        String s = "{[]}";
//        System.out.println(test1.isValid(s));
//        String [] s= {"4","13","5","/","+"};
//        System.out.println(test1.evalRPN(s));
        int n = 13;
        System.out.println(test1.trailingZeroes(n));
    }
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int [] s = new int[n];
        int sum = 0;
        for(int i = 0;i<n;i++){
            s[i] = gas[i] - cost[i];
            sum+=s[i];
        }
        if(sum<0){
            return -1;
        }

        int target=0,num = 0;
        boolean flag = true;
        for(int i = 0;i<n;i++){
            num =0;
            if(s[i]>=0){
                for(int j = 0;j<n;j++){
                    num += s[(i+j)%n];
                    if(num<0){
                        flag = false;
                        break;
                    }
                }
                if(flag == true){
                    target = i;
                    break;
                }
            }
        }
        return target;
    }
    public boolean isPalindrome(String s) {
        StringBuffer sb = new StringBuffer();
        char [] c = s.toCharArray();
        int len = c.length;
        for(int i = 0;i<len;i++){
            if(Character.isLetter(c[i])){
                sb.append(Character.toLowerCase(c[i]));
            }
        }
        String ss = sb.toString();
        for(int i = 0;i<ss.length();i++){
            if(s.charAt(i)!=ss.charAt(ss.length()-1-i)){
                return false;
            }
        }
        return true;
    }
    public int lengthOfLongestSubstring(String s) {
        //
        int ls = s.length();
        if(ls==0){
            return 0;
        }
        if(ls==1){
            return 1;
        }

        int i=0,len = 1;
        while(i+len<=ls){
            if(isDuplication(s.substring(i,i+len))){
                len++;
            }else {
                i++;
            }

        }
        return len-1;
    }

    public boolean isDuplication(String s){
        int ls = s.length();
        HashSet<Character> hs = new HashSet<>();
        for(int i = 0;i<ls;i++){
            if(hs.contains(s.charAt(i))){
                return false;
            }
            hs.add(s.charAt(i));
        }
        return true;
    }

    public boolean isValidSudoku(char[][] board) {
        HashSet<Character> hs = new HashSet<>();
        //横轴
        for(int i = 0;i<9;i++){
            hs.clear();
            for(int j = 0;j<9;j++){
                if(hs.contains(board[i][j])){
                    return false;
                }
                if(board[i][j]!='.'){
                    hs.add(board[i][j]);
                }
            }
        }

        hs.clear();
        //纵轴
        for(int i = 0;i<9;i++){
            hs.clear();
            for(int j = 0;j<9;j++){
                if(hs.contains(board[j][i])){
                    return false;
                }
                if(board[j][i]!='.'){
                    hs.add(board[j][i]);
                }
            }
        }

        hs.clear();
        //网格
        for(int i = 0;i<9;i+=3){
            for(int j =0;j<9;j+=3){
                hs.clear();
                for(int m = 0;m<3;m++){
                    for(int n =0;n<3;n++){
                        if(hs.contains(board[i+m][j+n])){
                            return false;
                        }
                        if(board[i+m][j+n]!='.'){
                            hs.add(board[i+m][j+n]);
                        }
                    }
                }
            }
        }
        return true;
    }
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list= new ArrayList<>();
        if(matrix.length==0){
            return list;
        }

        for (Integer i:list) {
            System.out.println(i);
        }
        int up = 0;
        int down = matrix.length;
        int left = 0, right = matrix[0].length;
        int i=0,j = 0;

        while(down!=up||right!=left){
            while(j<right){
                list.add(matrix[i][j]);
                j++;
            }
            right--;
            j--;
            i++;
            while(i<down){
                list.add(matrix[i][j]);
                i++;
            }
            down--;
            i--;
            j--;
            while(j>=left){
                list.add(matrix[i][j]);
                j--;
            }
            left++;
            j++;
            i--;
            while(i>up){
                list.add(matrix[i][j]);
                i--;
            }
            i++;
            up++;
        }
        return list;
    }
    public void setZeroes(int[][] matrix) {
        List<Integer> listRow = new ArrayList<>();
        List<Integer> listCol = new ArrayList<>();

        int m = matrix.length;
        int n = matrix[0].length;
        for(int i = 0;i<m;i++){
            for(int j =0;j<n;j++){
                if(matrix[i][j]==0){
                    listRow.add(i);
                    listCol.add(j);
                }
            }
        }
        //进行赋值
        for(int i = 0;i<listRow.size();i++){
            for(int j = 0;j<n;j++){
                matrix[listRow.get(i)][j] = 0;
            }
        }
        for(int i = 0;i<listCol.size();i++){
            for(int j = 0;j<m;j++){
                matrix[j][listCol.get(i)] = 0;
            }
        }
    }

    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        for(int i = 0;i<m;i++){
            for(int j =0;j<n;j++){
                if(board[i][j]!=0){
                    board[i][j] = liveCell(board,i,j);
                }else{
                    board[i][j] = deadCell(board,i,j);
                }
            }
        }
    }
    public int liveCell(int[][] board,int i,int j){
        int m = board.length;
        int n = board[0].length;
        int count = 0;
        int [][] directs = {{1,1},{1,-1},{0,1},{0,-1},{1,0},{-1,0},{-1,1},{-1,-1}};
        for(int w = 0;w<directs.length;w++){
            if(inBoard(i,j,m,n,directs[w][0],directs[w][1])&&board[i+directs[w][0]][j+directs[w][1]]==1){
                count++;
            }
        }
        if(count==2||count==3){
            return 1;
        }else{
            return 0;
        }
    }
    public int deadCell(int [][] board,int i,int j){
        int count = 0;
        int m = board.length;
        int n = board[0].length;
        int [][] directs = {{1,1},{1,-1},{0,1},{0,-1},{1,0},{-1,0},{-1,1},{-1,-1}};
        for(int w = 0;w<directs.length;w++){
            if(inBoard(i,j,m,n,directs[w][0],directs[w][1])&& board[i+directs[w][0]][j+directs[w][1]]==1){
                count++;
            }
        }
        if(count==3){
            return 1;
        }else{
            return 0;
        }
    }

    public boolean inBoard(int i,int j,int m,int n,int k,int l){
        if(i+k<0||i+k>=m){
            return false;
        }
        if(j+l<0||j+l>=n){
            return false;
        }
        return true;
    }

    public boolean wordPattern(String pattern, String s) {
        HashMap<Character,String> hashMap  = new HashMap<>();
        String[] words = s.split("\\s+");
        if(pattern.length()!= words.length){
            return false;
        }
        for(int i = 0;i<pattern.length();i++){
            if(hashMap.containsKey(pattern.charAt(i)) && !hashMap.get(pattern.charAt(i)).equals(words[i])){
                return false;
            }
            hashMap.put(pattern.charAt(i),words[i]);
        }
        return true;
    }
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> list = new ArrayList<>();
        List<String> hlist = new ArrayList<>();
        HashSet<String> hs = new HashSet<>();
        int ls = strs.length;
        if(ls==0){
            return list;
        }
        if(ls==1){
            hlist.add(strs[0]);
             list.add(hlist);
             return  list;
        }
        hlist.add(strs[0]);
        hs.add(strs[0]);
        for(int i = 0;i<strs.length;i++){
            if(hs.contains(strs[i])){
                break;
            }
            for(int j = i+1;j<strs.length;j++){
                if(isAnagrams(strs[i],strs[j])){
                    hlist.add(strs[j]);
                    hs.add(strs[j]);
                }
            }
            list.add(hlist);
            hlist.clear();
        }
        return list;
    }

    public boolean isAnagrams(String s,String t){
        int [] num = new int [26];
        if(s.length()!=t.length()){
            return false;
        }
        for(int i = 0;i<s.length();i++){
            num[(s.charAt(i)-'a')]++;
            num[(t.charAt(i)-'a')]--;
        }
        for(int i = 0;i<s.length();i++){
            if(num[i]!=0){
                return false;
            }
        }
        return true;
    }

    public boolean isHappy(int n) {
        HashSet<Integer>  hashSet =   new HashSet<>();
        int num = 0;
        while(!hashSet.contains(n)){
            hashSet.add(n);
            if(n==1){
                return true;
            }
            while(n>0){
                num+= (int) Math.pow(n%10,2);
                n = n/10;
            }
            n = num;
            num = 0;
        }
        return false;
    }

    public List<String> summaryRanges(int[] nums) {
        List<String> list = new  ArrayList<>();
        int len=0,fast=0,low = 0;
        while(fast<=nums.length){
            if(fast==nums.length-1||nums[low] != nums[fast] - len){
                if(low==fast-1){
                    list.add(nums[low]+"");
                }else{
                    list.add(nums[low]+"->"+nums[fast-1]);
                }
                low = fast;
                len = 0;
            }else{
                fast++;
                len++;
            }
        }
        return list;
    }

    public boolean isValid(String s) {
        char[] chars =   new char[s.length()+1];
        for(int i = 0,len=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='('||c=='{'||c=='['){
                chars[i] = c;
                len++;
            }else{
                if(i==0){
                    return false;
                }
                switch(c){
                    case ')' :
                        if(chars[len-1]!='('){
                            return false;
                        }
                        break;
                    case ']' :
                        if(chars[len-1]!='['){
                            return false;
                        }
                        break;
                    case '}' :
                        if(chars[len-1]!='{'){
                            return false;
                        }
                        break;
                }
                len--;
            }
        }
        return true;
    }
    public int evalRPN(String[] tokens) {
        HashSet<String> hashSet = new HashSet<>();
        Deque stack =  new ArrayDeque();
        int pro = 0;
        hashSet.add("+");
        hashSet.add("-");
        hashSet.add("*");
        hashSet.add("/");
        for (String token : tokens) {
            if (!hashSet.contains(token)) {
                stack.push(token);
            } else {
                int m = Integer.parseInt(stack.pop().toString());
                int n = Integer.parseInt(stack.pop().toString());
                if ("+".equals(token)) {
                    pro = n + m;
                }
                if ("-".equals(token)) {
                    pro = n - m;
                }
                if ("*".equals(token)) {
                    pro = n * m;
                }
                if ("/".equals(token)) {
                    pro = n / m;
                }
                stack.push("" + pro);
            }
        }
        return pro;
    }
    public int trailingZeroes(int n) {
        int num =1;
        int count = 0;
        for(int i = 1;i<=n;i++){
            num *= i;
        }
        while(num%10==0){
            count++;
            num = num/10;
        }
        return count;
    }

}
