# IsSymmetric
> [链接](https://leetcode-cn.com/problems/dui-cheng-de-er-cha-shu-lcof/)
```
请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。

例如，二叉树 [1,2,2,3,4,4,3] 是对称的。

    1
   / \
  2   2
 / \ / \
3  4 4  3
但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:

    1
   / \
  2   2
   \   \
   3    3

 

示例 1：

输入：root = [1,2,2,3,4,4,3]
输出：true
示例 2：

输入：root = [1,2,2,null,3,null,3]
输出：false

```
## 1.解题思路一 

### 1.1 具体实现
```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;

        return diff(root.left,root.right);
    }
    private boolean diff(TreeNode left,TreeNode right){
        if((left == null && right != null) || (left != null && right ==null)) return false;
        if(left == null && right == null) return true;
        if(left.val != right.val) return false;
        return diff(left.left,right.right) && diff(left.right,right.left);
    }
}   
```