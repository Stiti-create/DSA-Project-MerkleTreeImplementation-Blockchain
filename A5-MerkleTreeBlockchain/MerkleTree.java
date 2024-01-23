import Includes.*;
import java.util.*;
import java.lang.Math;

public class MerkleTree{
	// Check the TreeNode.java file for more details
	public TreeNode rootnode;
	public int numstudents;

	public String Build(List<Pair<String,Integer>> documents){
		// Implement Code here
		CRF o = new CRF(64);
		int s = documents.size();
		int h = (int)((float)Math.log(s)/(float)Math.log(2));

		TreeNode[] lnode = new TreeNode[s];
		for (int i=0; i<s; i++){
			TreeNode tn = new TreeNode();
			lnode[i] = tn;
			lnode[i].val=(documents.get(i).First+"_"+documents.get(i).Second);
			lnode[i].left = null;
			lnode[i].right = null;
			lnode[i].maxleafval = documents.get(i).Second;
			lnode[i].numberLeaves = 1;
			lnode[i].isLeaf = true;
			}
		TreeNode[][] tree = new TreeNode[h][s/2];
		int t = s/2;
		for (int j = h-1; j>=0; j--){

			for (int k=0; k<t; k++){
				TreeNode tn2 = new TreeNode();
				tree[j][k] = tn2;
				if(j==h-1){
					tree[j][k].left = lnode[2*k];
					tree[j][k].right = lnode[(2*k)+1];
					lnode[2*k].parent = tree[j][k];
					lnode[(2*k)+1].parent = tree[j][k];
					tree[j][k].val = o.Fn(tree[j][k].left.val+"#"+tree[j][k].right.val);
					tree[j][k].isLeaf = false;
					tree[j][k].numberLeaves = 2;
					tree[j][k].maxleafval = Math.max(tree[j][k].left.maxleafval, tree[j][k].right.maxleafval);
				    }
				else{
                    tree[j][k].left = tree[j+1][2*k];
                    tree[j][k].right = tree[j+1][(2*k)+1];
                    tree[j+1][2*k].parent = tree[j][k];
                    tree[j+1][(2*k)+1].parent = tree[j][k];
                    tree[j][k].val = o.Fn(tree[j][k].left.val+"#"+tree[j][k].right.val);
                    tree[j][k].isLeaf = false;
                    tree[j][k].numberLeaves = tree[j][k].left.numberLeaves + tree[j][k].right.numberLeaves;
                    tree[j][k].maxleafval = Math.max(tree[j][k].left.maxleafval, tree[j][k].right.maxleafval);
                    }
                }
             t=t/2;
             }
        tree[0][0].parent = null;
        this.numstudents = s;
        this.rootnode = tree[0][0];
		return this.rootnode.val;
	}

	/*
		Pair is a generic data structure defined in the Pair.java file
		It has two attributes - First and Second
	*/

	public String UpdateDocument(int student_id, int newScore){
		// Implement Code here
		CRF o = new CRF(64);
		TreeNode curr = this.rootnode;
		int n = this.numstudents;
		int h = (int)((float)Math.log(n)/(float)Math.log(2));
		int l = student_id;
		for(int x=1; x<=h; x++){
			if (l<n/2){
			    curr=curr.left;
			 	n = n/2;
				}
			else {
				curr=curr.right;
				l=l-n/2;
				n=n/2;
				}
			}
        String str = curr.val;
        int index=str.lastIndexOf("_");
        String sub = str.substring(0,index+1);
        curr.val = sub+newScore;
        curr.maxleafval = newScore;
        while(curr.parent!=null){
			curr.parent.maxleafval = Math.max(curr.parent.left.maxleafval,curr.parent.right.maxleafval);
			curr.parent.val = o.Fn(curr.parent.left.val+"#"+curr.parent.right.val);
			curr=curr.parent;
			}
        this.rootnode.val = curr.val;
        return this.rootnode.val;
	}
}
